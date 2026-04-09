package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    boolean existsByAssetCode(String assetCode);
}
