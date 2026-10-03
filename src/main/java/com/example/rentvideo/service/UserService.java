package com.example.rentvideo.service;

import com.example.rentvideo.dto.UserRegistrationRequest;
import com.example.rentvideo.dto.UserResponse;
import com.example.rentvideo.entity.Role;
import com.example.rentvideo.entity.User;
import com.example.rentvideo.exception.DuplicateResourceException;
import com.example.rentvideo.exception.ResourceNotFoundException;
import com.example.rentvideo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(UserRegistrationRequest request) {
        String email = request.getEmail() == null ? "" : request.getEmail().trim();
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("User with email '" + email + "' already exists");
        }

        Role role = request.getRole() == null ? Role.CUSTOMER : request.getRole();

        User user = new User(
                email,
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName().trim(),
                request.getLastName().trim(),
                role
        );

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToResponse(user);
    }

    public static UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
