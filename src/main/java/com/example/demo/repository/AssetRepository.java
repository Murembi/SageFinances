package com.example.demo.repository;

import com.example.demo.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetEntity, Integer> {
}
