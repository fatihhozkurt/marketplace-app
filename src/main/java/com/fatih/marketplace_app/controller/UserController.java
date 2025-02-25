package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.UserApi;
import com.fatih.marketplace_app.dto.request.user.CreateUserRequest;
import com.fatih.marketplace_app.dto.request.user.UpdateUserRequest;
import com.fatih.marketplace_app.dto.response.user.UserResponse;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.manager.service.UserService;
import com.fatih.marketplace_app.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Controller for managing user-related operations.
 * Implements {@link UserApi} interface.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    /**
     * Creates a new user.
     *
     * @param createUserRequest The user creation request data.
     * @return ResponseEntity containing the created user details.
     */
    @Override
    public ResponseEntity<UserResponse> createUser(CreateUserRequest createUserRequest) {

        log.info("Creating a new user: {}", createUserRequest);
        UserEntity requestedUser = UserMapper.INSTANCE.createUserRequestToEntity(createUserRequest);
        UserEntity savedUser = userService.createUser(requestedUser);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(savedUser);
        log.info("User created successfully with ID: {}", savedUser.getId());

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param pageable Pagination information.
     * @return Paginated response containing users.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<UserResponse>>>> getAllUsers(Pageable pageable) {

        log.info("Fetching all users with pagination: {}", pageable);
        Page<UserEntity> userEntities = userService.getAllUsers(pageable);
        List<UserResponse> userResponses = UserMapper.INSTANCE.toUserResponseList(userEntities.getContent());
        Map<UUID, List<UserResponse>> userMap = userResponses.stream().collect(Collectors.groupingBy(UserResponse::id));
        log.info("Retrieved {} users", userResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(userMap), pageable, userEntities.getTotalElements()),
                HttpStatus.OK);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing user details.
     */
    @Override
    public ResponseEntity<UserResponse> getUserById(UUID userId) {

        log.info("Fetching user by ID: {}", userId);
        UserEntity userEntity = userService.getUserById(userId);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(userEntity);
        log.info("User found: {}", userResponse);

        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }


    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity with no content.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteUser(UUID userId) {

        log.warn("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        log.info("User deleted successfully: {}", userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Updates an existing user.
     *
     * @param updateUserRequest The user update request data.
     * @return ResponseEntity containing the updated user details.
     */
    @Override
    public ResponseEntity<UserResponse> updateUser(UpdateUserRequest updateUserRequest) {

        log.info("Updating user with request: {}", updateUserRequest);
        UserEntity userEntity = UserMapper.INSTANCE.updateUserRequestToEntity(updateUserRequest);
        UserEntity updatedUser = userService.updateUser(userEntity);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(updatedUser);
        log.info("User updated successfully: {}", userResponse);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return ResponseEntity containing user details.
     */
    @Override
    public ResponseEntity<UserResponse> getUserByEmail(String email) {

        log.info("Fetching user by email: {}", email);
        UserEntity foundUser = userService.getUserByEmail(email);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(foundUser);
        log.info("User found: {}", userResponse);

        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves a user by phone number.
     *
     * @param phone The phone number of the user to retrieve.
     * @return ResponseEntity containing user details.
     */
    @Override
    public ResponseEntity<UserResponse> getUserByPhone(String phone) {

        log.info("Fetching user by phone: {}", phone);
        UserEntity foundUser = userService.getUserByPhone(phone);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(foundUser);
        log.info("User found: {}", userResponse);

        return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
    }
}
