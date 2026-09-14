package com.utsdevelopers.vms.visitors.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v.tag FROM Visitor v WHERE v.status = 'CHECKED_IN'")
    List<String> findIssuedTagsForUpdate();

    @EntityGraph(attributePaths = {"host","user"})
    Optional<Visitor> findByTagAndStatus(String tag, Status status);

    @EntityGraph(attributePaths = {"host","user"})
    List<Visitor> findByTag(String tag, Pageable pageable);

    @EntityGraph(attributePaths = {"host","user"})
    List<Visitor> findByStatus(Status status, Pageable pageable);

    @EntityGraph(attributePaths = {"host", "user"})
    List<Visitor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName,
            Pageable pageable);

    @EntityGraph(attributePaths = {"host","user"})
    List<Visitor> findByCheckedInTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                             Pageable pageable);

    @EntityGraph(attributePaths = {"host","user"})
    long countByCheckedInTimeBetweenAndStatus(LocalDateTime start,LocalDateTime end, Status status);

    @EntityGraph(attributePaths = {"host","user"})
    @Query("""
            SELECT COUNT(v) FROM Visitor v WHERE v.checkedInTime BETWEEN :start AND :end
            AND v.status IN :statuses    """)
    long countVisitors(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                            @Param("statuses") List<Status> statuses);
}
