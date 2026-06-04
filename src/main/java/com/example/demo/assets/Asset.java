package com.example.demo.assets;

import java.time.LocalDate;

public class Asset {
   private int assetId;
   private String Title;
   private String Category;
   private String serialNumber;
   private LocalDate acquisitionDate;
   private long cost;
   private String location;
   private String condition;
   private String photo;

   public int getAssetId() {
      return assetId;
   }

   public void setAssetId(int assetId) {
      this.assetId = assetId;
   }

   public String getTitle() {
      return Title;
   }

   public void setTitle(String title) {
      this.Title = title;
   }

   public String getCategory() {
      return Category;
   }

   public void setCategory(String category) {
      this.Category = category;
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

   public long getCost() {
      return cost;
   }

   public void setCost(long cost) {
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

   public String getPhoto() {
      return photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }
}




