package com.example.demo.controller;

import com.example.demo.entity.AssetEntity;
import com.example.demo.service.AssetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*") // allows frontend access (you can restrict later)
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    // =========================
    // 1. CREATE ASSET
    // =========================
    @PostMapping
    public AssetEntity createAsset(@RequestBody AssetEntity asset) {
        return service.addAsset(asset);
    }

    // =========================
    // 2. GET ALL ASSETS
    // =========================
    @GetMapping
    public List<AssetEntity> getAllAssets() {
        return service.getAllAssets();
    }

    // =========================
    // 3. EDIT ASSET
    // =========================
    @PutMapping("/{id}")
    public AssetEntity updateAsset(
            @PathVariable Integer id,
            @RequestBody AssetEntity asset
    ) {
        return service.editAsset(id, asset);
    }

    // =========================
    // 4. DELETE ASSET
    // =========================
    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable Integer id) {
        service.deleteAsset(id);
    }

    // =========================
    // 5. RETIRE ASSET
    // =========================
    @PutMapping("/{id}/retire")
    public AssetEntity retireAsset(@PathVariable Integer id) {
        return service.retireAsset(id);
    }

    // =========================
    // 6. SEARCH BY NAME
    // =========================
    @GetMapping("/search/name")
    public List<AssetEntity> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    // =========================
    // 7. SEARCH BY CATEGORY
    // =========================
    @GetMapping("/search/category")
    public List<AssetEntity> searchByCategory(@RequestParam String category) {
        return service.searchByCategory(category);
    }

    // =========================
    // 8. SEARCH BY STATUS
    // =========================
    @GetMapping("/search/status")
    public List<AssetEntity> searchByStatus(@RequestParam AssetEntity.AssetStatus status) {
        return service.searchByStatus(status);
    }

    // =========================
    // 9. FILTER BY LOCATION
    // =========================
    @GetMapping("/filter/location")
    public List<AssetEntity> filterByLocation(@RequestParam String location) {
        return service.filterByLocation(location);
    }

    // =========================
    // 10. FILTER BY CONDITION
    // =========================
    @GetMapping("/filter/condition")
    public List<AssetEntity> filterByCondition(@RequestParam String condition) {
        return service.filterByCondition(condition);
    }
}
