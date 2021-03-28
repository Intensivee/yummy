package com.orange.mainservice.controller;

import com.orange.mainservice.response.UserResponse;
import com.orange.mainservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getResponseById(id));
    }

    @GetMapping("search/getByUsername/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getResponseByUsername(username));
    }
}
