package com.waveghost.users.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waveghost.users.api.dtos.request.UserRequest;
import com.waveghost.users.api.dtos.response.UserResponse;
import com.waveghost.users.domain.abstract_services.IUserService;
import com.waveghost.users.infrastructure.mappers.UserMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        return ResponseEntity.status(201).body(
            this.userMapper.ToDto(
                this.userService.create(
                    this.userMapper.ToUserEntity(request)
                )
            )
        );
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(
            this.userMapper.ToDto(
                this.userService.getAll()
            )
        );
    }
    
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(
            this.userMapper.ToDto(
                this.userService.getById(id)
            )
        );
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(
            this.userMapper.ToDto(
                this.userService.findByEmail(email)
            )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable String id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(
            this.userMapper.ToDto(
                this.userService.update(id, 
                    this.userMapper.ToUserEntity(request)
                )
            )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){

        this.userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email-exist")
    public ResponseEntity<Boolean> emailExist(@RequestParam String email) {
        return ResponseEntity.ok().body(
            this.userService.emailExist(email)
        );
    }
    
}
