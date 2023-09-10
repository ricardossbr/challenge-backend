package com.revizia.challengebackend.controller;

import com.revizia.challengebackend.dto.CostumerDTO;
import com.revizia.challengebackend.exceptions.AccountAlreadyExistWithAnotherCostumerException;
import com.revizia.challengebackend.exceptions.AccountDoesNotExistException;
import com.revizia.challengebackend.service.ICostumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/costumer")
public class CostumerController {

    private ICostumerService service;

    public CostumerController(ICostumerService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<CostumerDTO>> findAll() {
        List<CostumerDTO> allCostumer = this.service.findAll();
        return new ResponseEntity<>(allCostumer, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CostumerDTO> findById( @PathVariable String id) {
        CostumerDTO costumer = this.service.findById(UUID.fromString(id));
        if(costumer != null) {
            return new ResponseEntity<>(costumer, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/", produces="application/json", consumes="application/json")
    public ResponseEntity save(@RequestBody CostumerDTO costumer) throws AccountAlreadyExistWithAnotherCostumerException {
        CostumerDTO response = this.service.save(costumer);
        if(response != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity delete(@PathVariable String id) {
        this.service.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/reactivate/{id}", produces="application/json")
    public ResponseEntity reactivate(@PathVariable String id) {
        try {
            CostumerDTO costumer = this.service.reactivate(UUID.fromString(id));
            return new ResponseEntity(costumer,HttpStatus.OK);
        } catch (AccountDoesNotExistException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity edit(@RequestBody CostumerDTO costumer, @PathVariable String id){
        if(costumer != null && id != null){
            try {
                CostumerDTO response = this.service.alter(costumer, UUID.fromString(id));
                return new ResponseEntity(response,HttpStatus.OK);
            } catch (AccountDoesNotExistException e) {
                return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
