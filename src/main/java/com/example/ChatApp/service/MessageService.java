package com.example.ChatApp.service;

import com.example.ChatApp.exception.ChatException;
import com.example.ChatApp.exception.MessageException;
import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.Message;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.request.SendmessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendmessageRequest req) throws UserException, ChatException;

    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException,UserException;

    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId,User reqUser) throws MessageException,UserException;

}
