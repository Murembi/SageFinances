package com.example.demo.service;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.repository.AssetRepository;
import com.example.demo.entity.Asset;
import com.example.demo.exception.AssetAlreadyExistsException;
import com.example.demo.exception.AssetNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    //ASSET Validation
    public void addAssetFromDTO(AssetRequestDTO dto) {

        Asset asset = new Asset();

        asset.setTitle(dto.getTitle());
        asset.setCategory(dto.getCategory());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setAcquisitionDate(dto.getAcquisitionDate());
        asset.setCost(dto.getCost());
        asset.setLocation(dto.getLocation());
        asset.setCondition(dto.getAssetCondition());
        asset.setPhotoPath(dto.getPhotoPath());
        asset.setStatus(
                Asset.Status.valueOf(dto.getStatus().toUpperCase()));
        repository.save(asset);
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
    public Asset createAsset(AssetRequestDTO dto) {

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

        // DTO field  Entity field
        asset.setCondition(dto.getAssetCondition());

        asset.setPhotoPath(dto.getPhotoPath());

        asset.setCreatedAt(LocalDateTime.now());

        // Force new assets to start as AVAILABLE
        asset.setStatus(Asset.Status.AVAILABLE);

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
    public List<Asset> searchAssets(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllAssets();
        }

        String k = keyword.toLowerCase();

        return repository.findAll()
                .stream()
                .filter(a ->
                        (a.getTitle() != null && a.getTitle().toLowerCase().contains(k)) ||
                                (a.getCategory() != null && a.getCategory().toLowerCase().contains(k)) ||
                                (a.getSerialNumber() != null && a.getSerialNumber().toLowerCase().contains(k)) ||
                                (a.getLocation() != null && a.getLocation().toLowerCase().contains(k)) ||
                                (a.getCondition() != null && a.getCondition().toLowerCase().contains(k)) ||
                                (a.getStatus() != null && a.getStatus().name().toLowerCase().contains(k))
                )
                .toList();
    }
}


