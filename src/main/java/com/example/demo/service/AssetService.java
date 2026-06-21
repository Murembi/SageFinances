package com.example.demo.service;

import com.example.demo.entity.Asset;
import com.example.demo.entity.User;
import com.example.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Asset addAsset(Asset asset) {
        asset.setCreatedAt(LocalDateTime.now());

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", saved.getAssetId(),
                "CREATE", null, saved.toString()
        );

        return saved;
    }

    // 2 // add multiple assets
    public List<Asset> addMultipleAssets(List<Asset> assets) {
        assets.forEach(a -> a.setCreatedAt(LocalDateTime.now()));

        List<Asset> saved = repository.saveAll(assets);

        saved.forEach(a ->
                auditLogService.createAuditLog(
                        null, "ASSET", a.getAssetId(),
                        "CREATE", null, a.toString()
                )
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
                .orElseThrow(() -> new RuntimeException("Asset not found: " + id));

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "READ", null, asset.toString()
        );

        return asset;
    }

    // 5 // search by title
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

    // 6 // search by serial number
    public Asset searchBySerial(String serialNumber) {
        Asset asset = repository.findAll()
                .stream()
                .filter(a -> a.getSerialNumber().equals(serialNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        auditLogService.createAuditLog(
                null, "ASSET", asset.getAssetId(),
                "SEARCH_SERIAL", null, serialNumber
        );

        return asset;
    }

    // 7 // search by created time
    public List<Asset> searchByCreatedAt(LocalDateTime createdAt) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> createdAt.equals(a.getCreatedAt()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "SEARCH_CREATED_AT", null, createdAt.toString()
        );

        return result;
    }

    // 8 // filter by category
    public List<Asset> filterByCategory(String category) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> category.equalsIgnoreCase(a.getCategory()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "FILTER_CATEGORY", null, category
        );

        return result;
    }

    // 9 // filter by acquisition date
    public List<Asset> filterByAcquisitionDate(LocalDate date) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> date.equals(a.getAcquisitionDate()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "FILTER_ACQUISITION_DATE", null, date.toString()
        );

        return result;
    }

    // 10 // filter by cost
    public List<Asset> filterByCost(BigDecimal cost) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> cost.equals(a.getCost()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "FILTER_COST", null, cost.toString()
        );

        return result;
    }

    // 11 // filter by location
    public List<Asset> filterByLocation(String location) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> location.equalsIgnoreCase(a.getLocation()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "FILTER_LOCATION", null, location
        );

        return result;
    }

    // 12 // filter by condition
    public List<Asset> filterByCondition(String condition) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> condition.equalsIgnoreCase(a.getCondition()))
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "FILTER_CONDITION", null, condition
        );

        return result;
    }

    // 13 // filter by status
    public List<Asset> filterByStatus(Asset.Status status) {
        List<Asset> result = repository.findAll()
                .stream()
                .filter(a -> a.getStatus() == status)
                .toList();

        auditLogService.createAuditLog(
                null, "ASSET", null,
                "FILTER_STATUS", null, status.name()
        );

        return result;
    }

    // 14 // update full asset
    public Asset editAsset(Long id, Asset updatedAsset) {

        Asset existing = getAssetById(id);
        String oldValue = existing.toString();

        existing.setTitle(updatedAsset.getTitle());
        existing.setCategory(updatedAsset.getCategory());
        existing.setSerialNumber(updatedAsset.getSerialNumber());
        existing.setAcquisitionDate(updatedAsset.getAcquisitionDate());
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
                saved.toString()
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

    // 16 // update category
    public Asset updateCategory(Long id, String category) {
        Asset asset = getAssetById(id);

        String old = asset.getCategory();
        asset.setCategory(category);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_CATEGORY", old, category
        );

        return saved;
    }

    // 17 // update serial number
    public Asset updateSerialNumber(Long id, String serialNumber) {
        Asset asset = getAssetById(id);

        String old = asset.getSerialNumber();
        asset.setSerialNumber(serialNumber);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_SERIAL", old, serialNumber
        );

        return saved;
    }

    // 18 // update acquisition date
    public Asset updateAcquisitionDate(Long id, LocalDate date) {
        Asset asset = getAssetById(id);

        LocalDate old = asset.getAcquisitionDate();
        asset.setAcquisitionDate(date);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_ACQUISITION_DATE",
                String.valueOf(old),
                String.valueOf(date)
        );

        return saved;
    }

    // 19 // update cost
    public Asset updateCost(Long id, BigDecimal cost) {
        Asset asset = getAssetById(id);

        BigDecimal old = asset.getCost();
        asset.setCost(cost);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_COST",
                String.valueOf(old),
                String.valueOf(cost)
        );

        return saved;
    }

    // 20 // update location
    public Asset updateLocation(Long id, String location) {
        Asset asset = getAssetById(id);

        String old = asset.getLocation();
        asset.setLocation(location);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_LOCATION", old, location
        );

        return saved;
    }

    // 21 // update condition
    public Asset updateCondition(Long id, String condition) {
        Asset asset = getAssetById(id);

        String old = asset.getCondition();
        asset.setCondition(condition);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_CONDITION", old, condition
        );

        return saved;
    }

    // 22 // update photo
    public Asset updatePhotoPath(Long id, String photoPath) {
        Asset asset = getAssetById(id);

        String old = asset.getPhotoPath();
        asset.setPhotoPath(photoPath);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_PHOTO", old, photoPath
        );

        return saved;
    }

    // 23 // update status
    public Asset updateStatus(Long id, Asset.Status status) {
        Asset asset = getAssetById(id);

        Asset.Status old = asset.getStatus();
        asset.setStatus(status);

        Asset saved = repository.save(asset);

        auditLogService.createAuditLog(
                null, "ASSET", id,
                "UPDATE_STATUS",
                old.name(),
                status.name()
        );

        return saved;
    }

    // 24 // delete asset
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

    public void retireAsset(Long assetId) {

        Asset asset = repository.findById(assetId)
                .orElseThrow(() ->
                        new RuntimeException("Asset not found"));

        asset.setStatus(Asset.Status.RETIRED);

        repository.save(asset);
    }
}


