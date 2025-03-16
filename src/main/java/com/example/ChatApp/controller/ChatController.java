package com.example.ChatApp.controller;

import com.example.ChatApp.exception.ChatException;
import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.Chat;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.request.GroupChatRequest;
import com.example.ChatApp.request.SingleChatRequest;
import com.example.ChatApp.response.ApiResponse;
import com.example.ChatApp.service.ChatService;
import com.example.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createChat(reqUser,singleChatRequest.getUserId());
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createGroup(groupChatRequest,reqUser);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable("chatId")Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException , ChatException {
        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findALLChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException , ChatException {
        User reqUser = userService.findUserProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        return new ResponseEntity<>(chats,HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/userId")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization")String jwt) throws UserException , ChatException{
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.addUserToGroup(userId,chatId,reqUser);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @PutMapping("/{chatId}/remove/userId")
    public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization")String jwt) throws UserException , ChatException{
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.removeFromGroup(userId,chatId,reqUser);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @DeleteMapping("/delete/userId")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization")String jwt) throws UserException , ChatException{
        User reqUser = userService.findUserProfile(jwt);
        chatService.deleteChat(chatId,reqUser.getId());
        ApiResponse res = new ApiResponse("chat is deleted successfully",true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
