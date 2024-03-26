package com.example.testbank.Services;

import com.example.testbank.Models.Account;
import com.example.testbank.Models.Client;
import com.example.testbank.Repos.AccountRepo;
import com.example.testbank.Repos.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public Client save(Client client) {
        return clientRepo.save(client);
    }
}
