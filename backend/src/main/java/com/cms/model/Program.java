package com.cms.model;

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
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "duration_years", nullable = false)
    private Integer durationYears;

    @Column(name = "degree_type", nullable = false, length = 50)
    private String degreeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id")
    private FacultyProfile coordinator;

    @Column(name = "total_intake")
    private Integer totalIntake;

    @Column(name = "eligibility_criteria", columnDefinition = "TEXT")
    private String eligibilityCriteria;

    @Column(name = "accreditation_status", length = 50)
    private String accreditationStatus;

    @Column(name = "accreditation_valid_until")
    private java.time.LocalDate accreditationValidUntil;

    @Column(name = "program_learning_outcomes", columnDefinition = "TEXT")
    private String programLearningOutcomes;

    @Column(name = "curriculum_version", length = 20)
    private String curriculumVersion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Integer getDurationYears() { return durationYears; }
    public void setDurationYears(Integer durationYears) { this.durationYears = durationYears; }
    public String getDegreeType() { return degreeType; }
    public void setDegreeType(String degreeType) { this.degreeType = degreeType; }
    public FacultyProfile getCoordinator() { return coordinator; }
    public void setCoordinator(FacultyProfile coordinator) { this.coordinator = coordinator; }
    public Integer getTotalIntake() { return totalIntake; }
    public void setTotalIntake(Integer totalIntake) { this.totalIntake = totalIntake; }
    public String getEligibilityCriteria() { return eligibilityCriteria; }
    public void setEligibilityCriteria(String eligibilityCriteria) { this.eligibilityCriteria = eligibilityCriteria; }
    public String getAccreditationStatus() { return accreditationStatus; }
    public void setAccreditationStatus(String accreditationStatus) { this.accreditationStatus = accreditationStatus; }
    public java.time.LocalDate getAccreditationValidUntil() { return accreditationValidUntil; }
    public void setAccreditationValidUntil(java.time.LocalDate accreditationValidUntil) { this.accreditationValidUntil = accreditationValidUntil; }
    public String getProgramLearningOutcomes() { return programLearningOutcomes; }
    public void setProgramLearningOutcomes(String programLearningOutcomes) { this.programLearningOutcomes = programLearningOutcomes; }
    public String getCurriculumVersion() { return curriculumVersion; }
    public void setCurriculumVersion(String curriculumVersion) { this.curriculumVersion = curriculumVersion; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
