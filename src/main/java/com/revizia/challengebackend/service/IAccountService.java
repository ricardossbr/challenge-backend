package com.revizia.challengebackend.service;

import com.revizia.challengebackend.dto.AccountDTO;
import com.revizia.challengebackend.exceptions.AccountAlreadyExistWithAnotherCostumerException;
import com.revizia.challengebackend.exceptions.BalanceIsUnchangingException;
import com.revizia.challengebackend.exceptions.AccountDoesNotExistException;
import com.revizia.challengebackend.exceptions.CostumerDoesNotExistException;

import java.util.List;
import java.util.UUID;

public interface IAccountService {

    List<AccountDTO> findAll();

    AccountDTO findById(UUID id);

    AccountDTO save(AccountDTO account) throws CostumerDoesNotExistException, AccountAlreadyExistWithAnotherCostumerException;

    void delete(UUID id);

    AccountDTO alter(AccountDTO account, UUID id) throws AccountDoesNotExistException, BalanceIsUnchangingException;

    AccountDTO reactivate(UUID id) throws AccountDoesNotExistException;
}
