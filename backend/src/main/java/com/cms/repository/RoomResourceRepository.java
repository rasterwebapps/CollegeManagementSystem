package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.RoomResource;

public interface RoomResourceRepository extends JpaRepository<RoomResource, Long> {
}
