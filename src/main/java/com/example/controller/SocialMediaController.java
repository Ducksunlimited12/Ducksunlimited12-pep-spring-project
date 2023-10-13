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

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private MessageService messageService;
    

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        return accountService.login(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> postMessage(@RequestBody Message message) {
        return messageService.postMessage(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessageByID(@PathVariable("message_id") Integer messageId) {
        return messageService.getMessageByID(messageId);
    }
    

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable int message_id) {
        Message msg = messageService.deleteMessageByID(message_id);

        if (msg != null) {
            return ResponseEntity.status(200).body(1);
        }
        
        return ResponseEntity.status(200).body(0);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessageByID(@PathVariable("message_id") Integer messageId, @RequestBody Message message) {
        return messageService.updateMessageByID(messageId, message);
    }
    
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable("account_id") Integer accountId) {
        List<Message> messages = messageService.getMessagesByUser(accountId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    


}



