package com.example.demo.controller;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/assets")
public class AssetPageController {

    private final AssetService service;

    public AssetPageController(AssetService service) {
        this.service = service;
    }

    // LOAD ASSET PAGE (JSP)

    @GetMapping
    public String loadAssetPage(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String status) {

        List<Asset> assets =
                service.searchAssets(keyword, location, condition, status);

        model.addAttribute("status", status);
        model.addAttribute("statuses", Asset.Status.values());
        model.addAttribute("assets", assets);

        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        model.addAttribute("condition", condition);

        model.addAttribute("locations", service.getAllLocations());
        model.addAttribute("conditions", service.getAllConditions());

        return "adminAsset";
    }

    // CREATE ASSET (FROM JSP FORM)
    @PostMapping("/create")
    public String createAsset(@ModelAttribute AssetRequestDTO dto,
                              @RequestParam("imageFile") MultipartFile imageFile) {

        service.createAsset(dto, imageFile);

        return "redirect:/admin/assets";
    }
    // EDIT ASSET (FROM JSP BUTTON)
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id,
                               Model model) {

        Asset asset = service.getAssetById(id);

        model.addAttribute("asset", asset);
        model.addAttribute("assets", service.getAllAssets());

        return "adminAsset";
    }

    // UPDATE ASSET (FROM JSP BUTTON)
    @PostMapping("/update")
    public String updateAsset(@ModelAttribute Asset asset) {

        service.editAsset(asset.getAssetId(), asset);

        return "redirect:/admin/assets";
    }

    // REMOVE CANT DELETE AN ASSET
    // DELETE ASSET (FROM JSP BUTTON)
    @PostMapping("/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        service.deleteAsset(id);
        return "redirect:/admin/assets";
    }
}