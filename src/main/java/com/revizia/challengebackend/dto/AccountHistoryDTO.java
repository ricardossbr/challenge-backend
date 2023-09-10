package com.revizia.challengebackend.dto;

import com.revizia.challengebackend.model.AccountHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountHistoryDTO {
    private Date transactionDate;
    private double lastBalance;
    private double amount;
    private String operation;

    public static List<AccountHistoryDTO> toDtos(List<AccountHistory> prevBalances){
        if(prevBalances != null && !prevBalances.isEmpty()){
            return prevBalances.stream().map(AccountHistoryDTO::toDto).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static AccountHistoryDTO toDto(AccountHistory prevBalance){
        return AccountHistoryDTO.builder()
                .transactionDate(prevBalance.getTransactionDate())
                .lastBalance(prevBalance.getLastBalance())
                .amount(prevBalance.getAmount())
                .operation(prevBalance.getOperation())
                .build();
    }
}
