package com.revizia.challengebackend.service.impl;

import com.revizia.challengebackend.dto.AccountDTO;
import com.revizia.challengebackend.dto.CostumerDTO;
import com.revizia.challengebackend.exceptions.AccountAlreadyExistWithAnotherCostumerException;
import com.revizia.challengebackend.exceptions.BalanceIsUnchangingException;
import com.revizia.challengebackend.exceptions.AccountDoesNotExistException;
import com.revizia.challengebackend.exceptions.CostumerDoesNotExistException;
import com.revizia.challengebackend.model.Account;
import com.revizia.challengebackend.model.Costumer;
import com.revizia.challengebackend.repository.IAccountRepository;
import com.revizia.challengebackend.repository.ICostumerRepository;
import com.revizia.challengebackend.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository repository;

    private ICostumerRepository costumerRepository;

    public AccountService(IAccountRepository repository, ICostumerRepository costumerRepository) {
        this.repository = repository;
        this.costumerRepository = costumerRepository;
    }


    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = this.repository.findAll();
        if(accounts != null && !accounts.isEmpty()){
            return AccountDTO.toDtos(accounts);
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public AccountDTO save(AccountDTO account) throws CostumerDoesNotExistException, AccountAlreadyExistWithAnotherCostumerException {
        Costumer costumer = this.verifyCostumer(account);
        Account accountValidated = this.verifyAccount(account, costumer);
        Account savedAccount = this.repository.save(accountValidated);
        if(savedAccount != null){
            return AccountDTO.toDto(savedAccount);
        }
        return null;
    }




    @Override
    @Transactional
    public AccountDTO alter(AccountDTO account, UUID id) throws BalanceIsUnchangingException {
        Account foundAccount = this.repository.findById(id).orElse(null);
        if(foundAccount != null){
            this.repository.deleteById(foundAccount.getId());
            foundAccount.setType(account.getType());
            foundAccount.setNumber(account.getNumber());
            if(foundAccount.getBalance() != account.getBalance()) throw new BalanceIsUnchangingException();
            Account savedAccount = this.repository.save(foundAccount);
            return AccountDTO.toDto(savedAccount);
        }
        return null;
    }

    @Override
    public AccountDTO reactivate(UUID id)throws AccountDoesNotExistException {
        Account account = this.repository.findById(id).orElse(null);
        if(account != null){
            account.setActive(true);
            account.setDeleted(false);
            Account newVersion = this.repository.save(account);
            return AccountDTO.toDto(newVersion);
        }
        throw new AccountDoesNotExistException();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Account account = this.repository.findById(id).orElse(null);
        if(account != null){
            account.setDeleted(true);
            account.setActive(false);
            this.repository.save(account);
        }
        this.repository.deleteById(id);
    }

    @Override
    public AccountDTO findById(UUID id) {
        Account account = this.repository.findById(id).orElse(null);
        if(account != null){
            return AccountDTO.toDto(account);
        }
        return null;
    }

    private Costumer verifyCostumer(AccountDTO account) throws CostumerDoesNotExistException {
        CostumerDTO costumer = account.getCostumer();
        if(costumer != null && costumer.getId() != null){
            Costumer foundCostumer = this.costumerRepository.findById(costumer.getId()).orElse(null);
            if(foundCostumer != null) return foundCostumer;
        }
        throw new CostumerDoesNotExistException();
    }

    private Account verifyAccount(AccountDTO account, Costumer costumer) throws AccountAlreadyExistWithAnotherCostumerException {
        if(account.getId() != null){
            Account repositoryAccount  = this.repository.findById(account.getId()).orElse(null);
            if(repositoryAccount != null){
                if(!costumer.equals(repositoryAccount.getCostumer())){
                    throw new AccountAlreadyExistWithAnotherCostumerException();
                }else{
                    repositoryAccount.setCostumer(costumer);
                    return repositoryAccount;
                }
            }
        }
        Account newAccount = Account.toModel(account);
        newAccount.setCostumer(costumer);
        return newAccount;
    }
}
