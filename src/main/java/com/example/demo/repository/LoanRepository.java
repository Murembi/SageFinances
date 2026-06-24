package com.example.demo.repository;

import com.example.demo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByStatus(Loan.Status status);

    List<Loan> findByStatus(Loan.Status status);

    List<Loan> findByUser_UserId(Long userId);

    boolean existsByUser_UserIdAndAsset_AssetIdAndStatus(
            Long userId,
            Long assetId,
            Loan.Status status);

    long countByUser_UserIdAndStatus(Long userId, Loan.Status status);

    List<Loan> findByUser_UserIdAndStatus(Long userId, Loan.Status status);

    long countByDueDateBeforeAndReturnDateIsNull(
            LocalDateTime dateTime);

    List<Loan> findByStatusAndDueDateBefore(
            Loan.Status status,
            LocalDateTime now
    );
    long countByStatusAndDueDateBefore(
            Loan.Status status,
            LocalDateTime now
    );

    List<Loan> findByUser_NameContainingIgnoreCase(String name);
    List<Loan> findByAsset_TitleContainingIgnoreCase(String title);

    @Query("""
        select l from Loan l
        join fetch l.user
        join fetch l.asset
        where l.status = :status
        """)
    List<Loan> findByStatusWithUserAndAsset(Loan.Status status);
}



