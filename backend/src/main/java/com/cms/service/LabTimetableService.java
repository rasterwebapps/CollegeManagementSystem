package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabTimetableRequest;
import com.cms.dto.LabTimetableResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.FacultyProfile;
import com.cms.model.Lab;
import com.cms.model.LabTimetable;
import com.cms.model.Semester;
import com.cms.repository.CourseRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.LabTimetableRepository;
import com.cms.repository.SemesterRepository;

@Service
public class LabTimetableService {

    private final LabTimetableRepository labTimetableRepository;
    private final LabRepository labRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final FacultyProfileRepository facultyProfileRepository;

    public LabTimetableService(LabTimetableRepository labTimetableRepository,
                               LabRepository labRepository,
                               CourseRepository courseRepository,
                               SemesterRepository semesterRepository,
                               FacultyProfileRepository facultyProfileRepository) {
        this.labTimetableRepository = labTimetableRepository;
        this.labRepository = labRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.facultyProfileRepository = facultyProfileRepository;
    }

    public List<LabTimetableResponse> findAll() {
        return labTimetableRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public LabTimetableResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabTimetableResponse create(LabTimetableRequest request) {
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));

        checkForConflicts(request, null);

        LabTimetable lt = new LabTimetable();
        mapRequestToEntity(lt, request, lab, course, semester, faculty);
        lt.setStatus(request.status() != null ? request.status() : "SCHEDULED");
        return toResponse(labTimetableRepository.save(lt));
    }

    public LabTimetableResponse update(Long id, LabTimetableRequest request) {
        LabTimetable lt = getOrThrow(id);
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));

        checkForConflicts(request, id);

        mapRequestToEntity(lt, request, lab, course, semester, faculty);
        if (request.status() != null) {
            lt.setStatus(request.status());
        }
        return toResponse(labTimetableRepository.save(lt));
    }

    public void delete(Long id) {
        if (!labTimetableRepository.existsById(id)) {
            throw new ResourceNotFoundException("LabTimetable", id);
        }
        labTimetableRepository.deleteById(id);
    }

    private void checkForConflicts(LabTimetableRequest request, Long excludeId) {
        List<LabTimetable> labSlots = labTimetableRepository
            .findByLabIdAndDayOfWeek(request.labId(), request.dayOfWeek());
        for (LabTimetable existing : labSlots) {
            if (excludeId != null && existing.getId().equals(excludeId)) continue;
            if (timesOverlap(request.startTime(), request.endTime(),
                             existing.getStartTime(), existing.getEndTime())) {
                throw new IllegalStateException("Lab room conflict: lab is already booked for this time slot");
            }
        }

        List<LabTimetable> facultySlots = labTimetableRepository
            .findByFacultyIdAndDayOfWeek(request.facultyId(), request.dayOfWeek());
        for (LabTimetable existing : facultySlots) {
            if (excludeId != null && existing.getId().equals(excludeId)) continue;
            if (timesOverlap(request.startTime(), request.endTime(),
                             existing.getStartTime(), existing.getEndTime())) {
                throw new IllegalStateException("Faculty conflict: faculty is already scheduled for this time slot");
            }
        }
    }

    boolean timesOverlap(java.time.LocalTime s1, java.time.LocalTime e1,
                         java.time.LocalTime s2, java.time.LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }

    private void mapRequestToEntity(LabTimetable lt, LabTimetableRequest request,
                                    Lab lab, Course course, Semester semester, FacultyProfile faculty) {
        lt.setLab(lab);
        lt.setCourse(course);
        lt.setSemester(semester);
        lt.setFaculty(faculty);
        lt.setDayOfWeek(request.dayOfWeek());
        lt.setStartTime(request.startTime());
        lt.setEndTime(request.endTime());
        lt.setBatchGroup(request.batchGroup());
    }

    private LabTimetable getOrThrow(Long id) {
        return labTimetableRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabTimetable", id));
    }

    private LabTimetableResponse toResponse(LabTimetable lt) {
        return new LabTimetableResponse(
            lt.getId(),
            lt.getLab().getId(), lt.getLab().getName(),
            lt.getCourse().getId(), lt.getCourse().getName(),
            lt.getSemester().getId(), lt.getSemester().getName(),
            lt.getFaculty().getId(), lt.getFaculty().getFirstName() + " " + lt.getFaculty().getLastName(),
            lt.getDayOfWeek(), lt.getStartTime(), lt.getEndTime(),
            lt.getBatchGroup(), lt.getStatus(),
            lt.getCreatedAt(), lt.getUpdatedAt()
        );
    }
}
