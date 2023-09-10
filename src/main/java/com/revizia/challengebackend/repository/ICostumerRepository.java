package com.revizia.challengebackend.repository;

import com.revizia.challengebackend.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICostumerRepository extends JpaRepository<Costumer, UUID> {

     Costumer findCostumerByAccounts_id(UUID id);


}
