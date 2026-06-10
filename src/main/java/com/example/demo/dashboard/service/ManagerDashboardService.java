package com.example.demo.dashboard.service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;

public class ManagerDashboardService {
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;

    public ManagerDashboardService(LoanRepository loanRepository, AssetRepository assetRepository) {
        this.loanRepository = loanRepository;
        this.assetRepository = assetRepository;
    }

    public DashboardDTO getManagerDashboard() {

        DashboardDTO dto = new DashboardDTO();


        dto.setPendingLoans(
                //loan repo comunicates with the db
                //SELECT COUNT(*) FROM loan WHERE status = 'PENDING';
                loanRepository.countByStatus(Loan.Status.PENDING)
        );

        dto.setApprovedLoans(
                //retrieves all the data approved
                loanRepository.countByStatus(Loan.Status.APPROVED)
        );

        dto.setRejectedLoans(
                //retrieve where everything is rejected
                loanRepository.countByStatus(Loan.Status.REJECTED)
        );

        dto.setTotalLoans(
                //all the rows of the loans table
                loanRepository.count()
        );

        //the total number of assest
        dto.setTotalAssets(assetRepository.count());

        dto.setAvailableAssets(
                // retrieve all the available assest
                assetRepository.countByStatus(Asset.Status.AVAILABLE)
        );

        dto.setLoanedAssets(
                //retrieve number of assest loaned
                assetRepository.countByStatus(Asset.Status.LOANED)
        );
        dto.setRetiredAssets(
                assetRepository.countByStatus(Asset.Status.RETIRED)
        );

        //dto.setDamagedAssets(
        //retrieve all assets that are damaged
        //assetRepository.countByStatus(Asset.Status.DAMAGED)
        //);

        //dto.setRetiredAssets(
        //retriee all the assest that are retired
        //assetRepository.countByStatus(Asset.Status.RETIRED)
        //);
        return dto;
    }

}
