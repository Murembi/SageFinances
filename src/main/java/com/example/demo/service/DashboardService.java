package com.example.demo.service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    //dashboard service has acess to the loan data
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;


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

        dto.setTotalAssets(assetRepository.count());

        dto.setAvailableAssets(
                assetRepository.countByStatus(Asset.Status.AVAILABLE)
        );

        dto.setLoanedAssets(
                assetRepository.countByStatus(Asset.Status.LOANED)
        );

        dto.setDamagedAssets(
                assetRepository.countByStatus(Asset.Status.DAMAGED)
        );

        dto.setRetiredAssets(
                assetRepository.countByStatus(Asset.Status.RETIRED)
        );

        return dto;
    }
    // asset dto
    // user dta

}
