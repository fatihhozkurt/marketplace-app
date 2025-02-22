package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.user.CreateUserRequest;
import com.fatih.marketplace_app.dto.request.user.UpdateUserRequest;
import com.fatih.marketplace_app.dto.response.user.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;


@RequestMapping(USER)
public interface UserApi {

    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<UserResponse>>>> getAllUsers(Pageable pageable);

    @GetMapping(ID)
    ResponseEntity<UserResponse> getUserById(@RequestParam("userId") @NotNull UUID userId);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteUser(@RequestParam("userId") @NotNull UUID userId);

    @PutMapping
    ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest);
}