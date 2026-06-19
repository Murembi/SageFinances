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
            assets = service.searchByTitle(keyword);
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
        return "redirect:/assets";
    }

    // =========================
    // DELETE ASSET (FROM JSP BUTTON)
    // =========================
    @PostMapping("/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        service.deleteAsset(id);
        return "redirect:/assets";
    }
}