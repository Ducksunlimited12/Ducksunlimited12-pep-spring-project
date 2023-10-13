package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    

    @Autowired
    private AccountService accountService;

    public ResponseEntity<?> postMessage(Message message) {
        if (message.getMessage_text().length() > 0 &&
            message.getMessage_text().length() < 255 &&
            accountService.accountExistsById(message.getPosted_by())) {
              return new ResponseEntity<>(messageRepository.save(message), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

        
    }

    public ResponseEntity<List<Message>> getAllMessages() {
        return new ResponseEntity<>(messageRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getMessageByID(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            return new ResponseEntity<>(optionalMessage.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        }
        
        public Message deleteMessageByID(int id) {
            Optional<Message> msg = messageRepository.findById(id);
    
            if (msg.isPresent()) {
                Message nmsg = msg.get();
                messageRepository.deleteById(id);
                return nmsg;
            } 
                return null;
            
        }

        @Transactional
    public ResponseEntity<?> updateMessageByID(Integer messageId, Message updatedMessage) {
        Message existingMessage = messageRepository.findById(messageId).orElse(null);

        if (existingMessage == null) {
            return ResponseEntity.status(400).body("Message with ID " + messageId + " not found.");
        }

        String newMessageText = updatedMessage.getMessage_text();
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
            return ResponseEntity.status(400).body("Invalid message text. It should not be blank and should not exceed 255 characters.");
        }

        existingMessage.setMessage_text(newMessageText);

        try {
            messageRepository.save(existingMessage);
            return ResponseEntity.ok().body(1);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error updating the message.");
        }
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findMessagesByUser(accountId);
    }
            
       
}

    




    

