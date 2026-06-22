package com.example.demo.dashboard.service;

import com.example.demo.dashboard.dto.AvailableAssetDTO;
import com.example.demo.dashboard.dto.MyLoanedAssetDTO;
import com.example.demo.dashboard.dto.PendingLoanDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDashboardService {
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;

    public DashboardDTO getUserDashboard(Long userId){
        DashboardDTO dto = new DashboardDTO();

        dto.setAvailableAssets(
                assetRepository.countByStatus(Asset.Status.AVAILABLE)
        );

        dto.setMyLoans(
                loanRepository.countByUser_UserIdAndStatus(
                        userId,
                        Loan.Status.APPROVED
                )
        );
        //
        dto.setMyPendingRequests(
                loanRepository.countByUser_UserIdAndStatus(
                        userId,
                        Loan.Status.PENDING
                )
        );



        return dto;
    }


    public List<PendingLoanDTO> getMyPendingRequests(Long userId) {

        List<Loan> loans = loanRepository.findByUser_UserIdAndStatus(
                userId,
                Loan.Status.PENDING
        );

        List<PendingLoanDTO> list = new ArrayList<>();

        for (Loan loan : loans) {

            PendingLoanDTO dto = new PendingLoanDTO();

            dto.setAssetTitle(loan.getAsset().getTitle());
            dto.setRequestDate(loan.getRequestDate());
            dto.setStatus(loan.getStatus().name());
            dto.setPhotoPath(loan.getAsset().getPhotoPath());
            list.add(dto);
        }

        return list;
    }
    //available asset

    public List<AvailableAssetDTO> getAvailableAssets() {

        List<Asset> assets = assetRepository.findByStatus(Asset.Status.AVAILABLE);

        List<AvailableAssetDTO> list = new ArrayList<>();

        for (Asset asset : assets) {

            AvailableAssetDTO dto = new AvailableAssetDTO();

            dto.setAssetId(asset.getAssetId());
            dto.setAssetName(asset.getTitle());
            dto.setCategory(asset.getCategory());
            dto.setStatus(asset.getStatus().name());
            dto.setPhotoPath(asset.getPhotoPath());
            list.add(dto);
        }

        return list;
    }

    //myLoaned assets
    public List<MyLoanedAssetDTO> getMyLoanedAssets(Long userId) {

        List<Loan> loans = loanRepository.findByUser_UserIdAndStatus(
                userId,
                Loan.Status.APPROVED
        );

        List<MyLoanedAssetDTO> list = new ArrayList<>();

        for (Loan loan : loans) {

            MyLoanedAssetDTO dto = new MyLoanedAssetDTO();

            dto.setAssetName(loan.getAsset().getTitle());
            dto.setCheckoutDate(loan.getCheckoutDate());
            dto.setDueDate(loan.getDueDate());
            dto.setStatus(loan.getStatus().name());
            dto.setPhotoPath(loan.getAsset().getPhotoPath());

            list.add(dto);
        }

        return list;
    }




}
