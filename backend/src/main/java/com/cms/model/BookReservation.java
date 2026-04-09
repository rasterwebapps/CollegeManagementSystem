package com.cms.model;

import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "book_reservations")
public class BookReservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id", nullable = false) private LibraryMember member;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "book_id", nullable = false) private Book book;
    @Column(name = "reservation_date", nullable = false) private LocalDate reservationDate;
    @Column(name = "queue_position") private int queuePosition;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public LibraryMember getMember() { return member; } public void setMember(LibraryMember v) { this.member = v; }
    public Book getBook() { return book; } public void setBook(Book v) { this.book = v; }
    public LocalDate getReservationDate() { return reservationDate; } public void setReservationDate(LocalDate v) { this.reservationDate = v; }
    public int getQueuePosition() { return queuePosition; } public void setQueuePosition(int v) { this.queuePosition = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; }
}
