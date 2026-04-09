package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "department_budgets")
public class DepartmentBudget {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id", nullable = false) private Department department;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "financial_year_id", nullable = false) private FinancialYear financialYear;
    @Column(name = "allocated_amount", nullable = false, precision = 14, scale = 2) private BigDecimal allocatedAmount;
    @Column(name = "utilized_amount", precision = 14, scale = 2) private BigDecimal utilizedAmount = BigDecimal.ZERO;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Department getDepartment() { return department; } public void setDepartment(Department v) { this.department = v; }
    public FinancialYear getFinancialYear() { return financialYear; } public void setFinancialYear(FinancialYear v) { this.financialYear = v; }
    public BigDecimal getAllocatedAmount() { return allocatedAmount; } public void setAllocatedAmount(BigDecimal v) { this.allocatedAmount = v; }
    public BigDecimal getUtilizedAmount() { return utilizedAmount; } public void setUtilizedAmount(BigDecimal v) { this.utilizedAmount = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
