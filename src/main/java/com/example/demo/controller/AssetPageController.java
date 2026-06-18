package com.example.demo.controller;

import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jsp/assets")
public class AssetPageController {

    private final AssetService service;

    public AssetPageController(AssetService service) {
        this.service = service;
    }

    // LOAD PAGE
    @GetMapping
    public String assetPage(Model model) {
        model.addAttribute("assets", service.getAllAssets());
        return "adminAsset"; // assets.jsp
    }

    // CREATE (FORM SUBMIT)
    @PostMapping("/create")
    public String createAsset(@ModelAttribute Asset asset) {
        service.addAsset(asset);
        return "redirect:/jsp/adminAsset";
    }

    // DELETE (JSP BUTTON)
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteAsset(id);
        return "redirect:/jsp/adminAsset";
    }
}