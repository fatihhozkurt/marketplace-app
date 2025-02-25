package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.user.CreateUserRequest;
import com.fatih.marketplace_app.dto.request.user.UpdateUserRequest;
import com.fatih.marketplace_app.dto.response.user.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

/**
 * API interface for managing users.
 */
@RequestMapping(USER)
public interface UserApi {

    /**
     * Creates a new user.
     *
     * @param createUserRequest The request body containing user details.
     * @return The created user response.
     */
    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest);

    /**
     * Retrieves all users with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of user responses grouped by user ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<UserResponse>>>> getAllUsers(Pageable pageable);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId The unique identifier of the user.
     * @return The user response.
     */
    @GetMapping(ID)
    ResponseEntity<UserResponse> getUserById(@RequestParam("userId") @NotNull UUID userId);

    /**
     * Deletes a user by their unique ID.
     *
     * @param userId The unique identifier of the user to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteUser(@RequestParam("userId") @NotNull UUID userId);

    /**
     * Updates an existing user.
     *
     * @param updateUserRequest The request body containing updated user details.
     * @return The updated user response.
     */
    @PutMapping
    ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user. Must be between 13 and 345 characters.
     * @return The user response.
     */
    @GetMapping(EMAIL)
    ResponseEntity<UserResponse> getUserByEmail(@RequestParam("email") @NotNull @NotBlank @Email @Size(min = 13, max = 345) String email);


    /**
     * Retrieves a user by their phone number.
     *
     * @param phone The phone number of the user. Must follow the Turkish phone number format.
     * @return The user response.
     */
    @GetMapping(PHONE)
    ResponseEntity<UserResponse> getUserByPhone(@RequestParam("phone") @NotNull @NotBlank @Pattern(regexp = "^(\\+90|0)?5\\d{9}$") String phone);
}