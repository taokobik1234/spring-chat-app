package com.example.ChatApp.controller;

import com.example.ChatApp.exception.ChatException;
import com.example.ChatApp.exception.MessageException;
import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.Message;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.request.SendmessageRequest;
import com.example.ChatApp.response.ApiResponse;
import com.example.ChatApp.service.MessageService;
import com.example.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendmessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatsMessages(chatId,user);
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException {
        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId,user);

        ApiResponse res = new ApiResponse("messages deleted successfully",false);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }
}
