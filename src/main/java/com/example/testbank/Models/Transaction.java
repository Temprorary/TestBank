package com.example.testbank.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private BigDecimal amount;

    @ManyToOne
    private Account debitAcc;

    @ManyToOne
    private Account creditAcc;

    public Transaction() {
    }

    public Transaction(Account fromAccount, Account toAccount, BigDecimal amount){
        this.amount = amount;
        this.creditAcc = fromAccount;
        this.debitAcc = fromAccount;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getDebitAcc() {
        return debitAcc;
    }

    public void setDebitAcc(Account debitAcc) {
        this.debitAcc = debitAcc;
    }

    public Account getCreditAcc() {
        return creditAcc;
    }

    public void setCreditAcc(Account creditAcc) {
        this.creditAcc = creditAcc;
    }
}
