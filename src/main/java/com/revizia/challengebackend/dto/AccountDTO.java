package com.revizia.challengebackend.dto;

import com.revizia.challengebackend.model.Account;
import com.revizia.challengebackend.model.AccountHistory;
import com.revizia.challengebackend.model.Costumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    private UUID id;

    private Long number;

    private double balance;

    private String type;

    private List<AccountHistoryDTO> prevBalances;

    private CostumerDTO costumer;

    private Date created;

    private Date dateOfModification;

    private boolean active;

    private boolean deleted;

    public static List<AccountDTO> toDtos(List<Account> account){
        if(account != null && !account.isEmpty()){
            return account.stream().map(AccountDTO::toDto).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    public static AccountDTO toDto(Account account){
        List<AccountHistoryDTO> histories = Collections.emptyList();
        if(account.getPrevBalances() != null && !account.getPrevBalances().isEmpty()){
            histories = AccountHistoryDTO.toDtos(account.getPrevBalances());
        }
        return AccountDTO.builder()
                .id(account.getId())
                .number(account.getNumber())
                .balance(account.getBalance())
                .type(account.getType())
                .prevBalances(histories)
                .costumer(CostumerDTO.toDto(account.getCostumer()))
                .created(account.getCreated())
                .dateOfModification(account.getDateOfModification())
                .active(account.isActive())
                .deleted(account.isDeleted())
                .build();
    }
}
