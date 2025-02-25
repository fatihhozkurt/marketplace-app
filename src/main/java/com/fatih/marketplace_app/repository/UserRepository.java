package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link UserEntity} persistence operations.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Retrieves a user by their phone number.
     *
     * @param phone the phone number of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<UserEntity> findByPhone(String phone);
}
