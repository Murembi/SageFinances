package com.example.demo.service;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.exception.AssetAlreadyExistsException;
import com.example.demo.exception.AssetNotFoundException;
import com.example.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssetService {

    private final AssetRepository repository;
    private final AuditLogService auditLogService;

    public AssetService(AssetRepository repository,
                        AuditLogService auditLogService) {
        this.repository = repository;
        this.auditLogService = auditLogService;
    }

    // 1 // add 1 asset
    //DONE
    @Transactional
    public Asset addAsset(Asset asset) {
        asset.setCreatedAt(LocalDateTime.now());
        asset.setAcquisitionDate(LocalDate.now());
        asset.setStatus(Asset.Status.AVAILABLE);
        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", saved.getAssetId(),
                "CREATE", null, "Asset added"
        );

        return saved;
    }


    // 3 // list all assets
    public List<Asset> getAllAssets() {
        List<Asset> assets = repository.findAll();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "READ_ALL", null, "Fetched all assets"
        );

        return assets;
    }

    // 4 // search by ID
    public Asset getAssetById(Long id) {
        Asset asset = repository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found: " + id));

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "READ", null, "Asset retrieved by ID"
        );

        return asset;
    }

    // 5 // search by title // NO EXCEPTION REQUIRED
    public List<Asset> searchByTitle(String title) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> a.getTitle() != null &&
                        a.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "SEARCH_TITLE", null, title
        );

        return result;
    }







    //Edit Assets
    public Asset updateAsset(Asset asset) {
        return repository.save(asset);
    }


    // 14 // update full asset
    public Asset editAsset(Long id, Asset updatedAsset) {

        Asset existing = getAssetById(id);
        String oldValue = existing.toString();

        existing.setTitle(updatedAsset.getTitle());
        existing.setCategory(updatedAsset.getCategory());
        existing.setSerialNumber(updatedAsset.getSerialNumber());
        if (updatedAsset.getAcquisitionDate() != null) {
            existing.setAcquisitionDate(updatedAsset.getAcquisitionDate());
        }
        existing.setCost(updatedAsset.getCost());
        existing.setLocation(updatedAsset.getLocation());
        existing.setCondition(updatedAsset.getCondition());
        existing.setPhotoPath(updatedAsset.getPhotoPath());
        existing.setStatus(updatedAsset.getStatus());

        Asset saved = repository.save(existing);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_FULL",
                oldValue,
                "Updated Asset"
        );

        return saved;
    }

    // 15 // update title
    public Asset updateTitle(Long id, String title) {
        Asset asset = getAssetById(id);

        String old = asset.getTitle();
        asset.setTitle(title);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_TITLE", old, title
        );

        return saved;
    }





    // 24 // delete asset // REMOVE
    @Transactional
    public void deleteAsset(Long id) {

        Asset asset = getAssetById(id);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "DELETE",
                asset.toString(),
                null
        );

        repository.deleteById(id);
    }

    public List<Asset> getAvailableAssets() {

        List<Asset> assets =
                repository.findByStatus(Asset.Status.AVAILABLE);

        auditLogService.createAuditLog(
                null,
                "ASSET",
                null,
                "FILTER_STATUS",
                null,
                Asset.Status.AVAILABLE.name()
        );

        return assets;
    }

    //USED
    @Transactional
    public void retireAsset(Long assetId) {

        Asset asset = repository.findById(assetId)
                .orElseThrow(() ->
                        new AssetNotFoundException(
                                "Asset with ID " + assetId + " not found."
                        ));

        String oldStatus = asset.getStatus().name();

        asset.setStatus(Asset.Status.RETIRED);

        repository.save(asset);

        auditLogService.createAuditLog(
                null,
                "ASSET",
                assetId,
                "RETIRE",
                oldStatus,
                Asset.Status.RETIRED.name()
        );
        System.out.println("RETIRE ASSET CALLED: " + assetId);
    }

    //USED
    //create an asset used both by the manager and admin
    @Transactional
    public Asset createAsset(AssetRequestDTO dto, MultipartFile imageFile) {

        if (repository.existsBySerialNumber(dto.getSerialNumber())) {
            throw new AssetAlreadyExistsException( "Asset with serial number " +
                    dto.getSerialNumber() + " already exists"
            );
        }
        Asset asset = new Asset();

        asset.setTitle(dto.getTitle());
        asset.setCategory(dto.getCategory());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setAcquisitionDate(dto.getAcquisitionDate());
        asset.setCost(dto.getCost());
        asset.setLocation(dto.getLocation());
        asset.setCondition(dto.getAssetCondition());
        asset.setCreatedAt(LocalDateTime.now());
        asset.setStatus(Asset.Status.AVAILABLE);

        if (!imageFile.isEmpty()) {
            try {
                String fileName =
                        System.currentTimeMillis() + "_" +
                                imageFile.getOriginalFilename();

                String uploadPath =
                        System.getProperty("user.dir") + "/src/main/webapp/uploads/";

                File uploadDir = new File(uploadPath);
                uploadDir.mkdirs();

                System.out.println("Saving image to: " + uploadPath + fileName);
                imageFile.transferTo(new File(uploadPath + fileName));

                asset.setPhotoPath("/uploads/" + fileName);

            } catch (IOException e) {
                throw new RuntimeException("Image upload failed");
            }
        }
        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null,
                "ASSET",
                saved.getAssetId(),
                "CREATE",
                null,
                "Asset created"
        );


        return saved;
    }


    //actuall working search method
    public List<Asset> searchAssets(
            String keyword,
            String location,
            String condition,
            String status) {

        return repository.findAll()
                .stream()
                .filter(a -> {

                    boolean matchesKeyword =
                            keyword == null || keyword.isBlank() ||
                                    (a.getTitle() != null && a.getTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                                    (a.getCategory() != null && a.getCategory().toLowerCase().contains(keyword.toLowerCase())) ||
                                    (a.getSerialNumber() != null && a.getSerialNumber().toLowerCase().contains(keyword.toLowerCase())) ||
                                    (a.getLocation() != null && a.getLocation().toLowerCase().contains(keyword.toLowerCase())) ||
                                    (a.getCondition() != null && a.getCondition().toLowerCase().contains(keyword.toLowerCase())) ||
                                    (a.getStatus() != null && a.getStatus().name().toLowerCase().contains(keyword.toLowerCase()));

                    boolean matchesLocation =
                            location == null || location.isBlank() ||
                                    location.equalsIgnoreCase(a.getLocation());

                    boolean matchesCondition =
                            condition == null || condition.isBlank() ||
                                    condition.equalsIgnoreCase(a.getCondition());

                    boolean matchesStatus =
                            status == null || status.isBlank() ||
                                    a.getStatus().name().equalsIgnoreCase(status);

                    return matchesKeyword &&
                            matchesLocation &&
                            matchesCondition &&
                            matchesStatus;
                })
                .toList();
    }

    public List<String> getAllLocations() {

        return repository.findAll()
                .stream()
                .map(Asset::getLocation)
                .filter(location -> location != null && !location.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    public List<String> getAllConditions() {

        return repository.findAll()
                .stream()
                .map(Asset::getCondition)
                .filter(condition -> condition != null && !condition.isBlank())
                .distinct()
                .sorted()
                .toList();
    }
}


