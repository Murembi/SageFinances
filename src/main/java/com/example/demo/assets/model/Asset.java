package com.example.demo.assets.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asset") //Comment to check
public class Asset {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int assetId;

   @Column(nullable = false, length = 150)   // making sure were not sending wrong data to the db
   private String title;

   @Column(nullable = false, length = 100)
   private String category;

   @Column(nullable = false, unique = true, length = 100)  // every serial number has to be unique
   private String serialNumber;

   private LocalDate acquisitionDate;

   private BigDecimal cost;

   private String location;

   @Column(name = "condition") //added for safety
   private String condition;

   @Column(name = "photo_path")
   private String photoUpload;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private AssetStatus status;


   public enum AssetStatus {
      AVAILABLE,
      LOANED,
      DAMAGED,
      RETIRED
   }

   public int getAssetId() {
      return assetId;
   }

   public void setAssetId(int assetId) {
      this.assetId = assetId;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getSerialNumber() {
      return serialNumber;
   }

   public void setSerialNumber(String serialNumber) {
      this.serialNumber = serialNumber;
   }

   public LocalDate getAcquisitionDate() {
      return acquisitionDate;
   }

   public void setAcquisitionDate(LocalDate acquisitionDate) {
      this.acquisitionDate = acquisitionDate;
   }

   public BigDecimal getCost() {
      return cost;
   }

   public void setCost(BigDecimal cost) {
      this.cost = cost;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getCondition() {
      return condition;
   }

   public void setCondition(String condition) {
      this.condition = condition;
   }

   public String getPhotoUpload() {
      return photoUpload;
   }

   public void setPhotoUpload(String photoUpload) {
      this.photoUpload = photoUpload;
   }

   public AssetStatus getStatus() {
      return status;
   }

   public void setStatus(AssetStatus status) {
      this.status = status;
   }


}




