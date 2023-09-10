package com.revizia.challengebackend.model;

import com.revizia.challengebackend.dto.AccountDTO;
import com.revizia.challengebackend.exceptions.NoBalaceException;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_account")
    private UUID id;

    private Long number;

    private double balance;

    private String type;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountHistory> prevBalances;


    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    @NotNull
    private Date created;

    private Date dateOfModification;

    @NotNull
    private boolean active = true;

    @NotNull
    private boolean deleted = false;

    public void withdrawMoney(double amount) throws NoBalaceException {
        if(balance < amount) throw new NoBalaceException();
        this.prevBalances.add(new AccountHistory(null, new Date(), this.balance, amount, "-" , this));
        this.balance -= amount;
    }

    private void cashDeposit(double amount){
        this.prevBalances.add(new AccountHistory(null, new Date(), this.balance, amount, "+" , this));
        this.balance += amount;
    }

    public static Account toModel(AccountDTO dto){
        Account account = new Account();
        if(dto.getId() != null) {
            account.setId(dto.getId());
        }
        account.setNumber(dto.getNumber());
        account.setBalance(dto.getBalance());
        account.setType(dto.getType());
        if(dto.getPrevBalances()!= null) {
            account.setPrevBalances(AccountHistory.toModelList(dto.getPrevBalances()));
        }else {
            account.setPrevBalances(Arrays.asList(new AccountHistory()));
        }
        return account;
    }

    @PrePersist
    public void prePersist() {
        this.created = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateOfModification = new Date();
    }

}
