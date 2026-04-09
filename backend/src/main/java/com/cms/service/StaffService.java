package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class StaffService {
    private final StaffRepository staffRepo;
    private final FacultyWorkloadRepository workloadRepo;
    private final FacultyAppraisalRepository appraisalRepo;
    private final LeaveTypeRepository leaveTypeRepo;
    private final CommitteeMembershipRepository committeeRepo;

    public StaffService(StaffRepository staffRepo, FacultyWorkloadRepository workloadRepo, FacultyAppraisalRepository appraisalRepo, LeaveTypeRepository leaveTypeRepo, CommitteeMembershipRepository committeeRepo) {
        this.staffRepo = staffRepo; this.workloadRepo = workloadRepo; this.appraisalRepo = appraisalRepo;
        this.leaveTypeRepo = leaveTypeRepo; this.committeeRepo = committeeRepo;
    }

    public List<Staff> findAllStaff() { return staffRepo.findAll(); }
    public Staff findStaffById(Long id) { return staffRepo.findById(id).orElse(null); }
    public Staff createStaff(Staff s) { return staffRepo.save(s); }
    public Staff updateStaff(Staff s) { return staffRepo.save(s); }

    public List<FacultyWorkload> findWorkloadByFaculty(Long facultyId) { return workloadRepo.findAll().stream().filter(w -> w.getFaculty().getId().equals(facultyId)).toList(); }
    public FacultyWorkload saveWorkload(FacultyWorkload w) { return workloadRepo.save(w); }

    public List<FacultyAppraisal> findAppraisalsByFaculty(Long facultyId) { return appraisalRepo.findAll().stream().filter(a -> a.getFaculty().getId().equals(facultyId)).toList(); }
    public FacultyAppraisal createAppraisal(FacultyAppraisal a) { return appraisalRepo.save(a); }

    public List<LeaveType> findAllLeaveTypes() { return leaveTypeRepo.findAll(); }
    public LeaveType createLeaveType(LeaveType t) { return leaveTypeRepo.save(t); }

    public List<CommitteeMembership> findCommitteesByFaculty(Long facultyId) { return committeeRepo.findAll().stream().filter(c -> c.getFaculty().getId().equals(facultyId)).toList(); }
    public CommitteeMembership createCommittee(CommitteeMembership c) { return committeeRepo.save(c); }
    public void deleteCommittee(Long id) { committeeRepo.deleteById(id); }
}
