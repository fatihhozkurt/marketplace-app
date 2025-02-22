package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.WalletDao;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.entity.WalletEntity;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.manager.service.UserService;
import com.fatih.marketplace_app.manager.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletManager implements WalletService {

    private final WalletDao walletDao;
    private final UserService userService;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public WalletEntity createWallet(UUID userId) {

        UserEntity foundUser = userService.getUserById(userId);
        checkExistingWallet(foundUser);

        return walletDao.save(WalletEntity.builder().user(foundUser).build());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public WalletEntity getWalletById(UUID walletId) {
        return walletDao.findById(walletId).orElseThrow(() ->
                new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.WL002",
                        new Object[]{walletId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<WalletEntity> getAllWallets(Pageable pageable) {
        return walletDao.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkExistingWallet(UserEntity foundUser) {
        if (foundUser.getWallet() != null) {
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.WL002",
                            new Object[]{foundUser.getId()},
                            Locale.getDefault()));
        }
    }
}
