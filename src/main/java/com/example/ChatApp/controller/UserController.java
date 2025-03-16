package com.example.ChatApp.controller;

import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.request.UpdateUserRequest;
import com.example.ChatApp.response.ApiResponse;
import com.example.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q){
        List<User> users = userService.searchUser(q);
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserProfileHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization")String token) throws  UserException{
        User user = userService.findUserProfile(token);
        userService.updateUser(user.getId(),req);
        ApiResponse res = new ApiResponse("user update succeesfully",true);
        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    }
}
