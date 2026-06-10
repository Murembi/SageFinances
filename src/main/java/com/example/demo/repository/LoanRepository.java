package com.example.demo.repository;

import com.example.demo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    long countByUser_UserId(Long userId);

    long countByUser_UserIdAndStatus(Long userId, Loan.Status status);
}



