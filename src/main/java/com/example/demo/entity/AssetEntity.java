package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "asset") //Comment to
public class AssetEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) //yea dawg
   @Column(name = "asset_id")
   private Integer assetId;

   @Column(name = "title",nullable = false, length = 150)   // making sure were not sending wrong data to the db
   private String title;

   @Column(name = "category",nullable = false, length = 100)
   private String category;

   @Column(name = "serial_number",nullable = false, unique = true, length = 100)  // every serial number has to be unique
   private String serialNumber;

   @Column(name = "acquisition_date",nullable = false)
   private LocalDate acquisitionDate;

   @Column(name = "cost",nullable = false)
   private BigDecimal cost;

   @Column(name = "location",nullable = false)
   private String location;

   @Column(name = "condition", nullable = true) //added for safety
   private String condition;

   @Column(name = "photo_path", nullable = false)
   private String photoPath;

   @Column(name = "created_at", insertable = false, updatable = false)
   private LocalDateTime createdAt;

   @Column(name = "status",nullable = false)
   @Enumerated(EnumType.STRING)
   private AssetStatus status = AssetStatus.AVAILABLE;




   public enum AssetStatus {
      AVAILABLE,
      LOANED,
      DAMAGED,
      RETIRED
   }

   public Integer getAssetId() {
      return assetId;
   }

   public void setAssetId(Integer assetId) {
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

   public String getPhotoPath() {
      return photoPath;
   }

   public void setPhotoPath(String photoPath) {
      this.photoPath = photoPath;
   }

   public AssetStatus getStatus() {
      return status;
   }

   public void setStatus(AssetStatus status) {
      this.status = status;
   }


}




