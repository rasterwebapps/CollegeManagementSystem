package com.cms.model;

import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "keycloak_id", length = 255) private String keycloakId;
    @Column(name = "employee_code", nullable = false, unique = true, length = 50) private String employeeCode;
    @Column(name = "first_name", nullable = false, length = 100) private String firstName;
    @Column(name = "last_name", nullable = false, length = 100) private String lastName;
    @Column(length = 255) private String email;
    @Column(length = 20) private String phone;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id") private Department department;
    @Column(nullable = false, length = 100) private String designation;
    @Column(name = "staff_type", nullable = false, length = 50) private String staffType;
    @Column(name = "joining_date") private LocalDate joiningDate;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getKeycloakId() { return keycloakId; } public void setKeycloakId(String v) { this.keycloakId = v; }
    public String getEmployeeCode() { return employeeCode; } public void setEmployeeCode(String v) { this.employeeCode = v; }
    public String getFirstName() { return firstName; } public void setFirstName(String v) { this.firstName = v; }
    public String getLastName() { return lastName; } public void setLastName(String v) { this.lastName = v; }
    public String getEmail() { return email; } public void setEmail(String v) { this.email = v; }
    public String getPhone() { return phone; } public void setPhone(String v) { this.phone = v; }
    public Department getDepartment() { return department; } public void setDepartment(Department v) { this.department = v; }
    public String getDesignation() { return designation; } public void setDesignation(String v) { this.designation = v; }
    public String getStaffType() { return staffType; } public void setStaffType(String v) { this.staffType = v; }
    public LocalDate getJoiningDate() { return joiningDate; } public void setJoiningDate(LocalDate v) { this.joiningDate = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
