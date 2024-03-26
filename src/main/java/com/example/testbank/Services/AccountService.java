package com.example.testbank.Services;

import com.example.testbank.Models.Account;
import com.example.testbank.Repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    public Optional<Account> findById(Long id) {
        return accountRepo.findById(id);
    }
    public Account save(Account account){
        return accountRepo.save(account);
    }
}
