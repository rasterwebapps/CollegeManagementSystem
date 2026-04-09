package com.cms.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id") private Department department;
    @Column(nullable = false, length = 100) private String category;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal amount;
    @Column(name = "expense_date", nullable = false) private LocalDate expenseDate;
    @Column(name = "approved_by", length = 200) private String approvedBy;
    @Column(name = "receipt_number", length = 100) private String receiptNumber;
    @Column(nullable = false, length = 30) private String status = "PENDING";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Department getDepartment() { return department; } public void setDepartment(Department v) { this.department = v; }
    public String getCategory() { return category; } public void setCategory(String v) { this.category = v; }
    public String getDescription() { return description; } public void setDescription(String v) { this.description = v; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal v) { this.amount = v; }
    public LocalDate getExpenseDate() { return expenseDate; } public void setExpenseDate(LocalDate v) { this.expenseDate = v; }
    public String getApprovedBy() { return approvedBy; } public void setApprovedBy(String v) { this.approvedBy = v; }
    public String getReceiptNumber() { return receiptNumber; } public void setReceiptNumber(String v) { this.receiptNumber = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
