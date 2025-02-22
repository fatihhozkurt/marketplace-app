package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.UserDao;
import com.fatih.marketplace_app.entity.UserEntity;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final MessageSource messageSource;
    private final UserDao userDao;

    @Transactional
    @Override
    public UserEntity createUser(UserEntity requestedUser) {
        return userDao.save(requestedUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable) {

        return userDao.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity getUserById(UUID userId) {


        return userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(messageSource
                .getMessage("backend.exceptions.US001",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    @Transactional
    @Override
    public void deleteUser(UUID userId) {

        UserEntity foundUser = getUserById(userId);
        userDao.delete(foundUser);
    }

    @Transactional
    @Override
    public UserEntity updateUser(UserEntity requestedUser) {

        UserEntity foundUser = getUserById(requestedUser.getId());
        UserEntity updatedUser = checkUpdateConditions(foundUser, requestedUser);

        return userDao.save(updatedUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserEntity checkUpdateConditions(UserEntity foundUser, UserEntity requestedUser) {

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
}
