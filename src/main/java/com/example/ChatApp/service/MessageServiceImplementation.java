package com.example.ChatApp.service;

import com.example.ChatApp.exception.ChatException;
import com.example.ChatApp.exception.MessageException;
import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.Chat;
import com.example.ChatApp.modal.Message;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.repository.MessageRepository;
import com.example.ChatApp.request.SendmessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImplementation implements MessageService{
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Message sendMessage(SendmessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());
        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        return null;
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId,User reqUser) throws ChatException,UserException {
        Chat chat = chatService.findChatById(chatId);
        if(!chat.getUsers().contains(reqUser)){
            throw new UserException("you are not related to this chat"+ chat.getId());
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new MessageException("Message not found with Id"+ messageId);
    }

    @Override
    public void deleteMessage(Integer messageId,User reqUser) throws MessageException,UserException {
        Message message = findMessageById(messageId);
        if(message.getUser().getId().equals(reqUser.getId())){
            messageRepository.deleteById(messageId);
        }
        else throw new UserException("You can not delete anothe user'message"+reqUser.getFullName());
    }
}
