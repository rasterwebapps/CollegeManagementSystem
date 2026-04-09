package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "salary_structures")
public class SalaryStructure {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String designation;
    @Column(name = "basic_pay", nullable = false, precision = 12, scale = 2) private BigDecimal basicPay;
    @Column(precision = 12, scale = 2) private BigDecimal da = BigDecimal.ZERO;
    @Column(precision = 12, scale = 2) private BigDecimal hra = BigDecimal.ZERO;
    @Column(name = "special_allowance", precision = 12, scale = 2) private BigDecimal specialAllowance = BigDecimal.ZERO;
    @Column(precision = 12, scale = 2) private BigDecimal deductions = BigDecimal.ZERO;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getDesignation() { return designation; } public void setDesignation(String v) { this.designation = v; }
    public BigDecimal getBasicPay() { return basicPay; } public void setBasicPay(BigDecimal v) { this.basicPay = v; }
    public BigDecimal getDa() { return da; } public void setDa(BigDecimal v) { this.da = v; }
    public BigDecimal getHra() { return hra; } public void setHra(BigDecimal v) { this.hra = v; }
    public BigDecimal getSpecialAllowance() { return specialAllowance; } public void setSpecialAllowance(BigDecimal v) { this.specialAllowance = v; }
    public BigDecimal getDeductions() { return deductions; } public void setDeductions(BigDecimal v) { this.deductions = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
