package com.example.demo.repository;

import com.example.demo.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    long countByStatus(Asset.Status status);

    List<Asset> findByStatus(Asset.Status status);
}
//changed to long
