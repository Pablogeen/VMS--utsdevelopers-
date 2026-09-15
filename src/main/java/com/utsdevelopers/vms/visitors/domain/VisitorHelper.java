package com.utsdevelopers.vms.visitors.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitorHelper {

    private final VisitorRepository visitorRepository;

    private static final int MIN_TAG = 1;
    private static final int MAX_TAG = 50;

    @Transactional
    public String generateTag() {
        log.info("Generating next available tag");

        List<String> issuedTags = visitorRepository.findIssuedTagsForUpdate();
        Set<String> issued = new HashSet<>(issuedTags);

        for (int candidate = MIN_TAG; candidate <= MAX_TAG; candidate++) {

            String tag = format(candidate);

            if (!issued.contains(tag)) {
                log.info("Tag generated: {}", tag);
                return tag;
            }
        }

        log.warn("No tags available — all {} tags currently checked in", MAX_TAG);
        throw new NoTargsAvailableException("NO TAGS AVAILABLE");
    }

    private String format(int tagNumber) {
        return String.format("%03d", tagNumber);
    }
}
