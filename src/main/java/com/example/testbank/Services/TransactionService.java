package com.example.testbank.Services;

import com.example.testbank.InsufficientFundsException;
import com.example.testbank.Models.Account;
import com.example.testbank.Models.Transaction;
import com.example.testbank.Repos.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountService accountService;

    public Transaction transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws AccountNotFoundException {

        Account fromAccount = findAccountById(fromAccountId);
        Account toAccount = findAccountById(toAccountId);

        validateTransfer(fromAccount, toAccount, amount);

        Transaction successTransaction = new Transaction(fromAccount, toAccount, amount);

        transactionRepo.save(successTransaction);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountService.save(fromAccount);
        accountService.save(toAccount);

        return successTransaction;
    }

    private void validateTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new IllegalArgumentException("Перевод средств на один и тот же счет невозможен");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть больше нуля");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }
    }

    private Account findAccountById(Long id) throws AccountNotFoundException {
        return accountService.findById(id).orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }

}
