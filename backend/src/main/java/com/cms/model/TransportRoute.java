package com.cms.model;

import java.time.LocalTime;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "transport_routes")
public class TransportRoute {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "route_name", nullable = false, length = 200) private String routeName;
    @Column(columnDefinition = "TEXT") private String stops;
    @Column(name = "start_time") private LocalTime startTime;
    @Column(name = "end_time") private LocalTime endTime;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "vehicle_id") private Vehicle vehicle;
    @Column(name = "distance_km") private double distanceKm;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getRouteName() { return routeName; } public void setRouteName(String v) { this.routeName = v; }
    public String getStops() { return stops; } public void setStops(String v) { this.stops = v; }
    public LocalTime getStartTime() { return startTime; } public void setStartTime(LocalTime v) { this.startTime = v; }
    public LocalTime getEndTime() { return endTime; } public void setEndTime(LocalTime v) { this.endTime = v; }
    public Vehicle getVehicle() { return vehicle; } public void setVehicle(Vehicle v) { this.vehicle = v; }
    public double getDistanceKm() { return distanceKm; } public void setDistanceKm(double v) { this.distanceKm = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
