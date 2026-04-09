package com.cms.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "library_fines")
public class LibraryFine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id", nullable = false) private LibraryMember member;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "book_issue_id") private BookIssue bookIssue;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal amount;
    @Column(nullable = false, length = 200) private String reason;
    @Column(name = "paid_date") private LocalDate paidDate;
    @Column(nullable = false, length = 30) private String status = "UNPAID";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public LibraryMember getMember() { return member; } public void setMember(LibraryMember v) { this.member = v; }
    public BookIssue getBookIssue() { return bookIssue; } public void setBookIssue(BookIssue v) { this.bookIssue = v; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal v) { this.amount = v; }
    public String getReason() { return reason; } public void setReason(String v) { this.reason = v; }
    public LocalDate getPaidDate() { return paidDate; } public void setPaidDate(LocalDate v) { this.paidDate = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; }
}
