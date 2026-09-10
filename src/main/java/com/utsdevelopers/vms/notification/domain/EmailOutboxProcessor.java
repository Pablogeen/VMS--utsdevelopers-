package com.utsdevelopers.vms.notification.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailOutboxProcessor {


    private final EmailOutboxRepository outboxRepository;
    private final EmailSender emailSender;
    private final EmailBuilder emailBuilder;


    @Scheduled(fixedDelay = 30000)
    public void process() {
        List<EmailOutbox> pending = outboxRepository
                                    .findByStatus(EmailStatus.PENDING);
        log.info("Pending Emails: {}",pending);

        for (EmailOutbox outbox : pending) {
            try {
                String html = buildHtml(outbox);
                emailSender.sendEmail(outbox.getRecipient(), outbox.getSubject(), html);
                log.info("Email sent to :{}",outbox.getRecipient());
                outbox.markSent();
                log.info("Email sent successfully to {} [type={}]", outbox.getRecipient(), outbox.getEmailType());
            } catch (Exception e) {
                outbox.markFailed();
                log.error("Failed to send email to {} [type={}, attempt={}]: {}",
                        outbox.getRecipient(), outbox.getEmailType(), outbox.getRetryCount(), e.getMessage());
            }
            outboxRepository.save(outbox);
        }
    }

    private String buildHtml(EmailOutbox outbox) {

        return switch (outbox.getEmailType()) {
            case CHECK_IN -> {
                String[] parts = outbox.getReference().split("\\|", 3);
                yield emailBuilder.buildCheckInEmail(
                        parts[0],  // firstname
                        parts[1],  // tag
                        parts[2]);     // purpose
            }
            case NOTIFY_HOST -> {
                String[] parts = outbox.getReference().split("\\|", 3);
                yield emailBuilder.buildNotifyHostEmail(
                        parts[0],  // firstname
                        parts[1],  // phoneNumber
                        parts[2]);     // purpose
            }

        };
    }

}
