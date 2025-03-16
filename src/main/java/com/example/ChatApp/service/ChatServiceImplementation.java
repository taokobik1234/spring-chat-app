package com.example.ChatApp.service;

import com.example.ChatApp.exception.ChatException;
import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.Chat;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.repository.ChatRepository;
import com.example.ChatApp.request.GroupChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImplementation implements ChatService{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(user,reqUser);
        if(isChatExist != null){
            return isChatExist;
        }
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);
        return  chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found with Id: " +chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(user.getId());
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChat_image(req.getChat_image());
        group.setChat_name(req.getChat_name());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);
        for(Integer UserId: req.getUserIds()){
            User user = userService.findUserById(UserId);
            group.getUsers().add(user);
        }
        return chatRepository.save(group);
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not admin");
            }
        }
        throw new ChatException("Chat not found with Id "+ chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if(chat.getUsers().contains(reqUser)){
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("U are not member of this group");
        }
        throw new ChatException("Chat not found with Id "+ chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                if(user.getId().equals(reqUser.getId())){
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            }
            throw new UserException("You cant remove another user");
        }
        throw new ChatException("Chat not found with Id "+ chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            chatRepository.deleteById(chat.getId());
        }
    }
}
