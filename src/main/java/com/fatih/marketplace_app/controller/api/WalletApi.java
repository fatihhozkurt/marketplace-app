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

@RequestMapping(WALLET)
public interface WalletApi {

    @PostMapping
    ResponseEntity<WalletResponse> createWallet(@RequestBody @Valid CreateWalletRequest createWalletRequest);

    @GetMapping(ID)
    ResponseEntity<WalletResponse> getWalletById(@RequestParam("walletId") @NotNull UUID walletId);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<WalletResponse>>>> getAllWallets(Pageable pageable);

    @PutMapping(LOAD)
    ResponseEntity<WalletResponse> loadBalance(@RequestBody @Valid LoadBalanceRequest loadBalanceRequest);

    @PutMapping(PAY)
    ResponseEntity<WalletResponse> payment(@RequestBody @Valid PaymentRequest paymentRequest);

    @PutMapping(CHANGE)
    ResponseEntity<WalletResponse> changeBalance(@RequestBody @Valid ChangeBalanceRequest changeBalanceRequest);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteWallet(@RequestParam @NotNull UUID walletId);
}