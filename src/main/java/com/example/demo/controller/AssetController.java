package com.example.demo.controller;

import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import org.springframework.http.ResponseEntity;
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

    // add 1 asset //1//DONE
    @PostMapping
    public Asset createAsset(@RequestBody Asset asset){
        return service.addAsset(asset);
    }

    // add multiple assets //2// Has a bug that skips id's if trying to enter duplicate assets
    @PostMapping("/bulk")
    public ResponseEntity<?> createAssets(@RequestBody List<Asset> assets){
        service.addMultipleAssets(assets);
        return ResponseEntity.ok("Assets created successfully");
    }
    // list all assets //3//DONE
    @GetMapping
    public List<Asset> getAllAssets() {
        return service.getAllAssets();
    }

    // search by ID //4//DONE
    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Long id){
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

    // search by creation time //7//DONE but make sure to use right formatting in postman
    @GetMapping("/search/created")
    public List<Asset> searchByCreatedAt(@RequestParam LocalDateTime createdAt) {
        return service.searchByCreatedAt(createdAt);
    }

    // filter by category //8//DONE
    @GetMapping("/filter/category")
    public List<Asset> filterByCategory(@RequestParam String category) {
        return service.filterByCategory(category);
    }

    // filter by acquisition date //9//DONE works with url in postman
    @GetMapping("/filter/acquisition-date")
    public List<Asset> filterByAcquisitionDate(@RequestParam LocalDate date) {
        return service.filterByAcquisitionDate(date);
    }

    // filter by cost //10//DONE
    @GetMapping("/filter/cost")
    public List<Asset> filterByCost(@RequestParam BigDecimal cost) {
        return service.filterByCost(cost);
    }

    // filter by location //11//DONE
    @GetMapping("/filter/location")
    public List<Asset> filterByLocation(@RequestParam String location) {
        return service.filterByLocation(location);
    }

    // filter by condition //12//DONE
    @GetMapping("/filter/condition")
    public List<Asset> filterByCondition(@RequestParam String condition) {
        return service.filterByCondition(condition);
    }

    // filter by status //13//DONE
    @GetMapping("/filter/status")
    public List<Asset> filterByStatus(@RequestParam Asset.Status status) {
        return service.filterByStatus(status);
    }

    // update entire asset //14//DONE
    @PutMapping("/{id}")
    public Asset updateAsset(
            @PathVariable Long id,
            @RequestBody Asset asset) {
        return service.editAsset(id, asset);
    }

    // update title //15//DONE
    @PatchMapping("/{id}/title")
    public Asset updateTitle(@PathVariable Long id, @RequestParam String title) {
        return service.updateTitle(id, title);
    }

    // update category //16//DONE
    @PatchMapping("/{id}/category")
    public Asset updateCategory(@PathVariable Long id, @RequestParam String category) {
        return service.updateCategory(id, category);
    }

    // update serial //17//DONE
    @PatchMapping("/{id}/serial")
    public Asset updateSerial(@PathVariable Long id, @RequestParam String serialNumber) {
        return service.updateSerialNumber(id, serialNumber);
    }

    // update acquisition-date //18//DONE
    @PatchMapping("/{id}/acquisition-date")
    public Asset updateAcquisitionDate(@PathVariable Long id, @RequestParam LocalDate date) {
        return service.updateAcquisitionDate(id, date);
    }

    // update cost //19//DONE
    @PatchMapping("/{id}/cost")
    public Asset updateCost(@PathVariable Long id, @RequestParam BigDecimal cost) {
        return service.updateCost(id, cost);
    }

    // update location //20//DONE
    @PatchMapping("/{id}/location")
    public Asset updateLocation(@PathVariable Long id, @RequestParam String location) {
        return service.updateLocation(id, location);
    }

    // update condition//21//DONE
    @PatchMapping("/{id}/condition")
    public Asset updateCondition(@PathVariable Long id, @RequestParam String condition) {
        return service.updateCondition(id, condition);
    }

    //update photo //22//DONE
    @PatchMapping("/{id}/photo")
    public Asset updatePhoto(@PathVariable Long id, @RequestParam String photoPath) {
        return service.updatePhotoPath(id, photoPath);
    }

    // update status of asset//23//DONE
    @PatchMapping("/{id}/status")
    public Asset updateStatus(
            @PathVariable Long id,
            @RequestParam Asset.Status status) {
        return service.updateStatus(id, status);
    }

    // delete asset //24//DONE but when deleting for example id 1, id 2 does not become id 1 and so forth
    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable Long id) {
        service.deleteAsset(id);
    }

}
