package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "research_publications")
public class ResearchPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private FacultyProfile faculty;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(name = "publication_type", nullable = false, length = 50)
    private String publicationType;

    @Column(name = "journal_or_conference", nullable = false, length = 500)
    private String journalOrConference;

    @Column(name = "publication_year", nullable = false)
    private Integer year;

    @Column(length = 50)
    private String volume;

    @Column(length = 50)
    private String pages;

    @Column(length = 200)
    private String doi;

    @Column(name = "impact_factor", precision = 6, scale = 3)
    private BigDecimal impactFactor;

    @Column(length = 100)
    private String indexing;

    @Column(name = "co_authors", columnDefinition = "TEXT")
    private String coAuthors;

    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;

    @Column(length = 500)
    private String url;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public FacultyProfile getFaculty() { return faculty; }
    public void setFaculty(FacultyProfile faculty) { this.faculty = faculty; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPublicationType() { return publicationType; }
    public void setPublicationType(String publicationType) { this.publicationType = publicationType; }
    public String getJournalOrConference() { return journalOrConference; }
    public void setJournalOrConference(String journalOrConference) { this.journalOrConference = journalOrConference; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }
    public String getPages() { return pages; }
    public void setPages(String pages) { this.pages = pages; }
    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }
    public BigDecimal getImpactFactor() { return impactFactor; }
    public void setImpactFactor(BigDecimal impactFactor) { this.impactFactor = impactFactor; }
    public String getIndexing() { return indexing; }
    public void setIndexing(String indexing) { this.indexing = indexing; }
    public String getCoAuthors() { return coAuthors; }
    public void setCoAuthors(String coAuthors) { this.coAuthors = coAuthors; }
    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
