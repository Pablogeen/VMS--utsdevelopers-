package com.utsdevelopers.vms.visitors.web;

import com.utsdevelopers.vms.users.User;
import com.utsdevelopers.vms.visitors.domain.VisitorRequest;
import com.utsdevelopers.vms.visitors.domain.VisitorResponse;
import com.utsdevelopers.vms.visitors.domain.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping("/check-in")
    @PreAuthorize("hasAuthority('RECEPTIONIST')")
    public ResponseEntity<String> checkInVisitor(@RequestBody VisitorRequest request,
                                                  @AuthenticationPrincipal User user) {
        log.info("Request made to add a visitor: {}", request.getFirstName());
        String response = visitorService.checkInVisitor(request, user);
        log.info("Visitor added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/check-out")
    @PreAuthorize("hasAuthority('RECEPTIONIST')")
    public ResponseEntity<String> checkOutVisitor(@RequestParam Integer tag) {
        log.info("Request made to check out visitor: {}", tag);
        String response = visitorService.checkOutVisitor(tag);
        log.info("Visitor checked out successfully: {}", tag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VisitorResponse>> getAllVisitors(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        log.info("Request made to get all visitors - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        List<VisitorResponse> response =
                visitorService.getAllVisitors(pageable);
        log.info("Visitors retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/un-checked")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VisitorResponse>> getUncheckedOutVisitors(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size){
        log.info("Request made to get Unchecked out visitors -page {}, size {} ", page,size);
        Pageable pageable = PageRequest.of(page, size);
        List<VisitorResponse> response = visitorService.getUncheckedOutVisitor(pageable);
        log.info("Gotten all unchecked out visitors");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VisitorResponse>> searchVisitors(@RequestParam String keyword,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        log.info("Request made to search visitors - keyword {}, page {}, size {}", keyword, page, size);
        Pageable pageable = PageRequest.of(page, size);
        List<VisitorResponse> response = visitorService.searchVisitors(keyword, pageable);
        log.info("Visitor search completed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-date")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VisitorResponse>> getVisitorsByDate(
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size) {
        log.info("Request made to get visitors between {} and {} - page {}, size {}", startDate, endDate, page, size);
        Pageable pageable = PageRequest.of(page, size);
        List<VisitorResponse> response = visitorService.getVisitorsByDate(startDate, endDate, pageable);
        log.info("Visitors retrieved successfully for the given period");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats/checked-in-today")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getCheckedInVisitorsToday() {
        log.info("Request made to get visitors checked in today");
        long response = visitorService.getCheckedInVisitorsToday();
        log.info("Visitors checked in today: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats/today")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getTotalVisitorsToday() {
        log.info("Request made to get total visitors today");
        long response = visitorService.getTotalVisitorsToday();
        log.info("Total visitors today: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats/week")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getTotalVisitorsThisWeek() {
        log.info("Request made to get total visitors this week");
        long response = visitorService.getTotalVisitorsThisWeek();
        log.info("Total visitors this week: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats/month")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getTotalVisitorsThisMonth() {
        log.info("Request made to get total visitors this month");
        long response = visitorService.getTotalVisitorsThisMonth();
        log.info("Total visitors this month: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
