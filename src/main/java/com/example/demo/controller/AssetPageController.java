package com.example.demo.controller;

import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/assets")
public class AssetPageController {

    private final AssetService service;

    public AssetPageController(AssetService service) {
        this.service = service;
    }

    // =========================
    // LOAD ASSET PAGE (JSP)
    // =========================
    @GetMapping
    public String loadAssetPage(Model model,
                                @RequestParam(required = false) String keyword) {

        List<Asset> assets;

        if (keyword != null && !keyword.trim().isEmpty()) {
            assets = service.searchAssets(keyword);
        } else {
            assets = service.getAllAssets();
        }

        model.addAttribute("assets", assets);
        model.addAttribute("keyword", keyword);

        return "adminAsset";
    }

    // =========================
    // CREATE ASSET (FROM JSP FORM)
    // =========================
    @PostMapping("/create")
    public String createAsset(@ModelAttribute Asset asset) {
        service.addAsset(asset);
        return "redirect:/admin/assets";
    }
    // =========================
    // EDIT ASSET (FROM JSP BUTTON)
    // =========================
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id,
                               Model model) {

        Asset asset = service.getAssetById(id);

        model.addAttribute("asset", asset);
        model.addAttribute("assets", service.getAllAssets());

        return "adminAsset";
    }

    // =========================
    // UPDATE ASSET (FROM JSP BUTTON)
    // =========================
    @PostMapping("/update")
    public String updateAsset(@ModelAttribute Asset asset) {

        service.editAsset(asset.getAssetId(), asset);

        return "redirect:/admin/assets";
    }

    // REMOVE CANT DELETE AN ASSET
    // =========================
    // DELETE ASSET (FROM JSP BUTTON)
    // =========================
    @PostMapping("/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        service.deleteAsset(id);
        return "redirect:/admin/assets";
    }
}