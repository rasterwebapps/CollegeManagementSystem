package com.cms.controller.graphql;

import java.math.BigDecimal;
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
import com.cms.service.TransportService;

@Controller
public class TransportGraphQLController {
    private final TransportService transportService;
    private final StudentProfileRepository studentProfileRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final TransportRouteRepository routeRepository;

    public TransportGraphQLController(TransportService transportService, StudentProfileRepository studentProfileRepository, FacultyProfileRepository facultyProfileRepository, TransportRouteRepository routeRepository) {
        this.transportService = transportService;
        this.studentProfileRepository = studentProfileRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.routeRepository = routeRepository;
    }

    @QueryMapping
    public List<Vehicle> vehicles(@Argument String status) {
        List<Vehicle> all = transportService.findAllVehicles();
        if (status != null) return all.stream().filter(v -> v.getStatus().equals(status)).toList();
        return all;
    }

    @QueryMapping
    public Vehicle vehicle(@Argument Long id) { return transportService.findVehicleById(id); }

    @QueryMapping
    public List<TransportRoute> transportRoutes() { return transportService.findAllRoutes(); }

    @QueryMapping
    public TransportRoute transportRoute(@Argument Long id) { return transportService.findRouteById(id); }

    @QueryMapping
    public List<TransportPass> transportPasses(@Argument Long studentId, @Argument String status) {
        List<TransportPass> all = transportService.findAllPasses();
        return all.stream().filter(p -> (studentId == null || (p.getStudent() != null && p.getStudent().getId().equals(studentId))) && (status == null || p.getStatus().equals(status))).toList();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Vehicle createVehicle(@Argument Map<String, Object> input) {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber((String) input.get("registrationNumber"));
        v.setType((String) input.get("vehicleType"));
        if (input.get("capacity") != null) v.setCapacity((Integer) input.get("capacity"));
        v.setDriverName((String) input.get("driverName"));
        v.setDriverPhone((String) input.get("driverPhone"));
        if (input.get("insuranceExpiry") != null) v.setInsuranceExpiry(LocalDate.parse((String) input.get("insuranceExpiry")));
        v.setStatus((String) input.getOrDefault("status", "ACTIVE"));
        return transportService.createVehicle(v);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Vehicle updateVehicle(@Argument Long id, @Argument Map<String, Object> input) {
        Vehicle v = transportService.findVehicleById(id);
        if (v == null) return null;
        if (input.containsKey("driverName")) v.setDriverName((String) input.get("driverName"));
        if (input.containsKey("driverPhone")) v.setDriverPhone((String) input.get("driverPhone"));
        if (input.containsKey("status")) v.setStatus((String) input.get("status"));
        if (input.containsKey("capacity")) v.setCapacity((Integer) input.get("capacity"));
        return transportService.updateVehicle(v);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TransportRoute createTransportRoute(@Argument Map<String, Object> input) {
        TransportRoute r = new TransportRoute();
        r.setRouteName((String) input.get("routeName"));
        if (input.get("vehicleId") != null) r.setVehicle(transportService.findVehicleById(Long.valueOf(input.get("vehicleId").toString())));
        r.setStops((String) input.get("stops"));
        if (input.get("startTime") != null) r.setStartTime(LocalTime.parse((String) input.get("startTime")));
        if (input.get("endTime") != null) r.setEndTime(LocalTime.parse((String) input.get("endTime")));
        if (input.get("distanceKm") != null) r.setDistanceKm(Double.parseDouble(input.get("distanceKm").toString()));
        r.setStatus((String) input.getOrDefault("status", "ACTIVE"));
        return transportService.createRoute(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TransportRoute updateTransportRoute(@Argument Long id, @Argument Map<String, Object> input) {
        TransportRoute r = transportService.findRouteById(id);
        if (r == null) return null;
        if (input.containsKey("stops")) r.setStops((String) input.get("stops"));
        if (input.containsKey("status")) r.setStatus((String) input.get("status"));
        return transportService.updateRoute(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteTransportRoute(@Argument Long id) {
        transportService.deleteRoute(id);
        return true;
    }

    @MutationMapping
    public TransportPass createTransportPass(@Argument Map<String, Object> input) {
        TransportPass p = new TransportPass();
        if (input.get("studentId") != null) p.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("facultyId") != null) p.setFaculty(facultyProfileRepository.findById(Long.valueOf(input.get("facultyId").toString())).orElse(null));
        if (input.get("routeId") != null) p.setRoute(routeRepository.findById(Long.valueOf(input.get("routeId").toString())).orElse(null));
        p.setPassType((String) input.get("passType"));
        if (input.get("validFrom") != null) p.setValidFrom(LocalDate.parse((String) input.get("validFrom")));
        if (input.get("validTo") != null) p.setValidTo(LocalDate.parse((String) input.get("validTo")));
        if (input.get("fee") != null) p.setFee(new BigDecimal(input.get("fee").toString()));
        p.setStatus((String) input.getOrDefault("status", "ACTIVE"));
        return transportService.createPass(p);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TransportPass updateTransportPassStatus(@Argument Long id, @Argument String status) {
        TransportPass p = transportService.findAllPasses().stream().filter(tp -> tp.getId().equals(id)).findFirst().orElse(null);
        if (p == null) return null;
        p.setStatus(status);
        return transportService.updatePass(p);
    }
}
