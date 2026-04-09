package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class TransportService {
    private final VehicleRepository vehicleRepository;
    private final TransportRouteRepository routeRepository;
    private final TransportPassRepository passRepository;

    public TransportService(VehicleRepository vehicleRepository, TransportRouteRepository routeRepository, TransportPassRepository passRepository) {
        this.vehicleRepository = vehicleRepository;
        this.routeRepository = routeRepository;
        this.passRepository = passRepository;
    }

    public List<Vehicle> findAllVehicles() { return vehicleRepository.findAll(); }
    public Vehicle findVehicleById(Long id) { return vehicleRepository.findById(id).orElse(null); }
    public Vehicle createVehicle(Vehicle v) { return vehicleRepository.save(v); }
    public Vehicle updateVehicle(Vehicle v) { return vehicleRepository.save(v); }

    public List<TransportRoute> findAllRoutes() { return routeRepository.findAll(); }
    public TransportRoute findRouteById(Long id) { return routeRepository.findById(id).orElse(null); }
    public TransportRoute createRoute(TransportRoute r) { return routeRepository.save(r); }
    public TransportRoute updateRoute(TransportRoute r) { return routeRepository.save(r); }
    public void deleteRoute(Long id) { routeRepository.deleteById(id); }

    public List<TransportPass> findAllPasses() { return passRepository.findAll(); }
    public TransportPass createPass(TransportPass p) { return passRepository.save(p); }
    public TransportPass updatePass(TransportPass p) { return passRepository.save(p); }
}
