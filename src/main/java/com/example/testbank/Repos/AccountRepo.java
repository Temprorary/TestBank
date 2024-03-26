package com.example.testbank.Repos;

import com.example.testbank.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
