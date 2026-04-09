package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class HostelEnhancedService {
    private final MessMenuRepository messRepo;
    private final HostelFeeRepository feeRepo;
    private final VisitorLogRepository visitorRepo;

    public HostelEnhancedService(MessMenuRepository messRepo, HostelFeeRepository feeRepo, VisitorLogRepository visitorRepo) {
        this.messRepo = messRepo; this.feeRepo = feeRepo; this.visitorRepo = visitorRepo;
    }

    public List<MessMenu> findMenusByHostel(Long hostelId) { return messRepo.findAll().stream().filter(m -> m.getHostel().getId().equals(hostelId)).toList(); }
    public MessMenu createMenu(MessMenu m) { return messRepo.save(m); }
    public MessMenu updateMenu(MessMenu m) { return messRepo.save(m); }

    public List<HostelFee> findFeesByStudent(Long studentId) { return feeRepo.findAll().stream().filter(f -> f.getStudent().getId().equals(studentId)).toList(); }
    public HostelFee createFee(HostelFee f) { return feeRepo.save(f); }

    public List<VisitorLog> findVisitorsByStudent(Long studentId) { return visitorRepo.findAll().stream().filter(v -> v.getStudent().getId().equals(studentId)).toList(); }
    public VisitorLog logVisitor(VisitorLog v) { return visitorRepo.save(v); }
    public VisitorLog markOut(Long id) { return visitorRepo.findById(id).map(v -> { v.setOutTime(java.time.Instant.now()); return visitorRepo.save(v); }).orElse(null); }
}
