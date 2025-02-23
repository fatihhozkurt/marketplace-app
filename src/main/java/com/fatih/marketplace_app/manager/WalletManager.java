package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.WalletDao;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.entity.WalletEntity;
import com.fatih.marketplace_app.exception.BusinessException;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.UserService;
import com.fatih.marketplace_app.manager.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
                new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.WLT001",
                        new Object[]{walletId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<WalletEntity> getAllWallets(Pageable pageable) {
        return walletDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteWallet(UUID walletId) {
        WalletEntity foundWallet = getWalletById(walletId);
        walletDao.deleteWallet(foundWallet);
    }

    @Transactional
    @Override
    public WalletEntity loadBalance(UUID walletId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.valueOf(50)) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT004", new Object[]{}, Locale.getDefault())
            );
        }

        WalletEntity foundWallet = getWalletById(walletId);
        foundWallet.setBalance(foundWallet.getBalance().add(amount));

        return walletDao.save(foundWallet);
    }

    @Transactional
    @Override
    public WalletEntity payment(UUID walletId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT007", new Object[]{}, Locale.getDefault())
            );
        }

        WalletEntity foundWallet = getWalletById(walletId);

        if (foundWallet.getBalance().compareTo(amount) <= 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT003", new Object[]{}, Locale.getDefault())
            );
        }

        foundWallet.setBalance(foundWallet.getBalance().subtract(amount));
        return walletDao.save(foundWallet);
    }

    @Transactional
    @Override
    public WalletEntity changeBalance(UUID walletId, BigDecimal amount) {
        WalletEntity foundWallet = getWalletById(walletId);

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT006", new Object[]{}, Locale.getDefault())
            );
        }

        foundWallet.setBalance(amount);

        return walletDao.save(foundWallet);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public WalletEntity getWalletByUserId(UUID userId) {

        userService.getUserById(userId);
        return walletDao.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.WLT005",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkExistingWallet(UserEntity foundUser) {
        if (foundUser.getWallet() != null) {
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.WLT002",
                            new Object[]{foundUser.getId()},
                            Locale.getDefault()));
        }
    }
}
