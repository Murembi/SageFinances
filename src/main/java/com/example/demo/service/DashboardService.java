package com.example.demo.service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    //dashboard service has acess to the loan data
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;

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
    // user dta

}
