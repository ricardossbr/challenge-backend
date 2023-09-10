package com.revizia.challengebackend.service.impl;

import com.revizia.challengebackend.dto.AccountDTO;
import com.revizia.challengebackend.dto.CostumerDTO;
import com.revizia.challengebackend.exceptions.AccountAlreadyExistWithAnotherCostumerException;
import com.revizia.challengebackend.exceptions.AccountDoesNotExistException;
import com.revizia.challengebackend.model.Account;
import com.revizia.challengebackend.model.Costumer;
import com.revizia.challengebackend.repository.IAccountRepository;
import com.revizia.challengebackend.repository.ICostumerRepository;
import com.revizia.challengebackend.service.ICostumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CostumerService implements ICostumerService {

    private ICostumerRepository repository;

    private IAccountRepository accountRepository;

    public CostumerService(ICostumerRepository repository) {

        this.repository = repository;
    }


    @Override
    public List<CostumerDTO> findAll() {
        List<Costumer> costumers = this.repository.findAll();
        if(costumers != null && !costumers.isEmpty()){
            return costumers.stream().parallel()
                    .map(CostumerDTO::toDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public CostumerDTO findById(UUID id) {
        Costumer costumer = this.repository.findById(id).orElse(null);
        if(costumer != null){
            return CostumerDTO.toDto(costumer);
        }
        return null;
    }

    @Override
    @Transactional
    public CostumerDTO save(CostumerDTO costumer) throws AccountAlreadyExistWithAnotherCostumerException {
        List<Account> accounts = this.verifyAccountsIsOk(costumer);
        Costumer model = Costumer.toModel(costumer);
        if(!accounts.isEmpty()){
            model.setAccounts(accounts);
        }
        Costumer savedCostumer = this.repository.save(model);
        if(savedCostumer != null){
            return CostumerDTO.toDto(savedCostumer);
        }
        return null;
    }

    private List<Account> verifyAccountsIsOk(CostumerDTO costumer) throws AccountAlreadyExistWithAnotherCostumerException {
        List<Account> accounts = new ArrayList<>();
        if(costumer.getAccounts() != null){
            for (AccountDTO dto: costumer.getAccounts()) {
                Costumer foundCostumer = this.repository.findCostumerByAccounts_id(dto.getId());
                if(foundCostumer != null){
                    if(costumer.equals(foundCostumer)){
                        accounts.add(Account.toModel(dto));
                    }else {
                        throw new AccountAlreadyExistWithAnotherCostumerException();
                    }
                }else {
                    Account saved = this.accountRepository.save(Account.toModel(dto));
                    accounts.add(saved);
                }
            }
            return accounts;
        }
        return Collections.emptyList();
    }

    @Override
    public void delete(UUID id) {
        Costumer costumer = this.repository.findById(id).orElse(null);
        if(costumer != null){
            costumer.setDeleted(true);
            costumer.setActive(false);
            this.repository.save(costumer);
        }
    }

    @Override
    public CostumerDTO alter(CostumerDTO costumer, UUID id) throws AccountDoesNotExistException {
        Costumer foundCostumer = this.repository.findById(id).orElse(null);
        if(foundCostumer != null){
            foundCostumer.setName(costumer.getName());
            foundCostumer.setDocument(costumer.getDocument());
            foundCostumer.setAddress(costumer.getAddress());
            foundCostumer.setBirthday(costumer.getBirthday());
            Costumer newVersion = this.repository.save(foundCostumer);
            return CostumerDTO.toDto(newVersion);
        }
        throw new AccountDoesNotExistException();
    }

    @Override
    public CostumerDTO reactivate(UUID id) throws AccountDoesNotExistException {
        Costumer costumer = this.repository.findById(id).orElse(null);
        if(costumer != null){
            costumer.setDeleted(false);
            costumer.setActive(true);
            this.repository.save(costumer);
        }
        throw new AccountDoesNotExistException();
    }
}
