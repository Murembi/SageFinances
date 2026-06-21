package com.example.demo.dashboard.service;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.dashboard.dto.PendingLoanDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerDashboardService {
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;

    public DashboardDTO getManagerDashboard() {

        DashboardDTO dto = new DashboardDTO();

//count stats
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
        dto.setOverdueLoans(
                loanRepository.countByStatusAndDueDateBefore(
                        Loan.Status.APPROVED,
                        LocalDateTime.now()
                ));

        return dto;
    }

    //returns tables
    public List<PendingLoanDTO> getPendingLoans() {
        return loanRepository.findByStatus(Loan.Status.PENDING)
                .stream()
                .map(loan -> new PendingLoanDTO(
                        loan.getLoanId(),
                        loan.getUser().getName(),
                        loan.getAsset().getTitle(),
                        loan.getRequestDate(),
                        loan.getDueDate(),
                        loan.getStatus().name()
                ))
                .toList();
    }
//    public List<Loan> getPendingLoans() {
//        return loanRepository.findByStatus(Loan.Status.PENDING);
//    }

    public List<Asset> getAvailableAssets() {
        return assetRepository.findByStatus(Asset.Status.AVAILABLE);
    }

    public List<Loan> getOverdueLoans() {
        return loanRepository.findByStatusAndDueDateBefore(
                Loan.Status.APPROVED,
                LocalDateTime.now()
        );
    }

}


