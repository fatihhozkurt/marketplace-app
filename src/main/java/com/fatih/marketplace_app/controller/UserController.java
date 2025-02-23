package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.UserApi;
import com.fatih.marketplace_app.dto.request.user.CreateUserRequest;
import com.fatih.marketplace_app.dto.request.user.UpdateUserRequest;
import com.fatih.marketplace_app.dto.response.user.UserResponse;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.manager.service.UserService;
import com.fatih.marketplace_app.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> createUser(CreateUserRequest createUserRequest) {

        UserEntity requestedUser = UserMapper.INSTANCE.createUserRequestToEntity(createUserRequest);
        UserEntity savedUser = userService.createUser(requestedUser);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(savedUser);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<UserResponse>>>> getAllUsers(Pageable pageable) {

        Page<UserEntity> userEntities = userService.getAllUsers(pageable);
        List<UserResponse> userResponses = UserMapper.INSTANCE.toUserResponseList(userEntities.getContent());
        Map<UUID, List<UserResponse>> userMap = userResponses.stream().collect(Collectors.groupingBy(UserResponse::id));

        return new ResponseEntity<>(new PageImpl<>(List.of(userMap), pageable, userEntities.getTotalElements()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(UUID userId) {

        UserEntity userEntity = userService.getUserById(userId);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(userEntity);

        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUser(UUID userId) {

        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UpdateUserRequest updateUserRequest) {

        UserEntity userEntity = UserMapper.INSTANCE.updateUserRequestToEntity(updateUserRequest);
        UserEntity updatedUser = userService.updateUser(userEntity);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(updatedUser);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> getUserByEmail(String email) {

        UserEntity foundUser = userService.getUserByEmail(email);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(foundUser);

        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<UserResponse> getUserByPhone(String phone) {

        UserEntity foundUser = userService.getUserByPhone(phone);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(foundUser);

        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }
}
