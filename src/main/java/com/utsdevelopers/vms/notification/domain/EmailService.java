package com.utsdevelopers.vms.notification.domain;

import com.utsdevelopers.vms.visitors.VisitorCheckedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailOutboxRepository outboxRepository;

    @ApplicationModuleListener
    public void visitorCheckIn(VisitorCheckedEvent event) {

        String reference = event.firstName() + "|" +
                event.tag() + "|" +
                event.purpose();

        outboxRepository.save(new EmailOutbox(
                event.visitorEmail(),
                "YOUR VISITOR APPOINTMENT CONFIRMATION",
                EmailType.CHECK_IN,
                reference));
    }

    @ApplicationModuleListener
    public void notifyHost(VisitorCheckedEvent event) {

        String reference = event.firstName() + "|" +
                event.phoneNumber() + "|" +
                event.purpose();

        outboxRepository.save(new EmailOutbox(
                event.hostEmail(),
                "VISITOR WAITING FOR YOU",
                EmailType.NOTIFY_HOST,
                reference));
    }

}