package com.example.demo.dashboard.service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;

public class UserDashboardService {
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;

    public UserDashboardService(LoanRepository loanRepository, AssetRepository assetRepository) {
        this.loanRepository = loanRepository;
        this.assetRepository = assetRepository;
    }
    public DashboardDTO getUserDashboard(Long userId){
        DashboardDTO dto = new DashboardDTO();

        dto.setMyLoans(
                loanRepository.countByUser_UserId(userId)
        );
        //
        dto.setMyPendingRequests(
                // SELECT COUNT(*) FROM loan WHERE user_id = 3 AND status = 'PENDING';
                loanRepository.countByUser_UserIdAndStatus(userId, Loan.Status.PENDING)
        );
        //dto.setMyApprovedLoans(
        //loanRepository.countByUser_UserIdAndStatus(userId, Loan.Status.APPROVED)
//        );
//        dto.setMyRejectedLoans(
//                loanRepository.countByUser_UserIdAndStatus(userId, Loan.Status.REJECTED)
//        );

        return dto;
    }
}
