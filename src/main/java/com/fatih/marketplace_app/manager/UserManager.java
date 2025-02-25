package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.UserDao;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

/**
 * Service class for managing users.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final MessageSource messageSource;
    private final UserDao userDao;

    /**
     * Creates a new user after validating email and phone number.
     *
     * @param requestedUser User entity to be created.
     * @return Created user entity.
     */
    @Transactional
    @Override
    public UserEntity createUser(UserEntity requestedUser) {
        log.info("Creating user with email: {}", requestedUser.getEmail());

        checkEmailAndPhone(requestedUser);
        return userDao.save(requestedUser);
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param pageable Pagination details.
     * @return Page of users.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable) {
        log.info("Fetching all users with pagination: {}", pageable);

        return userDao.findAll(pageable);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId User ID.
     * @return User entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity getUserById(UUID userId) {
        log.info("Fetching user by ID: {}", userId);

        return userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(messageSource
                .getMessage("backend.exceptions.US001",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId User ID.
     */
    @Transactional
    @Override
    public void deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);

        UserEntity foundUser = getUserById(userId);
        userDao.delete(foundUser);
    }

    /**
     * Updates an existing user.
     *
     * @param requestedUser Updated user entity.
     * @return Updated user entity.
     */
    @Transactional
    @Override
    public UserEntity updateUser(UserEntity requestedUser) {
        log.info("Updating user with ID: {}", requestedUser.getId());

        UserEntity foundUser = getUserById(requestedUser.getId());
        UserEntity updatedUser = checkUpdateConditions(foundUser, requestedUser);

        return userDao.save(updatedUser);
    }

    /**
     * Retrieves a user by email.
     *
     * @param email User email.
     * @return User entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);

        return userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.US002",
                                new Object[]{email},
                                Locale.getDefault())));
    }

    /**
     * Retrieves a user by phone number.
     *
     * @param phone User phone number.
     * @return User entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity getUserByPhone(String phone) {
        log.info("Fetching user by phone: {}", phone);

        return userDao.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.US003",
                                new Object[]{phone},
                                Locale.getDefault())));
    }

    /**
     * Checks and updates fields in an existing user entity.
     *
     * @param foundUser Existing user entity.
     * @param requestedUser Updated user entity.
     * @return Updated user entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserEntity checkUpdateConditions(UserEntity foundUser, UserEntity requestedUser) {
        log.info("Checking update conditions for user ID: {}", foundUser.getId());

        if (requestedUser.getFirstName() != null) {
            foundUser.setFirstName(requestedUser.getFirstName());
        }
        if (requestedUser.getLastName() != null) {
            foundUser.setLastName(requestedUser.getLastName());
        }
        if (requestedUser.getEmail() != null) {
            foundUser.setEmail(requestedUser.getEmail());
        }
        if (requestedUser.getPhone() != null) {
            foundUser.setPhone(requestedUser.getPhone());
        }
        if (requestedUser.getPassword() != null) {
            foundUser.setPassword(requestedUser.getPassword());
        }
        return foundUser;
    }

    /**
     * Checks if a user's email and phone number already exist in the system.
     *
     * @param requestedUser User entity to check.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkEmailAndPhone(UserEntity requestedUser) {
        log.info("Checking if email {} or phone {} already exist", requestedUser.getEmail(), requestedUser.getPhone());

        if (userDao.findByEmail(requestedUser.getEmail()).isPresent()) {
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.US004",
                            new Object[]{requestedUser.getEmail()},
                            Locale.getDefault()));
        }
        if (userDao.findByPhone(requestedUser.getPhone()).isPresent()) {
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.US005",
                            new Object[]{requestedUser.getPhone()},
                            Locale.getDefault()));
        }
    }
}
