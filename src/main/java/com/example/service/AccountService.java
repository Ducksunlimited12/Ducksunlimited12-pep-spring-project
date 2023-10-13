package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;



@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> registerAccount(Account account) {
        try {
            if (account.getUsername() == null || account.getUsername().isBlank()) {
                throw new IllegalArgumentException("Username cannot be blank");
            }

            if (account.getPassword() == null || account.getPassword().length() < 4) {
                throw new IllegalArgumentException("Password must be at least 4 characters long");
            }

            Account existingAccount = accountRepository.findByUsername(account.getUsername());
            if (existingAccount != null) {
                throw new DuplicateKeyException("An account with this username already exists");
            }

            Account savedAccount = accountRepository.save(account);
            return new ResponseEntity<>(savedAccount, HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("An account with this username already exists", HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
            if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
                return new ResponseEntity<>(existingAccount, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(account, HttpStatus.UNAUTHORIZED);
            }
            
    }

    


    public boolean accountExistsById(Integer userId) {
        return accountRepository.findById(userId).isPresent();
    }
}
