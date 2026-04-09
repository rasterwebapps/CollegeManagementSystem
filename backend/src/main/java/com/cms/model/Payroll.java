package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "payrolls")
public class Payroll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id") private FacultyProfile faculty;
    @Column(name = "pay_month", nullable = false) private int month;
    @Column(name = "pay_year", nullable = false) private int year;
    @Column(name = "gross_pay", nullable = false, precision = 12, scale = 2) private BigDecimal grossPay;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal deductions = BigDecimal.ZERO;
    @Column(name = "net_pay", nullable = false, precision = 12, scale = 2) private BigDecimal netPay;
    @Column(nullable = false, length = 30) private String status = "GENERATED";
    @Column(name = "paid_date") private java.time.LocalDate paidDate;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public FacultyProfile getFaculty() { return faculty; } public void setFaculty(FacultyProfile v) { this.faculty = v; }
    public int getMonth() { return month; } public void setMonth(int v) { this.month = v; }
    public int getYear() { return year; } public void setYear(int v) { this.year = v; }
    public BigDecimal getGrossPay() { return grossPay; } public void setGrossPay(BigDecimal v) { this.grossPay = v; }
    public BigDecimal getDeductions() { return deductions; } public void setDeductions(BigDecimal v) { this.deductions = v; }
    public BigDecimal getNetPay() { return netPay; } public void setNetPay(BigDecimal v) { this.netPay = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public java.time.LocalDate getPaidDate() { return paidDate; } public void setPaidDate(java.time.LocalDate v) { this.paidDate = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
