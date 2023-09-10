package com.revizia.challengebackend.controller;

import com.revizia.challengebackend.dto.AccountDTO;
import com.revizia.challengebackend.exceptions.AccountAlreadyExistWithAnotherCostumerException;
import com.revizia.challengebackend.exceptions.BalanceIsUnchangingException;
import com.revizia.challengebackend.exceptions.AccountDoesNotExistException;
import com.revizia.challengebackend.exceptions.CostumerDoesNotExistException;
import com.revizia.challengebackend.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService service;

    public AccountController(IAccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<AccountDTO>> findAll() {
        List<AccountDTO> allAccount = this.service.findAll();
        return new ResponseEntity<>(allAccount, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> findById( @PathVariable String id) {
        AccountDTO account = this.service.findById(UUID.fromString(id));
        if(account != null) {
            return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/", produces="application/json", consumes="application/json")
    public ResponseEntity save(@RequestBody AccountDTO account) {
        try {
            AccountDTO response = this.service.save(account);
            if(response != null)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CostumerDoesNotExistException | AccountAlreadyExistWithAnotherCostumerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity delete(@PathVariable String id) {
        this.service.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/reactivate/{id}", produces="application/json")
    public ResponseEntity reactivate(@PathVariable String id) {
        try {
            AccountDTO account = this.service.reactivate(UUID.fromString(id));
            return new ResponseEntity(account,HttpStatus.OK);
        } catch (AccountDoesNotExistException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity edit(@RequestBody AccountDTO account, @PathVariable String id){
        if(account != null && id != null){
            try {
                AccountDTO response = this.service.alter(account, UUID.fromString(id));
                return new ResponseEntity(response,HttpStatus.OK);
            } catch (AccountDoesNotExistException | BalanceIsUnchangingException e) {
                return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


}
