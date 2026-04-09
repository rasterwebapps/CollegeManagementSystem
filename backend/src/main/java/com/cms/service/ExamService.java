package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.ExamSchedule;
import com.cms.model.StudentMark;
import com.cms.repository.ExamScheduleRepository;
import com.cms.repository.StudentMarkRepository;

@Service
public class ExamService {

    private final ExamScheduleRepository examScheduleRepository;
    private final StudentMarkRepository studentMarkRepository;

    public ExamService(ExamScheduleRepository examScheduleRepository,
                       StudentMarkRepository studentMarkRepository) {
        this.examScheduleRepository = examScheduleRepository;
        this.studentMarkRepository = studentMarkRepository;
    }

    public List<ExamSchedule> findAllSchedules() {
        return examScheduleRepository.findAll();
    }

    public ExamSchedule findScheduleById(Long id) {
        return examScheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ExamSchedule", id));
    }

    public List<ExamSchedule> findSchedulesByCourseId(Long courseId) {
        return examScheduleRepository.findByCourseId(courseId);
    }

    public ExamSchedule createSchedule(ExamSchedule schedule) {
        return examScheduleRepository.save(schedule);
    }

    public ExamSchedule updateSchedule(Long id, ExamSchedule data) {
        ExamSchedule schedule = findScheduleById(id);
        if (data.getExamType() != null) schedule.setExamType(data.getExamType());
        if (data.getDate() != null) schedule.setDate(data.getDate());
        if (data.getStartTime() != null) schedule.setStartTime(data.getStartTime());
        if (data.getDuration() != null) schedule.setDuration(data.getDuration());
        if (data.getRoomLocation() != null) schedule.setRoomLocation(data.getRoomLocation());
        if (data.getMaxMarks() != null) schedule.setMaxMarks(data.getMaxMarks());
        if (data.getPassingMarks() != null) schedule.setPassingMarks(data.getPassingMarks());
        return examScheduleRepository.save(schedule);
    }

    public List<StudentMark> findMarksByStudentId(Long studentId) {
        return studentMarkRepository.findByStudentId(studentId);
    }

    public List<StudentMark> findMarksByScheduleId(Long scheduleId) {
        return studentMarkRepository.findByExamId(scheduleId);
    }

    public StudentMark createMark(StudentMark mark) {
        return studentMarkRepository.save(mark);
    }

    public StudentMark updateMark(Long id, StudentMark markData) {
        StudentMark mark = studentMarkRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("StudentMark", id));
        if (markData.getInternalMarks() != null) mark.setInternalMarks(markData.getInternalMarks());
        if (markData.getExternalMarks() != null) mark.setExternalMarks(markData.getExternalMarks());
        if (markData.getTotalMarks() != null) mark.setTotalMarks(markData.getTotalMarks());
        if (markData.getGrade() != null) mark.setGrade(markData.getGrade());
        if (markData.getRemarks() != null) mark.setRemarks(markData.getRemarks());
        return studentMarkRepository.save(mark);
    }

    public List<ExamSchedule> findSchedulesBySemesterId(Long semesterId) {
        return examScheduleRepository.findBySemesterId(semesterId);
    }

    public void deleteSchedule(Long id) {
        if (!examScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExamSchedule", id);
        }
        examScheduleRepository.deleteById(id);
    }

    public List<StudentMark> findMarksByStudentAndSemester(Long studentId, Long semesterId) {
        return studentMarkRepository.findByStudentIdAndSemesterId(studentId, semesterId);
    }

    public List<StudentMark> findMarksByExamId(Long examId) {
        return studentMarkRepository.findByExamId(examId);
    }

    public StudentMark recordMark(StudentMark mark) {
        return studentMarkRepository.save(mark);
    }

    public StudentMark findMarkById(Long id) {
        return studentMarkRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("StudentMark", id));
    }
}
