package com.example.testbank.Controllers;

import com.example.testbank.Models.Transaction;
import com.example.testbank.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PostMapping("/transfer")
    public String transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount) throws AccountNotFoundException {

        Transaction transaction = transactionService.transfer(fromAccountId, toAccountId, amount);
        return "Транзакция успешна";
    }
}
