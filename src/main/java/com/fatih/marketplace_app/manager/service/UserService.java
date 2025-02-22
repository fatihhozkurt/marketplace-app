package com.fatih.marketplace_app.manager.service;


import com.fatih.marketplace_app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    Page<UserEntity> getAllUsers(Pageable pageable);

    UserEntity getUserById(UUID userId);

    void deleteUser(UUID userId);

    UserEntity updateUser(UserEntity userEntity);
}
