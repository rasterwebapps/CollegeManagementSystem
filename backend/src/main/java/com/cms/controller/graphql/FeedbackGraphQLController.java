package com.cms.controller.graphql;

import java.util.List;
import java.util.Map;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.cms.model.*;
import com.cms.repository.*;
import com.cms.service.FeedbackService;

@Controller
public class FeedbackGraphQLController {
    private final FeedbackService feedbackService;
    private final GrievanceRepository grievanceRepo;

    public FeedbackGraphQLController(FeedbackService feedbackService, GrievanceRepository grievanceRepo) {
        this.feedbackService = feedbackService;
        this.grievanceRepo = grievanceRepo;
    }

    @QueryMapping
    public List<GrievanceComment> grievanceComments(@Argument Long grievanceId) {
        return feedbackService.findCommentsByGrievance(grievanceId);
    }

    @QueryMapping
    public List<FeedbackForm> feedbackForms(@Argument String status) {
        List<FeedbackForm> all = feedbackService.findAllForms();
        if (status != null) return all.stream().filter(f -> f.getStatus().equals(status)).toList();
        return all;
    }

    @QueryMapping
    public FeedbackForm feedbackForm(@Argument Long id) { return feedbackService.findFormById(id); }

    @QueryMapping
    public List<FeedbackResponse> feedbackResponses(@Argument Long formId) {
        return feedbackService.findResponsesByForm(formId);
    }

    @MutationMapping
    public GrievanceComment addGrievanceComment(@Argument Map<String, Object> input) {
        GrievanceComment c = new GrievanceComment();
        if (input.get("grievanceId") != null) c.setGrievance(grievanceRepo.findById(Long.valueOf(input.get("grievanceId").toString())).orElse(null));
        c.setCommenterKeycloakId((String) input.get("commentBy"));
        c.setCommenterName((String) input.get("commentByName"));
        c.setComment((String) input.get("content"));
        return feedbackService.addComment(c);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FeedbackForm createFeedbackForm(@Argument Map<String, Object> input) {
        FeedbackForm f = new FeedbackForm();
        f.setTitle((String) input.get("title"));
        f.setTargetAudience((String) input.get("targetAudience"));
        f.setQuestions((String) input.get("questions"));
        f.setAnonymous(Boolean.TRUE.equals(input.get("anonymous")));
        f.setStatus("ACTIVE");
        return feedbackService.createForm(f);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FeedbackForm updateFeedbackForm(@Argument Long id, @Argument Map<String, Object> input) {
        FeedbackForm f = feedbackService.findFormById(id);
        if (f == null) return null;
        if (input.containsKey("title")) f.setTitle((String) input.get("title"));
        if (input.containsKey("status")) f.setStatus((String) input.get("status"));
        return feedbackService.updateForm(f);
    }

    @MutationMapping
    public FeedbackResponse submitFeedbackResponse(@Argument Map<String, Object> input) {
        FeedbackResponse r = new FeedbackResponse();
        if (input.get("formId") != null) r.setForm(feedbackService.findFormById(Long.valueOf(input.get("formId").toString())));
        r.setRespondentKeycloakId((String) input.get("respondentKeycloakId"));
        r.setAnswers((String) input.get("answers"));
        return feedbackService.submitResponse(r);
    }
}
