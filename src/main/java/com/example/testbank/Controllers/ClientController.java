package com.example.testbank.Controllers;

import com.example.testbank.Models.Account;
import com.example.testbank.Models.Client;
import com.example.testbank.Services.AccountService;
import com.example.testbank.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @RequestMapping("/list")
    @GetMapping
    public List<Client> getAllAccounts() {
        return clientService.findAll();
    }

    @RequestMapping("/list/export-xml")
    public ResponseEntity<byte[]> exportClientsXml() throws IOException {
        List<Client> clients = clientService.findAll();


        StringBuilder xml = new StringBuilder("<clients>");
        for (Client client : clients) {
            xml.append("<client>");
            xml.append("<id>" + client.getId() + "</id>");
            xml.append("<name>" + client.getName() + "</name>");

            // Добавление информации о счетах
            if (!client.getAccounts().isEmpty()) {
                xml.append("<accounts>");
                for (Account account : client.getAccounts()) {
                    xml.append("<account>");
                    xml.append("<id>" + account.getId() + "</id>");
                    xml.append("<number>" + account.getNumber() + "</number>");
                    xml.append("<balance>" + account.getBalance() + "</balance>");
                    xml.append("</account>");
                }
                xml.append("</accounts>");
            }

            xml.append("</client>");
        }
        xml.append("</clients>");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("clients.xml")
                .build());

        return new ResponseEntity<>(xml.toString().getBytes(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/import-xml", method = RequestMethod.POST)
    public List<Client> importClientsXml(@RequestBody String xml) {
        return parseXml(xml);
    }

    public List<Client> parseXml(String xml){

        String[] strs =xml.split("client");
        ArrayList<Client> clients = new ArrayList<>();
        for(int i=2; i<strs.length-2; i=i+2) {
            clients.add(clientService.save(parseClient(strs[i])));
        }

        return clients;

    }

    public Client parseClient(String xml){
        Client newClient = new Client();
        int start = xml.indexOf("<id>")+"<id>".length();
        int end = xml.indexOf("</id>");
        newClient.setId(Long.parseLong(xml.substring(start,end)));

        start = xml.indexOf("<name>")+"<name>".length();
        end = xml.indexOf("</name>");
        newClient.setName(xml.substring(start,end));

        System.out.println(newClient.getId());
        System.out.println(newClient.getName());

        String[] strs = xml.split("account");
        System.out.println(Arrays.toString(strs));
        newClient.setAccounts(new ArrayList<>());
        Account buffer;
        for(int i=2; i<strs.length-2; i=i+2)
        {
            buffer=parseAccount(strs[i]);
            buffer.setClient(newClient);
            newClient.addAccount(buffer);
        }

        return newClient;
    }

    private Account parseAccount(String xml) {
        Account newAccount = new Account();
        int start = xml.indexOf("<id>")+"<id>".length();
        int end = xml.indexOf("</id>");
        newAccount.setId(Long.parseLong(xml.substring(start,end)));

        start = xml.indexOf("<number>")+"<number>".length();
        end = xml.indexOf("</number>");
        newAccount.setNumber(xml.substring(start,end));

        start = xml.indexOf("<balance>")+"<balance>".length();
        end = xml.indexOf("</balance>");
        newAccount.setBalance(new BigDecimal(xml.substring(start,end)));
         return newAccount;

    }

}
