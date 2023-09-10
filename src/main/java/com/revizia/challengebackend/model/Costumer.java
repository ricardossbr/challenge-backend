package com.revizia.challengebackend.model;

import com.revizia.challengebackend.dto.CostumerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="costumer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_costumer")
    private UUID id;

    private String name;

    private String document;

    private String address;

    private Date birthday;

    private Date created;

    private Date dateOfModification;

    @OneToMany(mappedBy = "costumer", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @NotNull
    private boolean active = true;

    @NotNull
    private boolean deleted = false;

    public static Costumer toModel(CostumerDTO dto){
        Costumer costumer = new Costumer();
        if(dto.getId() != null) {
            costumer.setId(dto.getId());
        }
        costumer.setName(dto.getName());
        costumer.setDocument(dto.getDocument());
        costumer.setAddress(dto.getAddress());
        costumer.setBirthday(dto.getBirthday());
        if(dto.getAccounts() != null){
            costumer.setAccounts(dto.getAccounts().stream().map(Account::toModel).collect(Collectors.toList()));
        }
        return costumer;
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
