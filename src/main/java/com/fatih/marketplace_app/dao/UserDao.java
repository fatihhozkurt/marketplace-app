package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public UserEntity save(UserEntity requestedUser) {
        return userRepository.save(requestedUser);
    }

    public void delete(UserEntity foundUser) {

        userRepository.delete(foundUser);
    }

    public Optional<UserEntity> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public Page<UserEntity> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
