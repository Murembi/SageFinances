package com.example.demo.service;

import com.example.demo.entity.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssetService {

    private final AssetRepository repository;

    public AssetService(AssetRepository repository) {
        this.repository = repository;
    }

//     =====================
//     ADD SINGLE ASSET
//     =====================
        public Asset addAsset (Asset asset){

            if (asset.getStatus() == null) {
                asset.setStatus(Asset.Status.AVAILABLE);
            }

            return repository.save(asset);
        }

        // =====================
        // ADD MULTIPLE ASSETS
        // =====================
        public List<Asset> addMultipleAssets (List < Asset > assets) {

            assets.forEach(asset -> {
                if (asset.getStatus() == null) {
                    asset.setStatus(Asset.Status.AVAILABLE);
                }
            });

            return repository.saveAll(assets);
        }

        // =====================
        // GET ALL ASSETS
        // =====================
        public List<Asset> getAllAssets () {
            return repository.findAll();
        }

        // =====================
        // GET ONE ASSET
        // =====================
        public Asset getAssetById (Integer id){
            return repository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Asset not found with id: " + id));
        }

        // =====================
        // EDIT ASSET
        // =====================
        public Asset editAsset (Integer id, Asset updated){

            Asset existing = getAssetById(id);

            existing.setTitle(updated.getTitle());
            existing.setCategory(updated.getCategory());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setAcquisitionDate(updated.getAcquisitionDate());
            existing.setCost(updated.getCost());
            existing.setLocation(updated.getLocation());
            existing.setCondition(updated.getCondition());
            existing.setPhotoPath(updated.getPhotoPath());
            existing.setStatus(updated.getStatus());

            return repository.save(existing);
        }

        // =====================
        // DELETE ASSET
        // =====================
        public void deleteAsset (Integer id){
            repository.deleteById(id);
        }

        // =====================
        // SEARCH BY TITLE
        // =====================
        public List<Asset> searchByTitle (String title){
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getTitle() != null &&
                            a.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .toList();
        }

        // =====================
        // FILTER BY CATEGORY
        // =====================
        public List<Asset> filterByCategory (String category){
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getCategory() != null &&
                            a.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        // =====================
        // FILTER BY STATUS
        // =====================
        public List<Asset> filterByStatus (Asset.Status status){
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getStatus() == status)
                    .toList();
        }

        // =====================
        // FILTER BY LOCATION
        // =====================
        public List<Asset> filterByLocation (String location){
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getLocation() != null &&
                            a.getLocation().equalsIgnoreCase(location))
                    .toList();
        }

        // =====================
        // FILTER BY CONDITION
        // =====================
        public List<Asset> filterByCondition (String condition){
            return repository.findAll()
                    .stream()
                    .filter(a -> a.getCondition() != null &&
                            a.getCondition().equalsIgnoreCase(condition))
                    .toList();
        }

        // =====================
        // FILTER BY ACQUISITION DATE
        // =====================
        public List<Asset> filterByAcquisitionDate (LocalDate date){
            return repository.findAll()
                    .stream()
                    .filter(a -> date.equals(a.getAcquisitionDate()))
                    .toList();
        }
    }

