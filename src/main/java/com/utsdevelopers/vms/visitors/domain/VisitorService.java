package com.utsdevelopers.vms.visitors.domain;

import com.utsdevelopers.vms.users.User;
import com.utsdevelopers.vms.visitors.VisitorCheckedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final ModelMapper modelMapper;
    private final VisitorHelper visitorHelper;
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public String checkInVisitor(VisitorRequest request, User user) {
        log.info("About to check in a visitor");
        Visitor visitors = modelMapper.map(request, Visitor.class);

        String generatedTag = visitorHelper.generateTag();
        log.info("Tag generated successfully");

        visitors.setTag(generatedTag);
        visitors.setUser(user);
        visitors.setCheckedInTime(LocalDateTime.now());
        visitors.setStatus(Status.CHECKED_IN);
        Employee employee = employeeRepository.findById(request.getHostId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        visitors.setHost(employee);

        visitorRepository.save(visitors);
        log.info("Visitor saved in the db");

        eventPublisher.publishEvent(
                new VisitorCheckedEvent(visitors.getEmail(),visitors.getHost().getEmail(), visitors.getFirstName(),
                        visitors.getPurpose(), visitors.getTag(), visitors.getPhoneNumber()));

        return generatedTag;
    }

    @Transactional
    public String checkOutVisitor(String tag) {
        log.info("About to check out a visitor with tag {}: ",tag);

        Visitor visitor = visitorRepository.findByTagAndStatus(tag, Status.CHECKED_IN)
                .orElseThrow(() -> new VisitorNotFoundException("VISITOR WITH TAG NOT FOUND"));

        visitor.setStatus(Status.CHECKED_OUT);
        visitor.setCheckedOutTime(LocalDateTime.now());

        visitorRepository.save(visitor);
        log.info("Visitor has been checked out successfully");

        String response = "VISITOR WITH TAG NUMBER "+tag+" HAS BEEN CHECKED OUT SUCCESSFULLY";
        return response;
    }


    public List<VisitorResponse> getAllVisitors(Pageable pageable) {
        log.info("Generating all visitors from the database");
        return visitorRepository.findAll(pageable).getContent().stream()
                .map(visitor -> {
                    VisitorResponse response = modelMapper.map(visitor, VisitorResponse.class);
                    response.setHostId(visitor.getHost().getId());
                    response.setUserId(visitor.getUser().getId());
                    return response;
                }).toList();
    }

    public List<VisitorResponse> getUncheckedOutVisitor(Pageable pageable) {
        return visitorRepository.findByStatus(Status.CHECKED_IN, pageable).stream()
                .map(visitors -> modelMapper.map(visitors, VisitorResponse.class)).toList();
    }

    public List<VisitorResponse> searchVisitors(String keyword, Pageable pageable) {

        List<Visitor> visitors;

        if (keyword.matches("\\d+")) {
            visitors = visitorRepository.findByTag(keyword, pageable);
        } else {
            visitors = visitorRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                            keyword,
                            keyword,
                            pageable);
        }
        if (visitors.isEmpty()) {
            throw new VisitorNotFoundException("VISITOR NOT FOUND");
        }

        return visitors.stream()
                .map(visitor -> modelMapper.map(visitor, VisitorResponse.class))
                .toList();
    }

    public List<VisitorResponse> getVisitorsByDate(
            LocalDate startDate, LocalDate endDate, Pageable pageable) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Visitor> visitors = visitorRepository.findByCheckedInTimeBetween(startDateTime, endDateTime, pageable);
        log.info("Visitors gotten per the dates");
        return visitors.stream()
                .map(visitor -> modelMapper.map(visitor, VisitorResponse.class))
                .toList();
    }


    public long getCheckedInVisitorsToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return visitorRepository.countByCheckedInTimeBetweenAndStatus(start, end, Status.CHECKED_IN);
    }

    public long getTotalVisitorsToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return visitorRepository.countVisitors(start, end, List.of(Status.CHECKED_IN, Status.CHECKED_OUT));
    }

    public long getTotalVisitorsThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        LocalDateTime start = startOfWeek.atStartOfDay();
        LocalDateTime end = endOfWeek.atTime(LocalTime.MAX);
        return visitorRepository.countVisitors(start, end, List.of(Status.CHECKED_IN, Status.CHECKED_OUT));
    }

    public long getTotalVisitorsThisMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        LocalDateTime start = startOfMonth.atStartOfDay();
        LocalDateTime end = endOfMonth.atTime(LocalTime.MAX);
        return visitorRepository.countVisitors(start, end, List.of(Status.CHECKED_IN, Status.CHECKED_OUT));
    }
}

