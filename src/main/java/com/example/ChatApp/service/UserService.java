package com.example.ChatApp.service;

import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.request.UpdateUserRequest;

import java.util.List;

public interface UserService {
    public User findUserById(Integer id) throws UserException;

    public User findUserProfile(String jwt) throws UserException;

    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

    public List<User> searchUser(String query);
}
