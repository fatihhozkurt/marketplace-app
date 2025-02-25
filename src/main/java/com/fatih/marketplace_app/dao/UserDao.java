package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link UserEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    /**
     * Saves the given user entity to the database.
     *
     * @param requestedUser the user entity to save
     * @return the saved {@link UserEntity}
     */
    public UserEntity save(UserEntity requestedUser) {
        return userRepository.save(requestedUser);
    }


    /**
     * Deletes the given user entity from the database.
     *
     * @param foundUser the user entity to delete
     */
    public void delete(UserEntity foundUser) {

        userRepository.delete(foundUser);
    }

    /**
     * Finds a user by their unique identifier.
     *
     * @param userId the UUID of the user
     * @return an {@link Optional} containing the found user, or empty if not found
     */
    public Optional<UserEntity> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link UserEntity} objects
     */
    public Page<UserEntity> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user
     * @return an {@link Optional} containing the found user, or empty if not found
     */
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Finds a user by their phone number.
     *
     * @param phone the phone number of the user
     * @return an {@link Optional} containing the found user, or empty if not found
     */
    public Optional<UserEntity> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
