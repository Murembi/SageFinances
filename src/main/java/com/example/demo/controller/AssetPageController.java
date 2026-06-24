package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;

@Controller
@RequestMapping("/admin/assets")
public class AssetPageController {

    private final AssetService service;

    public AssetPageController(AssetService service) {
        this.service = service;
    }


    @GetMapping
    public String loadAssetPage(Model model, @RequestParam(required = false) String keyword) 
    {

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

    
    @PostMapping("/create")
    public String createAsset(@ModelAttribute Asset asset) {
        service.addAsset(asset);
        return "redirect:/admin/assets";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model) {

        Asset asset = service.getAssetById(id);

        model.addAttribute("asset", asset);
        model.addAttribute("assets", service.getAllAssets());

        return "adminAsset";
    }


    @PostMapping("/update")
    public String updateAsset(@ModelAttribute Asset asset) {

        service.editAsset(asset.getAssetId(), asset);

        return "redirect:/admin/assets";
    }

    
    @PostMapping("/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        service.deleteAsset(id);
        return "redirect:/admin/assets";
    }
}