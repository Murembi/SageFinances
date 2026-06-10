package com.example.demo.dashboard.service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;

public class AdminDashboardService {
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;

    public AdminDashboardService(LoanRepository loanRepository, AssetRepository assetRepository) {
        this.loanRepository = loanRepository;
        this.assetRepository = assetRepository;
    }

    public DashboardDTO getAdminDashboard() {
        //the total number of assest
        DashboardDTO dto = new DashboardDTO();

        dto.setTotalAssets(assetRepository.count());
        dto.setAvailableAssets(
                // retrieve all the available assest
                assetRepository.countByStatus(Asset.Status.AVAILABLE)
        );
        dto.setRetiredAssets(
                assetRepository.countByStatus(Asset.Status.RETIRED)
        );
        dto.setLoanedAssets(
                //retrieve number of assest loaned
                assetRepository.countByStatus(Asset.Status.LOANED)
        );
        dto.setPendingLoans(
                //loan repo comunicates with the db
                //SELECT COUNT(*) FROM loan WHERE status = 'PENDING';
                loanRepository.countByStatus(Loan.Status.PENDING)
        );
        return dto;
        // TODO
        // Later add:
        // dto.setTotalUsers(userRepository.count());

    }
}
