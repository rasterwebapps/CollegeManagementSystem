package com.cms.controller.graphql;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.cms.model.Announcement;
import com.cms.model.Grievance;
import com.cms.model.Notification;
import com.cms.model.NotificationPreference;
import com.cms.model.Message;
import com.cms.model.MessageThread;
import com.cms.service.AnnouncementService;
import com.cms.service.GrievanceService;
import com.cms.service.NotificationService;

@Controller
public class CommunicationGraphQLController {

    private final AnnouncementService announcementService;
    private final GrievanceService grievanceService;
    private final NotificationService notificationService;

    public CommunicationGraphQLController(AnnouncementService announcementService,
                                           GrievanceService grievanceService,
                                           NotificationService notificationService) {
        this.announcementService = announcementService;
        this.grievanceService = grievanceService;
        this.notificationService = notificationService;
    }

    @QueryMapping
    public List<Announcement> announcements() {
        return announcementService.findAll();
    }

    @QueryMapping
    public Announcement announcement(@Argument Long id) {
        return announcementService.findById(id);
    }

    @QueryMapping
    public List<Announcement> announcementsByCategory(@Argument String category) {
        return announcementService.findByCategory(category);
    }

    @QueryMapping
    public List<Announcement> pinnedAnnouncements() {
        return announcementService.findPinned();
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public Announcement createAnnouncement(@Argument Map<String, Object> input) {
        Announcement ann = new Announcement();
        ann.setTitle((String) input.get("title"));
        ann.setContent((String) input.get("content"));
        ann.setCategory((String) input.get("category"));
        ann.setTargetRoles((String) input.get("targetRoles"));
        ann.setTargetDepartments((String) input.get("targetDepartments"));
        ann.setPriority(input.get("priority") != null ? (String) input.get("priority") : "NORMAL");
        ann.setPublishDate(input.get("publishDate") != null ? LocalDate.parse((String) input.get("publishDate")) : LocalDate.now());
        if (input.get("expiryDate") != null) ann.setExpiryDate(LocalDate.parse((String) input.get("expiryDate")));
        ann.setAttachmentUrl((String) input.get("attachmentUrl"));
        ann.setPinned(input.get("pinned") != null ? (Boolean) input.get("pinned") : false);
        ann.setPublishedBy((String) input.get("publishedBy"));
        return announcementService.create(ann);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public Announcement updateAnnouncement(@Argument Long id, @Argument Map<String, Object> input) {
        Announcement ann = announcementService.findById(id);
        if (input.containsKey("title")) ann.setTitle((String) input.get("title"));
        if (input.containsKey("content")) ann.setContent((String) input.get("content"));
        if (input.containsKey("category")) ann.setCategory((String) input.get("category"));
        if (input.containsKey("targetRoles")) ann.setTargetRoles((String) input.get("targetRoles"));
        if (input.containsKey("priority")) ann.setPriority((String) input.get("priority"));
        if (input.containsKey("pinned")) ann.setPinned((Boolean) input.get("pinned"));
        if (input.containsKey("status")) ann.setStatus((String) input.get("status"));
        if (input.containsKey("expiryDate")) ann.setExpiryDate(LocalDate.parse((String) input.get("expiryDate")));
        return announcementService.update(id, ann);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteAnnouncement(@Argument Long id) {
        announcementService.delete(id);
        return true;
    }

    @QueryMapping
    public List<Grievance> grievances() {
        return grievanceService.findAll();
    }

    @QueryMapping
    public Grievance grievance(@Argument Long id) {
        return grievanceService.findById(id);
    }

    @QueryMapping
    public Grievance grievanceByTicketNumber(@Argument String ticketNumber) {
        return grievanceService.findByTicketNumber(ticketNumber);
    }

    @QueryMapping
    public List<Grievance> grievancesByStatus(@Argument String status) {
        return grievanceService.findByStatus(status);
    }

    @QueryMapping
    public List<Grievance> myGrievances(@Argument String keycloakId) {
        return grievanceService.findByComplainant(keycloakId);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Grievance createGrievance(@Argument Map<String, Object> input) {
        Grievance g = new Grievance();
        g.setComplainantKeycloakId((String) input.get("complainantKeycloakId"));
        g.setComplainantName((String) input.get("complainantName"));
        g.setComplainantRole((String) input.get("complainantRole"));
        g.setCategory((String) input.get("category"));
        g.setSubject((String) input.get("subject"));
        g.setDescription((String) input.get("description"));
        g.setPriority(input.get("priority") != null ? (String) input.get("priority") : "MEDIUM");
        g.setIsAnonymous(input.get("isAnonymous") != null ? (Boolean) input.get("isAnonymous") : false);
        g.setAttachmentUrl((String) input.get("attachmentUrl"));
        return grievanceService.create(g);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Grievance updateGrievanceStatus(@Argument Long id, @Argument String status,
                                            @Argument String assignedTo, @Argument String resolution) {
        Grievance g = grievanceService.findById(id);
        g.setStatus(status);
        if (assignedTo != null) g.setAssignedTo(assignedTo);
        if (resolution != null) g.setResolution(resolution);
        return grievanceService.updateStatus(id, status, resolution);
    }

    // Notifications
    @QueryMapping
    public List<Notification> notifications(@Argument String recipientKeycloakId, @Argument Boolean unreadOnly) {
        List<Notification> notifs = notificationService.findByRecipient(recipientKeycloakId);
        if (Boolean.TRUE.equals(unreadOnly)) return notifs.stream().filter(n -> !n.isReadStatus()).toList();
        return notifs;
    }

    @QueryMapping
    public NotificationPreference notificationPreference(@Argument String userKeycloakId) {
        return notificationService.findPreference(userKeycloakId);
    }

    @MutationMapping
    public boolean markNotificationRead(@Argument Long id) {
        notificationService.markRead(id);
        return true;
    }

    @MutationMapping
    public boolean markAllNotificationsRead(@Argument String recipientKeycloakId) {
        notificationService.markAllRead(recipientKeycloakId);
        return true;
    }

    @MutationMapping
    public NotificationPreference saveNotificationPreference(@Argument Map<String, Object> input) {
        NotificationPreference p = new NotificationPreference();
        p.setKeycloakId((String) input.get("userKeycloakId"));
        p.setCategory((String) input.getOrDefault("category", "GENERAL"));
        p.setEmailEnabled(Boolean.TRUE.equals(input.get("emailEnabled")));
        p.setSmsEnabled(Boolean.TRUE.equals(input.get("smsEnabled")));
        p.setPushEnabled(Boolean.TRUE.equals(input.get("pushEnabled")));
        return notificationService.savePreference(p);
    }

    // Messages
    @QueryMapping
    public List<Message> messages(@Argument String userKeycloakId, @Argument Long threadId) {
        List<Message> msgs = notificationService.findMessages(userKeycloakId);
        if (threadId != null) return msgs.stream().filter(m -> m.getThread() != null && m.getThread().getId().equals(threadId)).toList();
        return msgs;
    }

    @QueryMapping
    public List<MessageThread> messageThreads(@Argument String userKeycloakId) {
        return notificationService.findThreads(userKeycloakId);
    }

    @MutationMapping
    public Message sendMessage(@Argument Map<String, Object> input) {
        Message m = new Message();
        m.setSenderKeycloakId((String) input.get("senderKeycloakId"));
        m.setReceiverKeycloakId((String) input.get("receiverKeycloakId"));
        m.setSubject((String) input.get("subject"));
        m.setBody((String) input.get("body"));
        m.setReadStatus(false);
        return notificationService.sendMessage(m);
    }

    @MutationMapping
    public boolean markMessageRead(@Argument Long id) {
        notificationService.markMessageRead(id);
        return true;
    }
}
