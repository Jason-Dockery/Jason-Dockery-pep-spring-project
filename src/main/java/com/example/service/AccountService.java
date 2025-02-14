package com.example.service;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    private AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    //##1
    public ResponseEntity<Account> addUser(Account account){
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(activeUsername(account.getUsername()) == true){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
        Account addedAccount = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(addedAccount);
        }
    }
    //##2
    public ResponseEntity<Account> findAccount(Account account){
        Account registeredAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(registeredAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        }
    }

    public boolean activeUsername(String username){
        return accountRepository.existsByUsername(username);
    }

}
