package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class FeedbackService {
    private final GrievanceCommentRepository commentRepository;
    private final FeedbackFormRepository formRepository;
    private final FeedbackResponseRepository responseRepository;

    public FeedbackService(GrievanceCommentRepository commentRepository, FeedbackFormRepository formRepository, FeedbackResponseRepository responseRepository) {
        this.commentRepository = commentRepository;
        this.formRepository = formRepository;
        this.responseRepository = responseRepository;
    }

    public List<GrievanceComment> findCommentsByGrievance(Long grievanceId) { return commentRepository.findAll().stream().filter(c -> c.getGrievance().getId().equals(grievanceId)).toList(); }
    public GrievanceComment addComment(GrievanceComment c) { return commentRepository.save(c); }

    public List<FeedbackForm> findAllForms() { return formRepository.findAll(); }
    public FeedbackForm findFormById(Long id) { return formRepository.findById(id).orElse(null); }
    public FeedbackForm createForm(FeedbackForm f) { return formRepository.save(f); }
    public FeedbackForm updateForm(FeedbackForm f) { return formRepository.save(f); }

    public List<FeedbackResponse> findResponsesByForm(Long formId) { return responseRepository.findAll().stream().filter(r -> r.getForm().getId().equals(formId)).toList(); }
    public FeedbackResponse submitResponse(FeedbackResponse r) { return responseRepository.save(r); }
}
