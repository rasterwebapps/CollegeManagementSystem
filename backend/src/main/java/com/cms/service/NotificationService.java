package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;
    private final MessageRepository messageRepository;
    private final MessageThreadRepository threadRepository;

    public NotificationService(NotificationRepository notificationRepository, NotificationPreferenceRepository preferenceRepository, MessageRepository messageRepository, MessageThreadRepository threadRepository) {
        this.notificationRepository = notificationRepository;
        this.preferenceRepository = preferenceRepository;
        this.messageRepository = messageRepository;
        this.threadRepository = threadRepository;
    }

    public List<Notification> findByRecipient(String keycloakId) { return notificationRepository.findAll().stream().filter(n -> n.getRecipientKeycloakId().equals(keycloakId)).toList(); }
    public Notification createNotification(Notification n) { return notificationRepository.save(n); }
    public void markRead(Long id) { notificationRepository.findById(id).ifPresent(n -> { n.setReadStatus(true); notificationRepository.save(n); }); }
    public void markAllRead(String keycloakId) { findByRecipient(keycloakId).forEach(n -> { n.setReadStatus(true); notificationRepository.save(n); }); }

    public NotificationPreference findPreference(String keycloakId) { return preferenceRepository.findAll().stream().filter(p -> p.getKeycloakId().equals(keycloakId)).findFirst().orElse(null); }
    public NotificationPreference savePreference(NotificationPreference p) { return preferenceRepository.save(p); }

    public List<Message> findMessages(String keycloakId) { return messageRepository.findAll().stream().filter(m -> m.getSenderKeycloakId().equals(keycloakId) || m.getReceiverKeycloakId().equals(keycloakId)).toList(); }
    public Message sendMessage(Message m) { return messageRepository.save(m); }
    public void markMessageRead(Long id) { messageRepository.findById(id).ifPresent(m -> { m.setReadStatus(true); messageRepository.save(m); }); }

    public List<MessageThread> findThreads(String keycloakId) { return threadRepository.findAll(); }
    public MessageThread createThread(MessageThread t) { return threadRepository.save(t); }
}
