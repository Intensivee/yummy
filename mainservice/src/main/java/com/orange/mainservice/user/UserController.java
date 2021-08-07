package com.orange.mainservice.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("users")
@AllArgsConstructor
class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getResponseById(id));
    }

    @GetMapping("search/findByUsername/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getResponseByUsername(username));
    }

    @PatchMapping
    public ResponseEntity<UserResponse> patchUser(@RequestBody UserRequest userRequest, Principal principal) {
        return ResponseEntity.ok().body(userService.patchUser(principal.getName(), userRequest));
    }
}
