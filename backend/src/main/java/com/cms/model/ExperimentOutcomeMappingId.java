package com.cms.model;

import java.io.Serializable;
import java.util.Objects;

public class ExperimentOutcomeMappingId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long experiment;
    private Long courseOutcome;

    public ExperimentOutcomeMappingId() {
    }

    public ExperimentOutcomeMappingId(Long experiment, Long courseOutcome) {
        this.experiment = experiment;
        this.courseOutcome = courseOutcome;
    }

    public Long getExperiment() {
        return experiment;
    }

    public void setExperiment(Long experiment) {
        this.experiment = experiment;
    }

    public Long getCourseOutcome() {
        return courseOutcome;
    }

    public void setCourseOutcome(Long courseOutcome) {
        this.courseOutcome = courseOutcome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentOutcomeMappingId that = (ExperimentOutcomeMappingId) o;
        return Objects.equals(experiment, that.experiment)
                && Objects.equals(courseOutcome, that.courseOutcome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experiment, courseOutcome);
    }
}
