package com.example.price_comparator_market.controller;

import com.example.price_comparator_market.entity.User;
import com.example.price_comparator_market.repository.UserRepository;
import com.example.price_comparator_market.service.dto.RegisterDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/getUser", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public RegisterDto registerUser(@RequestBody RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(
                registerDto.getUsername(),
                registerDto.getPassword(),
                registerDto.getEmail()
        );
        userRepository.save(user);
        return registerDto;
    }

    @RequestMapping(path = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}

