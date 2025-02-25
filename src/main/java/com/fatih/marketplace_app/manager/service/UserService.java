package com.fatih.marketplace_app.manager.service;


import com.fatih.marketplace_app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing user operations.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param userEntity The user entity to be created.
     * @return The created user entity.
     */
    UserEntity createUser(UserEntity userEntity);

    /**
     * Retrieves all users with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of user entities.
     */
    Page<UserEntity> getAllUsers(Pageable pageable);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId The unique identifier of the user.
     * @return The user entity if found.
     */
    UserEntity getUserById(UUID userId);

    /**
     * Deletes a user by their unique ID.
     *
     * @param userId The unique identifier of the user to be deleted.
     */
    void deleteUser(UUID userId);

    /**
     * Updates an existing user.
     *
     * @param userEntity The updated user entity.
     * @return The updated user entity.
     */
    UserEntity updateUser(UserEntity userEntity);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user.
     * @return The user entity if found.
     */
    UserEntity getUserByEmail(String email);

    /**
     * Retrieves a user by their phone number.
     *
     * @param phone The phone number of the user.
     * @return The user entity if found.
     */
    UserEntity getUserByPhone(String phone);
}
