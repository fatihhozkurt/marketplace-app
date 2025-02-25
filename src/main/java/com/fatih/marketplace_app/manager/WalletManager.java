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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletManager implements WalletService {

    private final WalletDao walletDao;
    private final UserService userService;
    private final MessageSource messageSource;

    /**
     * Creates a new wallet for the given user.
     *
     * @param userId The UUID of the user.
     * @return The created WalletEntity.
     */
    @Transactional
    @Override
    public WalletEntity createWallet(UUID userId) {
        log.info("Creating wallet for user with ID: {}", userId);

        UserEntity foundUser = userService.getUserById(userId);
        checkExistingWallet(foundUser);

        return walletDao.save(WalletEntity.builder().user(foundUser).build());
    }

    /**
     * Retrieves a wallet by its ID.
     *
     * @param walletId The UUID of the wallet.
     * @return The WalletEntity if found.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public WalletEntity getWalletById(UUID walletId) {
        log.info("Fetching wallet with ID: {}", walletId);

        return walletDao.findById(walletId).orElseThrow(() ->
                new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.WLT001",
                        new Object[]{walletId},
                        Locale.getDefault())));
    }

    /**
     * Retrieves all wallets with pagination.
     *
     * @param pageable The pagination information.
     * @return A paginated list of WalletEntity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<WalletEntity> getAllWallets(Pageable pageable) {
        log.info("Fetching all wallets with pagination.");
        return walletDao.findAll(pageable);
    }

    /**
     * Deletes a wallet by its ID.
     *
     * @param walletId The UUID of the wallet to delete.
     */
    @Transactional
    @Override
    public void deleteWallet(UUID walletId) {
        log.info("Deleting wallet with ID: {}", walletId);

        WalletEntity foundWallet = getWalletById(walletId);
        walletDao.deleteWallet(foundWallet);
    }

    /**
     * Adds balance to a wallet.
     *
     * @param walletId The UUID of the wallet.
     * @param amount The amount to add.
     * @return The updated WalletEntity.
     */
    @Transactional
    @Override
    public WalletEntity loadBalance(UUID walletId, BigDecimal amount) {
        log.info("Adding balance of {} to wallet with ID: {}", amount, walletId);

        if (amount.compareTo(BigDecimal.valueOf(50)) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT004", new Object[]{}, Locale.getDefault())
            );
        }

        WalletEntity foundWallet = getWalletById(walletId);
        foundWallet.setBalance(foundWallet.getBalance().add(amount));

        return walletDao.save(foundWallet);
    }

    /**
     * Processes a payment from the wallet.
     *
     * @param walletId The UUID of the wallet.
     * @param amount The amount to be deducted.
     * @return The updated WalletEntity.
     */
    @Transactional
    @Override
    public WalletEntity payment(UUID walletId, BigDecimal amount) {
        log.info("Processing payment of {} from wallet with ID: {}", amount, walletId);

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT007", new Object[]{}, Locale.getDefault())
            );
        }

        WalletEntity foundWallet = getWalletById(walletId);

        if (foundWallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT003", new Object[]{walletId}, Locale.getDefault())
            );
        }

        foundWallet.setBalance(foundWallet.getBalance().subtract(amount));
        return walletDao.save(foundWallet);
    }

    /**
     * Changes the wallet balance to a specific amount.
     *
     * @param walletId The UUID of the wallet.
     * @param amount The new balance amount.
     * @return The updated WalletEntity.
     */
    @Transactional
    @Override
    public WalletEntity changeBalance(UUID walletId, BigDecimal amount) {
        log.info("Changing balance of wallet with ID: {} to {}", walletId, amount);

        WalletEntity foundWallet = getWalletById(walletId);

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT006", new Object[]{}, Locale.getDefault())
            );
        }

        foundWallet.setBalance(amount);

        return walletDao.save(foundWallet);
    }

    /**
     * Retrieves a wallet by user ID.
     *
     * @param userId The UUID of the user.
     * @return The WalletEntity associated with the user.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public WalletEntity getWalletByUserId(UUID userId) {
        log.info("Fetching wallet for user with ID: {}", userId);

        userService.getUserById(userId);
        return walletDao.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.WLT005",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    /**
     * Checks if a user already has a wallet.
     *
     * @param foundUser The UserEntity to check.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkExistingWallet(UserEntity foundUser) {
        log.info("Checking if user with ID: {} already has a wallet", foundUser.getId());

        if (foundUser.getWallet() != null) {
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.WLT002",
                            new Object[]{foundUser.getId()},
                            Locale.getDefault()));
        }
    }
}
