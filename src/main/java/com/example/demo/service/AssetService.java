package com.example.demo.service;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.User;
import com.example.demo.exception.AssetAlreadyExistsException;
import com.example.demo.exception.AssetNotFoundException;
import com.example.demo.exception.FileUploadException;
import com.example.demo.exception.InvalidAssetActionException;
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
    @Transactional
    public Asset addAsset(Asset asset, User currentUser) {
        asset.setCreatedAt(LocalDateTime.now());
        asset.setAcquisitionDate(LocalDate.now());
        asset.setStatus(Asset.Status.AVAILABLE);
        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                currentUser, "ASSET", saved.getAssetId(),
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

    // 14 // update full asset
    public Asset editAsset(Long id, Asset updatedAsset, User currentUser) {

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
        existing.setStatus(updatedAsset.getStatus());

        Asset saved = repository.save(existing);

        auditLogService.createAuditLog(
                currentUser, "ASSET", id,
                "UPDATE_FULL",
                oldValue,
                "Updated Asset"
        );

        return saved;
    }

    // 24 // delete asset // REMOVE
    @Transactional
    public void deleteAsset(Long id, User currentUser) {

        Asset asset = getAssetById(id);

        auditLogService.createAuditLog(
                currentUser,
                "ASSET",
                id,
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
    public void retireAsset(Long assetId, User currentUser) {

        Asset asset = repository.findById(assetId)
                .orElseThrow(() ->
                        new AssetNotFoundException(
                                "Asset with ID " + assetId + " not found."
                        ));

        if (asset.getStatus() == Asset.Status.LOANED) {
            throw new InvalidAssetActionException(
                    "Cannot retire an asset that is currently loaned."
            );
        }

        if (asset.getStatus() == Asset.Status.RETIRED) {
            throw new InvalidAssetActionException(
                    "Asset is already retired."
            );
        }

        String oldStatus = asset.getStatus().name();

        asset.setStatus(Asset.Status.RETIRED);

        repository.save(asset);

        auditLogService.createAuditLog(
                currentUser,
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
    public Asset createAsset(AssetRequestDTO dto, MultipartFile imageFile, User currentUser) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new InvalidAssetActionException("Asset title is required.");
        }

        if (dto.getCategory() == null || dto.getCategory().isBlank()) {
            throw new InvalidAssetActionException("Asset category is required.");
        }

        if (dto.getSerialNumber() == null || dto.getSerialNumber().isBlank()) {
            throw new InvalidAssetActionException("Asset serial number is required.");
        }

        if (dto.getCost() == null || dto.getCost().doubleValue() <= 0) {
            throw new InvalidAssetActionException("Asset cost cannot be negative.");
        }

        if (repository.existsBySerialNumber(dto.getSerialNumber())) {
            throw new AssetAlreadyExistsException( "Asset with serial number " +
                    dto.getSerialNumber() + " already exists"
            );
        }
        if (dto.getAcquisitionDate() != null &&
                dto.getAcquisitionDate().isAfter(LocalDate.now())) {

            throw new InvalidAssetActionException(
                    "Acquisition date cannot be in the future."
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

        //only allows png and jpeg
        if (imageFile != null && !imageFile.isEmpty()) {

            String contentType = imageFile.getContentType();

            if (contentType == null ||
                    !(contentType.equals("image/png") ||
                            contentType.equals("image/jpeg"))) {

                throw new FileUploadException(
                        "Only JPG and PNG images are allowed."
                );
            }

            try {
                String fileName =
                        System.currentTimeMillis() + "_" +
                                imageFile.getOriginalFilename();

                String uploadPath =
                        System.getProperty("user.dir") + "/src/main/webapp/uploads/";

                File uploadDir = new File(uploadPath);
                uploadDir.mkdirs();

                imageFile.transferTo(new File(uploadPath + fileName));

                asset.setPhotoPath("/uploads/" + fileName);

            } catch (IOException e) {
                throw new FileUploadException("Image upload failed. Please try again.");
            }
        }
        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                currentUser,
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


