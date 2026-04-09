package com.cms.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "fee_installments")
public class FeeInstallment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "fee_structure_id", nullable = false) private FeeStructure feeStructure;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private StudentProfile student;
    @Column(name = "installment_number", nullable = false) private int installmentNumber;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(name = "due_date", nullable = false) private LocalDate dueDate;
    @Column(name = "paid_date") private LocalDate paidDate;
    @Column(nullable = false, length = 30) private String status = "PENDING";
    @Column(name = "late_fee", precision = 10, scale = 2) private BigDecimal lateFee;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public FeeStructure getFeeStructure() { return feeStructure; } public void setFeeStructure(FeeStructure v) { this.feeStructure = v; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public int getInstallmentNumber() { return installmentNumber; } public void setInstallmentNumber(int v) { this.installmentNumber = v; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal v) { this.amount = v; }
    public LocalDate getDueDate() { return dueDate; } public void setDueDate(LocalDate v) { this.dueDate = v; }
    public LocalDate getPaidDate() { return paidDate; } public void setPaidDate(LocalDate v) { this.paidDate = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public BigDecimal getLateFee() { return lateFee; } public void setLateFee(BigDecimal v) { this.lateFee = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
