package com.fatih.marketplace_app.controller;


import com.fatih.marketplace_app.controller.api.WalletApi;
import com.fatih.marketplace_app.dto.request.wallet.ChangeBalanceRequest;
import com.fatih.marketplace_app.dto.request.wallet.CreateWalletRequest;
import com.fatih.marketplace_app.dto.request.wallet.LoadBalanceRequest;
import com.fatih.marketplace_app.dto.request.wallet.PaymentRequest;
import com.fatih.marketplace_app.dto.response.wallet.WalletResponse;
import com.fatih.marketplace_app.entity.WalletEntity;
import com.fatih.marketplace_app.manager.service.WalletService;
import com.fatih.marketplace_app.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller for managing wallet-related operations.
 * Implements {@link WalletApi} interface.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class WalletController implements WalletApi {

    private final WalletService walletService;

    /**
     * Creates a new wallet for a user.
     *
     * @param createWalletRequest The request containing user ID.
     * @return ResponseEntity containing the created wallet details.
     */
    @Override
    public ResponseEntity<WalletResponse> createWallet(CreateWalletRequest createWalletRequest) {

        log.info("Creating a wallet for user ID: {}", createWalletRequest.userId());
        WalletEntity createdWallet = walletService.createWallet(createWalletRequest.userId());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(createdWallet);
        log.info("Wallet created successfully with ID: {}", createdWallet.getId());

        return new ResponseEntity<>(walletResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a wallet by its ID.
     *
     * @param walletId The ID of the wallet to retrieve.
     * @return ResponseEntity containing wallet details.
     */
    @Override
    public ResponseEntity<WalletResponse> getWalletById(UUID walletId) {

        log.info("Fetching wallet by ID: {}", walletId);
        WalletEntity foundWallet = walletService.getWalletById(walletId);
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(foundWallet);
        log.info("Wallet found: {}", walletResponse);

        return new ResponseEntity<>(walletResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves all wallets with pagination.
     *
     * @param pageable Pagination information.
     * @return Paginated response containing wallets.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<WalletResponse>>>> getAllWallets(Pageable pageable) {

        log.info("Fetching all wallets with pagination: {}", pageable);
        Page<WalletEntity> walletEntities = walletService.getAllWallets(pageable);
        List<WalletResponse> walletResponses = WalletMapper.INSTANCE.toWalletResponseList(walletEntities.getContent());
        Map<UUID, List<WalletResponse>> walletMap = walletResponses.stream().collect(Collectors.groupingBy(WalletResponse::walletId));
        log.info("Retrieved {} wallets", walletResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(walletMap), pageable, walletEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Loads balance into a wallet.
     *
     * @param loadBalanceRequest The request containing wallet ID and amount.
     * @return ResponseEntity containing updated wallet details.
     */
    @Override
    public ResponseEntity<WalletResponse> loadBalance(LoadBalanceRequest loadBalanceRequest) {

        log.info("Loading balance into wallet ID: {} with amount: {}", loadBalanceRequest.walletId(), loadBalanceRequest.amount());
        WalletEntity updatedWallet = walletService.loadBalance(loadBalanceRequest.walletId(), loadBalanceRequest.amount());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(updatedWallet);
        log.info("Wallet balance updated successfully: {}", walletResponse);

        return new ResponseEntity<>(walletResponse, HttpStatus.OK);
    }

    /**
     * Processes a payment from a wallet.
     *
     * @param paymentRequest The request containing wallet ID and payment amount.
     * @return ResponseEntity containing updated wallet details.
     */
    @Override
    public ResponseEntity<WalletResponse> payment(PaymentRequest paymentRequest) {

        log.info("Processing payment from wallet ID: {} with amount: {}", paymentRequest.walletId(), paymentRequest.amount());
        WalletEntity updatedWallet = walletService.payment(paymentRequest.walletId(), paymentRequest.amount());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(updatedWallet);
        log.info("Payment processed successfully: {}", walletResponse);

        return new ResponseEntity<>(walletResponse, HttpStatus.OK);

    }

    /**
     * Changes the balance of a wallet.
     *
     * @param changeBalanceRequest The request containing wallet ID and new balance.
     * @return ResponseEntity containing updated wallet details.
     */
    @Override
    public ResponseEntity<WalletResponse> changeBalance(ChangeBalanceRequest changeBalanceRequest) {

        log.info("Changing balance for wallet ID: {} with new amount: {}", changeBalanceRequest.walletId(), changeBalanceRequest.amount());
        WalletEntity updatedWallet = walletService.changeBalance(changeBalanceRequest.walletId(), changeBalanceRequest.amount());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(updatedWallet);
        log.info("Wallet balance changed successfully: {}", walletResponse);

        return new ResponseEntity<>(walletResponse, HttpStatus.OK);
    }

    /**
     * Deletes a wallet by ID.
     *
     * @param walletId The ID of the wallet to delete.
     * @return ResponseEntity with no content.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteWallet(UUID walletId) {

        log.warn("Deleting wallet with ID: {}", walletId);
        walletService.deleteWallet(walletId);
        log.info("Wallet deleted successfully: {}", walletId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a wallet by user ID.
     *
     * @param userId The ID of the user whose wallet is to be retrieved.
     * @return ResponseEntity containing wallet details.
     */
    @Override
    public ResponseEntity<WalletResponse> getWalletByUserId(UUID userId) {

        log.info("Fetching wallet for user ID: {}", userId);
        WalletEntity foundWallet = walletService.getWalletByUserId(userId);
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(foundWallet);
        log.info("Wallet found: {}", walletResponse);

        return new ResponseEntity<>(walletResponse, HttpStatus.FOUND);
    }
}
