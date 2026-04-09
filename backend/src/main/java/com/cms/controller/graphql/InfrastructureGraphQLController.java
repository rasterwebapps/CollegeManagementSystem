package com.cms.controller.graphql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.cms.model.*;
import com.cms.repository.*;
import com.cms.service.InfrastructureService;

@Controller
public class InfrastructureGraphQLController {
    private final InfrastructureService infraService;
    private final RoomResourceRepository roomResourceRepo;
    private final CourseRepository courseRepo;
    private final FacultyProfileRepository facultyRepo;
    private final SemesterRepository semesterRepo;

    public InfrastructureGraphQLController(InfrastructureService infraService, RoomResourceRepository roomResourceRepo, CourseRepository courseRepo, FacultyProfileRepository facultyRepo, SemesterRepository semesterRepo) {
        this.infraService = infraService; this.roomResourceRepo = roomResourceRepo;
        this.courseRepo = courseRepo; this.facultyRepo = facultyRepo; this.semesterRepo = semesterRepo;
    }

    @QueryMapping
    public List<Building> buildings() { return infraService.findAllBuildings(); }

    @QueryMapping
    public Building building(@Argument Long id) { return infraService.findBuildingById(id); }

    @QueryMapping
    public List<RoomAllocation> roomAllocations(@Argument Long roomId, @Argument String date) {
        return infraService.findAllAllocations().stream()
            .filter(a -> (roomId == null || a.getRoom().getId().equals(roomId)))
            .toList();
    }

    @QueryMapping
    public List<MaintenanceRequest> maintenanceRequests(@Argument String status) {
        List<MaintenanceRequest> all = infraService.findAllRequests();
        if (status != null) return all.stream().filter(r -> r.getStatus().equals(status)).toList();
        return all;
    }

    @QueryMapping
    public List<CampusFacility> campusFacilities() { return infraService.findAllFacilities(); }

    @QueryMapping
    public List<TheoryTimetable> theoryTimetables(@Argument Long semesterId, @Argument Long courseId) {
        return infraService.findAllTimetables().stream()
            .filter(t -> (semesterId == null || (t.getSemester() != null && t.getSemester().getId().equals(semesterId)))
                && (courseId == null || (t.getCourse() != null && t.getCourse().getId().equals(courseId))))
            .toList();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Building createBuilding(@Argument Map<String, Object> input) {
        Building b = new Building();
        b.setName((String) input.get("name"));
        b.setCode((String) input.get("code"));
        if (input.get("floors") != null) b.setFloors((Integer) input.get("floors"));
        b.setAddress((String) input.get("address"));
        b.setStatus((String) input.getOrDefault("status", "ACTIVE"));
        return infraService.createBuilding(b);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Building updateBuilding(@Argument Long id, @Argument Map<String, Object> input) {
        Building b = infraService.findBuildingById(id);
        if (b == null) return null;
        if (input.containsKey("name")) b.setName((String) input.get("name"));
        if (input.containsKey("status")) b.setStatus((String) input.get("status"));
        return infraService.updateBuilding(b);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoomAllocation createRoomAllocation(@Argument Map<String, Object> input) {
        RoomAllocation a = new RoomAllocation();
        if (input.get("roomId") != null) a.setRoom(roomResourceRepo.findById(Long.valueOf(input.get("roomId").toString())).orElse(null));
        a.setPurpose((String) input.get("purpose"));
        a.setDayOfWeek((String) input.get("dayOfWeek"));
        if (input.get("startTime") != null) a.setStartTime(LocalTime.parse((String) input.get("startTime")));
        if (input.get("endTime") != null) a.setEndTime(LocalTime.parse((String) input.get("endTime")));
        if (input.get("courseId") != null) a.setCourse(courseRepo.findById(Long.valueOf(input.get("courseId").toString())).orElse(null));
        if (input.get("semesterId") != null) a.setSemester(semesterRepo.findById(Long.valueOf(input.get("semesterId").toString())).orElse(null));
        return infraService.createAllocation(a);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteRoomAllocation(@Argument Long id) {
        infraService.deleteAllocation(id);
        return true;
    }

    @MutationMapping
    public MaintenanceRequest createMaintenanceRequest(@Argument Map<String, Object> input) {
        MaintenanceRequest r = new MaintenanceRequest();
        r.setRequesterKeycloakId((String) input.get("requestedBy"));
        r.setRequesterName((String) input.get("requesterName"));
        r.setDescription((String) input.get("description"));
        r.setPriority((String) input.getOrDefault("priority", "MEDIUM"));
        r.setLocation((String) input.get("location"));
        r.setStatus("OPEN");
        return infraService.createRequest(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MaintenanceRequest updateMaintenanceRequest(@Argument Long id, @Argument Map<String, Object> input) {
        MaintenanceRequest r = infraService.findAllRequests().stream().filter(req -> req.getId().equals(id)).findFirst().orElse(null);
        if (r == null) return null;
        if (input.containsKey("status")) r.setStatus((String) input.get("status"));
        if (input.containsKey("assignedTo")) r.setAssignedTo((String) input.get("assignedTo"));
        if (input.containsKey("resolution")) r.setResolution((String) input.get("resolution"));
        return infraService.updateRequest(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CampusFacility createCampusFacility(@Argument Map<String, Object> input) {
        CampusFacility f = new CampusFacility();
        f.setName((String) input.get("name"));
        f.setType((String) input.get("facilityType"));
        f.setDescription((String) input.get("description"));
        f.setLocation((String) input.get("location"));
        if (input.get("capacity") != null) f.setCapacity((Integer) input.get("capacity"));
        f.setStatus((String) input.getOrDefault("status", "ACTIVE"));
        return infraService.createFacility(f);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TheoryTimetable createTheoryTimetable(@Argument Map<String, Object> input) {
        TheoryTimetable t = new TheoryTimetable();
        if (input.get("courseId") != null) t.setCourse(courseRepo.findById(Long.valueOf(input.get("courseId").toString())).orElse(null));
        if (input.get("facultyId") != null) t.setFaculty(facultyRepo.findById(Long.valueOf(input.get("facultyId").toString())).orElse(null));
        if (input.get("semesterId") != null) t.setSemester(semesterRepo.findById(Long.valueOf(input.get("semesterId").toString())).orElse(null));
        if (input.get("roomId") != null) t.setRoom(roomResourceRepo.findById(Long.valueOf(input.get("roomId").toString())).orElse(null));
        t.setDayOfWeek((String) input.get("dayOfWeek"));
        if (input.get("startTime") != null) t.setStartTime(LocalTime.parse((String) input.get("startTime")));
        if (input.get("endTime") != null) t.setEndTime(LocalTime.parse((String) input.get("endTime")));
        return infraService.createTimetable(t);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TheoryTimetable updateTheoryTimetable(@Argument Long id, @Argument Map<String, Object> input) {
        TheoryTimetable t = infraService.findAllTimetables().stream().filter(tt -> tt.getId().equals(id)).findFirst().orElse(null);
        if (t == null) return null;
        if (input.containsKey("dayOfWeek")) t.setDayOfWeek((String) input.get("dayOfWeek"));
        return infraService.updateTimetable(t);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteTheoryTimetable(@Argument Long id) {
        infraService.deleteTimetable(id);
        return true;
    }
}
