package com.revizia.challengebackend.model;

import com.revizia.challengebackend.dto.AccountHistoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="account_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_history_sequence")
    @SequenceGenerator(name = "account_history_sequence", sequenceName = "ACCOUNT_HISTORY_SEQ")
    @Column(name = "id_account_history")
    private Long id;
    private Date transactionDate;
    private double lastBalance;
    private double amount;
    private String operation;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public static List<AccountHistory> toModelList(List<AccountHistoryDTO> dtos){
        if(dtos != null && !dtos.isEmpty()){
            return dtos.stream().map(AccountHistory::toModel).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    public static AccountHistory toModel(AccountHistoryDTO dto){
        AccountHistory ach = new AccountHistory();
        ach.setTransactionDate(dto.getTransactionDate());
        ach.setLastBalance(dto.getLastBalance());
        ach.setAmount(dto.getAmount());
        ach.setOperation(dto.getOperation());
        return ach;
    }


}
