package com.cms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_types")
public class LeaveType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String name;
    @Column(name = "max_days_per_year") private int maxDaysPerYear;
    @Column(name = "is_paid") private boolean paid = true;
    @Column(columnDefinition = "TEXT") private String description;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public int getMaxDaysPerYear() { return maxDaysPerYear; } public void setMaxDaysPerYear(int v) { this.maxDaysPerYear = v; }
    public boolean isPaid() { return paid; } public void setPaid(boolean v) { this.paid = v; }
    public String getDescription() { return description; } public void setDescription(String v) { this.description = v; }
}
