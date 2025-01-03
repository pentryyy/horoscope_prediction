package com.pentryyy.horoscope_prediction.service;

import com.pentryyy.horoscope_prediction.repository.UserRepository;
import com.pentryyy.horoscope_prediction.dto.UserUpdateRequest;
import com.pentryyy.horoscope_prediction.exception.EmailAlreadyExistsException;
import com.pentryyy.horoscope_prediction.exception.UserAlreadyDisabledException;
import com.pentryyy.horoscope_prediction.exception.UserAlreadyEnabledException;
import com.pentryyy.horoscope_prediction.exception.UserDoesNotExistException;
import com.pentryyy.horoscope_prediction.exception.UsernameAlreadyExistsException;
import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserDoesNotExistException(id));
    }

    public void disableUser(Long id) {
        User user = findById(id);

        if (!user.getIsEnabled()) {
            throw new UserAlreadyDisabledException();
        }

        user.setIsEnabled(false);
        userRepository.save(user);
    }

    public void enableUser(Long id) {
        User user = findById(id);

        if (user.getIsEnabled()) {
            throw new UserAlreadyEnabledException();
        }

        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public void changeRole(Long id, Role role){
        User user = findById(id);

        user.setRole(role);
        userRepository.save(user);
    }

    public void updateUser(
        Long id, 
        UserUpdateRequest request){
        
        User user = findById(id);

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setBirthDate(request.getBirthDate());
        userRepository.save(user);
    }

    public void changePassword(
        Long id, 
        String encryptedPassword){
        
        User user = findById(id);

        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public User saveNewUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public Page<User> getAllUsers(
        int page, 
        int limit,
        String sortBy, 
        String sortOrder) {
        
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        return userRepository.findAll(PageRequest.of(page, limit, sort));
    }
}