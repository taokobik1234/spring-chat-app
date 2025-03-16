package com.example.ChatApp.service;

import com.example.ChatApp.config.TokenProvider;
import com.example.ChatApp.exception.UserException;
import com.example.ChatApp.modal.User;
import com.example.ChatApp.repository.UserRepository;
import com.example.ChatApp.request.UpdateUserRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;
    private TokenProvider tokenProvider;
    public UserServiceImplementation(UserRepository userRepository,TokenProvider tokenProvider){
        this.userRepository=userRepository;
        this.tokenProvider= tokenProvider;
    }
    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found with Id"+id);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException{
        String email = tokenProvider.getEmailFromToken(jwt);
        if(email == null){
            throw new BadCredentialsException("received invalid token ---");
        }
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not found with email"+ email);
        }
        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        User user = findUserById(userId);
        if(req.getFullName() !=null){
            user.setFullName(req.getFullName());
        }
        if(req.getProfilePicture()!=null){
            user.setProfile_picture(req.getProfilePicture());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return List.of();
    }
}
