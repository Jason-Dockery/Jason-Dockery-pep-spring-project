package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity <Account> postRegistration(@RequestBody Account account){
        return accountService.addUser(account);
    }

    @PostMapping("/login")
    public ResponseEntity <?> postLogin(@RequestBody Account account){
        ResponseEntity <Account> loginRequest = accountService.findAccount(account);
        return ResponseEntity.status(loginRequest.getStatusCode()).body(loginRequest.getBody());
    }

    @PostMapping("/messages")
    public ResponseEntity <Message> postMessages(@RequestBody Message message){
        return messageService.addMessage(message);
    }

    @GetMapping("/messages")
    public ResponseEntity <List<Message>> getMessages(Message message){
        List <Message> retrieveMessages = messageService.getAllMessages(message);
        return ResponseEntity.ok(retrieveMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity <Message> getMessagesById(@PathVariable("messageId") Integer messageId){
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity <?> deleteMessagesById(@PathVariable("messageId") Integer messageId){
        return messageService.deleteMessageById(messageId);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity <?> updateMessagesById(@PathVariable("messageId") Integer messageId, @RequestBody Message updatedMessage){
        return messageService.updateMessageById(messageId, updatedMessage);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity <List<Message>> getAllMessagesByUserId(@PathVariable("accountId") Integer accountId){
        return messageService.getAllMessagesByUser(accountId);
    }

}
