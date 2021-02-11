package com.acs.parking.service;

import com.acs.parking.dto.LoginCredentialsDto;
import com.acs.parking.exception.EntityAlreadyExistsException;
import com.acs.parking.exception.EntityNotFoundException;
import com.acs.parking.model.UserEntity;
import com.acs.parking.model.UserRole;
import com.acs.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserEntity createUser(UserEntity user) {
        userRepository.findByUsername(user.getUsername()).ifPresent ( param -> {
            String message = MessageFormat.format("A user with username {0} already exists", user.getUsername());
            throw new EntityAlreadyExistsException(message);
        });
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public UserEntity getById(Long userId) {
        String message = MessageFormat.format("User with id {0} not found", userId);
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(message));
    }

    public UserEntity getByUsername(String username) {
        String message = MessageFormat.format("User with username {0} not found", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(message));
    }

    public void activateAccount(String username) {
        UserEntity user = getByUsername(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public UserEntity resetPassword(LoginCredentialsDto loginCredentials) {
        UserEntity user = getByUsername(loginCredentials.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(loginCredentials.getPassword()));
        return userRepository.save(user);
    }

    public Iterable<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
