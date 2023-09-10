package com.revizia.challengebackend.service;

import com.revizia.challengebackend.dto.CostumerDTO;
import com.revizia.challengebackend.exceptions.AccountAlreadyExistWithAnotherCostumerException;
import com.revizia.challengebackend.exceptions.AccountDoesNotExistException;

import java.util.List;
import java.util.UUID;

public interface ICostumerService {

    List<CostumerDTO> findAll();

    CostumerDTO findById(UUID id);

    CostumerDTO save(CostumerDTO costumer) throws AccountAlreadyExistWithAnotherCostumerException;

    void delete(UUID id);

    CostumerDTO alter(CostumerDTO costumer, UUID id) throws AccountDoesNotExistException;

    CostumerDTO reactivate(UUID id) throws AccountDoesNotExistException;
}
