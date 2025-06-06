package com.waveghost.auth.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.waveghost.auth.api.dtos.request.UserRequest;
import com.waveghost.auth.models.UserModel;

@FeignClient(name = "MSVC-USERS")
public interface UserClient {

    @PostMapping("/api/users/create")
    public ResponseEntity<UserModel> create(@RequestBody UserRequest request);

    @GetMapping("/api/users/get-by-email")
    public ResponseEntity<UserModel> getByEmail(@RequestParam String email);

    @GetMapping("/api/users/email-exist")
    public ResponseEntity<Boolean> emailExist(@RequestParam String email);
}
