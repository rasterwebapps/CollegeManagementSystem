package com.cms.controller.graphql;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Hostel;
import com.cms.model.HostelAllocation;
import com.cms.model.HostelRoom;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.HostelRepository;
import com.cms.repository.HostelRoomRepository;
import com.cms.repository.StudentProfileRepository;
import com.cms.service.HostelEnhancedService;
import com.cms.service.HostelService;
import com.cms.model.MessMenu;
import com.cms.model.HostelFee;
import com.cms.model.VisitorLog;
import com.cms.repository.SemesterRepository;

@Controller
public class HostelGraphQLController {

    private final HostelService hostelService;
    private final HostelEnhancedService hostelEnhancedService;
    private final HostelRepository hostelRepository;
    private final HostelRoomRepository hostelRoomRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;

    public HostelGraphQLController(HostelService hostelService,
                                    HostelEnhancedService hostelEnhancedService,
                                    HostelRepository hostelRepository,
                                    HostelRoomRepository hostelRoomRepository,
                                    FacultyProfileRepository facultyProfileRepository,
                                    StudentProfileRepository studentProfileRepository,
                                    AcademicYearRepository academicYearRepository,
                                    SemesterRepository semesterRepository) {
        this.hostelService = hostelService;
        this.hostelEnhancedService = hostelEnhancedService;
        this.hostelRepository = hostelRepository;
        this.hostelRoomRepository = hostelRoomRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.academicYearRepository = academicYearRepository;
        this.semesterRepository = semesterRepository;
    }

    @QueryMapping
    public List<Hostel> hostels() {
        return hostelService.findAllHostels();
    }

