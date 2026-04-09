package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class InfrastructureService {
    private final BuildingRepository buildingRepo;
    private final RoomAllocationRepository allocationRepo;
    private final MaintenanceRequestRepository maintenanceRepo;
    private final CampusFacilityRepository facilityRepo;
    private final TheoryTimetableRepository timetableRepo;

    public InfrastructureService(BuildingRepository buildingRepo, RoomAllocationRepository allocationRepo, MaintenanceRequestRepository maintenanceRepo, CampusFacilityRepository facilityRepo, TheoryTimetableRepository timetableRepo) {
        this.buildingRepo = buildingRepo; this.allocationRepo = allocationRepo;
        this.maintenanceRepo = maintenanceRepo; this.facilityRepo = facilityRepo; this.timetableRepo = timetableRepo;
    }

    public List<Building> findAllBuildings() { return buildingRepo.findAll(); }
    public Building findBuildingById(Long id) { return buildingRepo.findById(id).orElse(null); }
    public Building createBuilding(Building b) { return buildingRepo.save(b); }
    public Building updateBuilding(Building b) { return buildingRepo.save(b); }

    public List<RoomAllocation> findAllAllocations() { return allocationRepo.findAll(); }
    public RoomAllocation createAllocation(RoomAllocation a) { return allocationRepo.save(a); }
    public void deleteAllocation(Long id) { allocationRepo.deleteById(id); }

    public List<MaintenanceRequest> findAllRequests() { return maintenanceRepo.findAll(); }
    public MaintenanceRequest createRequest(MaintenanceRequest r) { return maintenanceRepo.save(r); }
    public MaintenanceRequest updateRequest(MaintenanceRequest r) { return maintenanceRepo.save(r); }

    public List<CampusFacility> findAllFacilities() { return facilityRepo.findAll(); }
    public CampusFacility createFacility(CampusFacility f) { return facilityRepo.save(f); }

    public List<TheoryTimetable> findAllTimetables() { return timetableRepo.findAll(); }
    public TheoryTimetable createTimetable(TheoryTimetable t) { return timetableRepo.save(t); }
    public TheoryTimetable updateTimetable(TheoryTimetable t) { return timetableRepo.save(t); }
    public void deleteTimetable(Long id) { timetableRepo.deleteById(id); }
}
