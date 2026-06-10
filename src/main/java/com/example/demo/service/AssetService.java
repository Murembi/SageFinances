package com.example.demo.service;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

    @Service
    public class AssetService {

        private final AssetRepository repository;

        public AssetService(AssetRepository repository) {
            this.repository = repository;
        }

        // add multiple assets //2
        public List<Asset> addMultipleAssets(List<Asset> assets) {
            assets.forEach(a -> a.setCreatedAt(LocalDateTime.now()));
            return repository.saveAll(assets);
        }

        // list all assets //3
        public List<Asset> getAllAssets() {
            return repository.findAll();
        }

        // search by ID //4
        public Asset getAssetById(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));
        }

        // search by title //5
        public Asset searchBySerial(String serialNumber) {
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getSerialNumber().equals(serialNumber))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Asset not found with serial: " + serialNumber));
        }

        // search by serial number //6
        public List<Asset> searchByTitle(String title) {
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getTitle() != null && a.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .toList();
        }

        // search by creation time //7
        public List<Asset> searchByCreatedAt(LocalDateTime createdAt) {
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getCreatedAt() != null && a.getCreatedAt().equals(createdAt))
                    .toList();
        }

        // filter by category //8
        public List<Asset> filterByCategory(String category) {
            return repository.findAll()
                    .stream()
                    .filter(a -> category.equalsIgnoreCase(a.getCategory()))
                    .toList();
        }

        // filter by acquisition date //9
        public List<Asset> filterByAcquisitionDate(java.time.LocalDate date) {
            return repository.findAll()
                    .stream()
                    .filter(a -> date.equals(a.getAcquisitionDate()))
                    .toList();
        }

        // filter by cost //10
        public List<Asset> filterByCost(java.math.BigDecimal cost) {
            return repository.findAll()
                    .stream()
                    .filter(a -> cost.equals(a.getCost()))
                    .toList();
        }

        // filter by location //11
        public List<Asset> filterByLocation(String location) {
            return repository.findAll()
                    .stream()
                    .filter(a -> location.equalsIgnoreCase(a.getLocation()))
                    .toList();
        }

        // filter by condition //12
        public List<Asset> filterByCondition(String condition) {
            return repository.findAll()
                    .stream()
                    .filter(a -> condition.equalsIgnoreCase(a.getCondition()))
                    .toList();
        }

        // filter by status //13
        public List<Asset> filterByStatus(Asset.Status status) {
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getStatus() == status)
                    .toList();
        }

        // update entire asset //14
        public Asset editAsset(Long id, Asset updatedAsset) {
            Asset existing = getAssetById(id);

            existing.setTitle(updatedAsset.getTitle());
            existing.setCategory(updatedAsset.getCategory());
            existing.setSerialNumber(updatedAsset.getSerialNumber());
            existing.setAcquisitionDate(updatedAsset.getAcquisitionDate());
            existing.setCost(updatedAsset.getCost());
            existing.setLocation(updatedAsset.getLocation());
            existing.setCondition(updatedAsset.getCondition());
            existing.setPhotoPath(updatedAsset.getPhotoPath());
            existing.setStatus(updatedAsset.getStatus());

            return repository.save(existing);
        }

        // update title //15
        public Asset updateTitle(Long id, String title) {
            Asset asset = getAssetById(id);
            asset.setTitle(title);
            return repository.save(asset);
        }

        // update category //16
        public Asset updateCategory(Long id, String category) {
            Asset asset = getAssetById(id);
            asset.setCategory(category);
            return repository.save(asset);
        }

        // update serial number //17
        public Asset updateSerialNumber(Long id, String serialNumber) {
            Asset asset = getAssetById(id);
            asset.setSerialNumber(serialNumber);
            return repository.save(asset);
        }

        // update acquisition date //18
        public Asset updateAcquisitionDate(Long id, LocalDate date) {
            Asset asset = getAssetById(id);
            asset.setAcquisitionDate(date);
            return repository.save(asset);
        }

        // update cost //19
        public Asset updateCost(Long id, BigDecimal cost) {
            Asset asset = getAssetById(id);
            asset.setCost(cost);
            return repository.save(asset);
        }

        // update location //20
        public Asset updateLocation(Long id, String location) {
            Asset asset = getAssetById(id);
            asset.setLocation(location);
            return repository.save(asset);
        }

        // update condition //21
        public Asset updateCondition(Long id, String condition) {
            Asset asset = getAssetById(id);
            asset.setCondition(condition);
            return repository.save(asset);
        }

        // update photo path //22
        public Asset updatePhotoPath(Long id, String photoPath) {
            Asset asset = getAssetById(id);
            asset.setPhotoPath(photoPath);
            return repository.save(asset);
        }

        // update status //23
        public Asset updateStatus(Long id, Asset.Status status) {
            Asset asset = getAssetById(id);
            asset.setStatus(status);
            return repository.save(asset);
        }

        // delete asset //24
        public void deleteAsset(Long id) {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Asset not found with id: " + id);
            }
            repository.deleteById(id);
        }
    }