    @QueryMapping
    public Hostel hostel(@Argument Long id) {
        return hostelService.findHostelById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Hostel createHostel(@Argument Map<String, Object> input) {
        Hostel hostel = new Hostel();
        hostel.setName((String) input.get("name"));
        hostel.setCode((String) input.get("code"));
        hostel.setHostelType((String) input.get("hostelType"));
        if (input.get("totalCapacity") != null) hostel.setTotalCapacity((Integer) input.get("totalCapacity"));
        hostel.setAddress((String) input.get("address"));
        hostel.setContactPhone((String) input.get("contactPhone"));
        hostel.setContactEmail((String) input.get("contactEmail"));
        hostel.setFacilities((String) input.get("facilities"));
        if (input.get("wardenId") != null) {
            Long wardenId = Long.valueOf(input.get("wardenId").toString());
            hostel.setWarden(facultyProfileRepository.findById(wardenId)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", wardenId)));
        }
        return hostelService.createHostel(hostel);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Hostel updateHostel(@Argument Long id, @Argument Map<String, Object> input) {
        Hostel hostel = hostelService.findHostelById(id);
        if (input.containsKey("name")) hostel.setName((String) input.get("name"));
        if (input.containsKey("hostelType")) hostel.setHostelType((String) input.get("hostelType"));
        if (input.containsKey("totalCapacity")) hostel.setTotalCapacity((Integer) input.get("totalCapacity"));
        if (input.containsKey("address")) hostel.setAddress((String) input.get("address"));
        if (input.containsKey("contactPhone")) hostel.setContactPhone((String) input.get("contactPhone"));
        if (input.containsKey("contactEmail")) hostel.setContactEmail((String) input.get("contactEmail"));
        if (input.containsKey("facilities")) hostel.setFacilities((String) input.get("facilities"));
        if (input.containsKey("status")) hostel.setStatus((String) input.get("status"));
        if (input.containsKey("wardenId")) {
            Long wardenId = Long.valueOf(input.get("wardenId").toString());
            hostel.setWarden(facultyProfileRepository.findById(wardenId)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", wardenId)));
        }
        return hostelService.createHostel(hostel);
    }

    @QueryMapping
    public List<HostelRoom> hostelRooms(@Argument Long hostelId) {
        return hostelService.findRoomsByHostelId(hostelId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HostelRoom createHostelRoom(@Argument Map<String, Object> input) {
        HostelRoom room = new HostelRoom();
        Long hostelId = Long.valueOf(input.get("hostelId").toString());
        room.setHostel(hostelRepository.findById(hostelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hostel", hostelId)));
        room.setRoomNumber((String) input.get("roomNumber"));
        if (input.get("floor") != null) room.setFloor((Integer) input.get("floor"));
        room.setRoomType((String) input.get("roomType"));
        if (input.get("capacity") != null) room.setCapacity((Integer) input.get("capacity"));
        room.setCurrentOccupancy(0);
        if (input.get("hasAttachedBathroom") != null) room.setHasAttachedBathroom((Boolean) input.get("hasAttachedBathroom"));
        if (input.get("hasFurniture") != null) room.setHasFurniture((Boolean) input.get("hasFurniture"));
        if (input.get("monthlyRent") != null) room.setMonthlyRent(new java.math.BigDecimal(input.get("monthlyRent").toString()));
        return hostelService.createRoom(room);
    }

    @QueryMapping
    public List<HostelAllocation> hostelAllocationsByStudent(@Argument Long studentId) {
        return hostelService.findAllocationsByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HostelAllocation allocateHostelRoom(@Argument Map<String, Object> input) {
        HostelAllocation allocation = new HostelAllocation();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        allocation.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        Long roomId = Long.valueOf(input.get("roomId").toString());
        allocation.setRoom(hostelRoomRepository.findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("HostelRoom", roomId)));
        if (input.get("academicYearId") != null) {
            Long ayId = Long.valueOf(input.get("academicYearId").toString());
            allocation.setAcademicYear(academicYearRepository.findById(ayId)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", ayId)));
        }
        allocation.setAllocationDate(LocalDate.now());
        allocation.setBedNumber((String) input.get("bedNumber"));
        allocation.setStatus("ACTIVE");
        return hostelService.createAllocation(allocation);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HostelAllocation vacateHostelRoom(@Argument Long id) {
        return hostelService.vacateAllocation(id);
    }

    @QueryMapping
    public List<MessMenu> messMenus(@Argument Long hostelId, @Argument String dayOfWeek) {
        List<MessMenu> menus = hostelEnhancedService.findMenusByHostel(hostelId);
        if (dayOfWeek != null) return menus.stream().filter(m -> m.getDayOfWeek().equals(dayOfWeek)).toList();
        return menus;
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MessMenu createMessMenu(@Argument Map<String, Object> input) {
        MessMenu m = new MessMenu();
        if (input.get("hostelId") != null) m.setHostel(hostelRepository.findById(Long.valueOf(input.get("hostelId").toString())).orElse(null));
        m.setDayOfWeek((String) input.get("dayOfWeek"));
        m.setMealType((String) input.get("mealType"));
        m.setItems((String) input.get("items"));
        if (input.get("date") != null) m.setDate(LocalDate.parse((String) input.get("date")));
        return hostelEnhancedService.createMenu(m);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MessMenu updateMessMenu(@Argument Long id, @Argument Map<String, Object> input) {
        MessMenu m = hostelEnhancedService.findMenusByHostel(null).stream().filter(mm -> mm.getId().equals(id)).findFirst().orElse(null);
        if (m == null) return null;
        if (input.containsKey("items")) m.setItems((String) input.get("items"));
        return hostelEnhancedService.updateMenu(m);
    }

    @QueryMapping
    public List<HostelFee> hostelFees(@Argument Long studentId) {
        return hostelEnhancedService.findFeesByStudent(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HostelFee createHostelFee(@Argument Map<String, Object> input) {
        HostelFee f = new HostelFee();
        if (input.get("studentId") != null) f.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("hostelId") != null) f.setHostel(hostelRepository.findById(Long.valueOf(input.get("hostelId").toString())).orElse(null));
        if (input.get("semesterId") != null) f.setSemester(semesterRepository.findById(Long.valueOf(input.get("semesterId").toString())).orElse(null));
        if (input.get("roomFee") != null) f.setRoomFee(new java.math.BigDecimal(input.get("roomFee").toString()));
        if (input.get("messFee") != null) f.setMessFee(new java.math.BigDecimal(input.get("messFee").toString()));
        f.setStatus("PENDING");
        return hostelEnhancedService.createFee(f);
    }

    @QueryMapping
    public List<VisitorLog> visitorLogs(@Argument Long studentId, @Argument String date) {
        return hostelEnhancedService.findVisitorsByStudent(studentId);
    }

    @MutationMapping
    public VisitorLog logVisitor(@Argument Map<String, Object> input) {
        VisitorLog v = new VisitorLog();
        if (input.get("studentId") != null) v.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        v.setVisitorName((String) input.get("visitorName"));
        v.setRelation((String) input.get("relation"));
        v.setPurpose((String) input.get("purpose"));
        v.setInTime(java.time.Instant.now());
        return hostelEnhancedService.logVisitor(v);
    }

    @MutationMapping
    public VisitorLog markVisitorOut(@Argument Long visitorLogId) {
        return hostelEnhancedService.markOut(visitorLogId);
    }
}
