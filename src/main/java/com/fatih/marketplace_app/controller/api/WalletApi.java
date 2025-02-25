package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.wallet.ChangeBalanceRequest;
import com.fatih.marketplace_app.dto.request.wallet.CreateWalletRequest;
import com.fatih.marketplace_app.dto.request.wallet.LoadBalanceRequest;
import com.fatih.marketplace_app.dto.request.wallet.PaymentRequest;
import com.fatih.marketplace_app.dto.response.wallet.WalletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

/**
 * API interface for managing wallets.
 */
@RequestMapping(WALLET)
public interface WalletApi {

    /**
     * Creates a new wallet.
     *
     * @param createWalletRequest The request body containing wallet details.
     * @return The created wallet response.
     */
    @PostMapping
    ResponseEntity<WalletResponse> createWallet(@RequestBody @Valid CreateWalletRequest createWalletRequest);

    /**
     * Retrieves a wallet by its unique ID.
     *
     * @param walletId The unique identifier of the wallet.
     * @return The wallet response.
     */
    @GetMapping(ID)
    ResponseEntity<WalletResponse> getWalletById(@RequestParam("walletId") @NotNull UUID walletId);

    /**
     * Retrieves all wallets with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of wallet responses grouped by wallet ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<WalletResponse>>>> getAllWallets(Pageable pageable);

    /**
     * Loads balance into a wallet.
     *
     * @param loadBalanceRequest The request body containing balance load details.
     * @return The updated wallet response.
     */
    @PutMapping(LOAD)
    ResponseEntity<WalletResponse> loadBalance(@RequestBody @Valid LoadBalanceRequest loadBalanceRequest);

    /**
     * Processes a payment from a wallet.
     *
     * @param paymentRequest The request body containing payment details.
     * @return The updated wallet response.
     */
    @PutMapping(PAY)
    ResponseEntity<WalletResponse> payment(@RequestBody @Valid PaymentRequest paymentRequest);

    /**
     * Changes the balance of a wallet manually.
     *
     * @param changeBalanceRequest The request body containing balance change details.
     * @return The updated wallet response.
     */
    @PutMapping(CHANGE)
    ResponseEntity<WalletResponse> changeBalance(@RequestBody @Valid ChangeBalanceRequest changeBalanceRequest);

    /**
     * Deletes a wallet by its unique ID.
     *
     * @param walletId The unique identifier of the wallet to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteWallet(@RequestParam @NotNull UUID walletId);

    /**
     * Retrieves a wallet by user ID.
     *
     * @param userId The unique identifier of the user.
     * @return The wallet response.
     */
    @GetMapping(USER + ID)
    ResponseEntity<WalletResponse> getWalletByUserId(@RequestParam("userId") @NotNull UUID userId);
}