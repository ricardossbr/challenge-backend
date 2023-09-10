package com.revizia.challengebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revizia.challengebackend.model.Account;
import com.revizia.challengebackend.model.Costumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostumerDTO {
    private UUID id;

    private String name;

    private String document;

    private String address;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date created;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfModification;

    private boolean active;

    private boolean deleted;

    private List<AccountDTO> accounts;

    public static CostumerDTO toDto(Costumer costumer){
        List<AccountDTO> dtos = Collections.emptyList();
        if(costumer.getAccounts() != null){
            List<Account> accounts1 = costumer.getAccounts();
            dtos = accounts1.stream().map(AccountDTO::toDto).collect(Collectors.toList());
        }
        return CostumerDTO.builder()
                .id(costumer.getId())
                .name(costumer.getName())
                .document(costumer.getDocument())
                .address(costumer.getAddress())
                .birthday(costumer.getBirthday())
                .created(costumer.getCreated())
                .dateOfModification(costumer.getDateOfModification() != null ? costumer.getDateOfModification() : null )
                .active(costumer.isActive())
                .deleted(costumer.isDeleted())
                .accounts(dtos)
                .build();
    }
}
