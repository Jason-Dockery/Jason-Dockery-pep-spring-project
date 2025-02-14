package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    private MessageService(MessageRepository messageRepository, 
                           AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    //##3
    public ResponseEntity<Message> addMessage (Message message){
        String username = userAccountId(message.getPostedBy());
        if(message.getMessageText().length() == 0 || 
            message.getMessageText().length() > 255 ||
            !accountRepository.existsByUsername(username)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Message createdMessage = messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        }
    }
    //##4
    public List<Message> getAllMessages (Message message){
        return messageRepository.findAll();
    }
    //##8
    public ResponseEntity<List<Message>> getAllMessagesByUser (Integer accountId){
        List<Message> allMessages = messageRepository.findByPostedBy(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(allMessages);
        
    }
    //##5
    public ResponseEntity<Message> getMessageById (Integer messageId){
        Optional <Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(messageOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    //##6
    public ResponseEntity<Object> updateMessageById (Integer messageId, Message newMessage){
        Message message = null;
        Optional <Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            message = messageOptional.get();
            if(newMessage.getMessageText() == "" || newMessage.getMessageText().length() > 255){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else {
                message.setMessageText(newMessage.getMessageText());
                messageRepository.save(message);
                return ResponseEntity.status(HttpStatus.OK).body("1");
            } 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    //##7
    public ResponseEntity<Object> deleteMessageById (Integer messageId){
        Optional <Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            messageRepository.deleteById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body("1");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    public String userAccountId (Integer accountId){
        Account account = accountRepository.findByAccountId(accountId);
        if(account == null){
            return null;
        } else {
            return account.getUsername();  
        }
    }
}
