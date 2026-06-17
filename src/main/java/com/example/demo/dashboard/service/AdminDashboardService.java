package com.example.demo.dashboard.service;

import com.example.demo.dashboard.dto.AuditLogDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.AuditLogRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

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
        dto.setTotalUsers(userRepository.count());
        return dto;
        // TODO

        //dto.setTotalUsers(userRepository.count());
    }
        public List<AuditLogDTO> getAuditLogs() {
            return auditLogRepository.findAll()
                    .stream()
                    .map(log -> new AuditLogDTO(
                            log.getUser().getUserId(),
                            log.getAction(),
                            log.getEntityType(),
                            log.getEntityId(),
                            log.getTimestamp(),
                            log.getOldValue(),
                            log.getNewValue()
                    ))
                    .toList();

        }


    }
