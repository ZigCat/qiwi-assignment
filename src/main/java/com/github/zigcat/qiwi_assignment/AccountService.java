package com.github.zigcat.qiwi_assignment;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {
    private final HashMap<String, Account> accounts = new HashMap<>();

    public void save(Account account){
        accounts.putIfAbsent(account.getNumber(), account);
    }

    public Account getById(String number){
        return accounts.get(number);
    }

    public List<Account> getAll(){
        return List.copyOf(accounts.values());
    }
}
