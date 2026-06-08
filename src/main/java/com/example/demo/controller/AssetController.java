package com.example.demo.controller;

import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    // add 1 asset //1
    @PostMapping
    public Asset createAsset(@RequestBody Asset asset){
        return service.addAsset(asset);
    }

    // add multiple assets //2
    @PostMapping("/bulk")
    public List<Asset> createAssets(@RequestBody List<Asset> assets){
        return service.addMultipleAssets(assets);
    }

    // list all assets //3//DONE
    @GetMapping
    public List<Asset> getAllAssets() {
        return service.getAllAssets();
    }

    // search by ID //4//DONE
    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Integer id){
        return service.getAssetById(id);
    }

    // search by title //5//DONE
    @GetMapping("/search/title")
    public List<Asset> searchByTitle(@RequestParam String title) {
        return service.searchByTitle(title);
    }

    // search by serial number //6//DONE
    @GetMapping("/search/serial")
    public Asset searchBySerialNumber(@RequestParam String serialNumber) {
        return service.searchBySerial(serialNumber);
    }

    // search by creation time //7
    @GetMapping("/search/created")
    public List<Asset> searchByCreatedAt(@RequestParam LocalDateTime createdAt) {
        return service.searchByCreatedAt(createdAt);
    }

    // filter by category //8
    @GetMapping("/filter/category")
    public List<Asset> filterByCategory(@RequestParam String category) {
        return service.filterByCategory(category);
    }

    // filter by acquisition date //9
    @GetMapping("/filter/acquisition-date")
    public List<Asset> filterByAcquisitionDate(@RequestParam LocalDate date) {
        return service.filterByAcquisitionDate(date);
    }

    // filter by cost //10
    @GetMapping("/filter/cost")
    public List<Asset> filterByCost(@RequestParam BigDecimal cost) {
        return service.filterByCost(cost);
    }

    // filter by location //11
    @GetMapping("/filter/location")
    public List<Asset> filterByLocation(@RequestParam String location) {
        return service.filterByLocation(location);
    }

    // filter by condition //12
    @GetMapping("/filter/condition")
    public List<Asset> filterByCondition(@RequestParam String condition) {
        return service.filterByCondition(condition);
    }


    // filter by status //13
    @GetMapping("/filter/status")
    public List<Asset> filterByStatus(@RequestParam Asset.Status status) {
        return service.filterByStatus(status);
    }

    // update entire asset //14//DONE
    @PutMapping("/{id}")
    public Asset updateAsset(
            @PathVariable Integer id,
            @RequestBody Asset asset) {
        return service.editAsset(id, asset);
    }

    // update status of asset//15//DONE
    @PatchMapping("/{id}/status")
    public Asset updateStatus(
            @PathVariable Integer id,
            @RequestParam Asset.Status status) {
        return service.updateStatus(id, status);
    }

    // delete asset //
    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable Integer id) {
        service.deleteAsset(id);
    }

}
