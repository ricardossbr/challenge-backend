package com.revizia.challengebackend.controller;

import com.revizia.challengebackend.dto.AccountDTO;
import com.revizia.challengebackend.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService service;

    public AccountController(IAccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<AccountDTO>> findAll() {
        //List<ProductDTO> p = service.findAll();
        //return new ResponseEntity<>(p, HttpStatus.OK);
        return new ResponseEntity<>(Arrays.asList(new AccountDTO()), HttpStatus.OK);
    }


}
