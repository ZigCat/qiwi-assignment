package com.github.zigcat.qiwi_assignment;

import com.github.zigcat.qiwi_assignment.requests.ChangeBalanceRequest;
import com.github.zigcat.qiwi_assignment.requests.GetBalanceRequest;
import com.github.zigcat.qiwi_assignment.requests.TransactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountService service;

    @PostMapping("/change")
    public ResponseEntity<Double> changeBalance(@RequestBody ChangeBalanceRequest request){
        Account account = service.getById(request.getNumber());
        if(account == null){
            account = new Account(request.getNumber(), 0.0);
        }
        account.setBalance(account.getBalance() + request.getValue());
        service.save(account);
        return ResponseEntity.ok(account.getBalance());
    }

    @PostMapping
    public ResponseEntity<?> getBalance(@RequestBody(required = false) GetBalanceRequest request){
        if(request != null){
            Account account = service.getById(request.getNumber());
            if(account == null){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(account);
            }
        } else {
            return ResponseEntity.ok(service.getAll());
        }
    }

    @PostMapping("/transact")
    public ResponseEntity<Account> transact(@RequestBody TransactRequest request){
        Account sender = service.getById(request.getSender());
        Account receiver = service.getById(request.getReceiver());
        if(sender != null && receiver != null){
            if(request.getValue() > 0.0){
                sender.setBalance(sender.getBalance() - request.getValue());
                receiver.setBalance(receiver.getBalance() + request.getValue());
                service.save(sender);
                service.save(receiver);
                return ResponseEntity.ok(sender);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
