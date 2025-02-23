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

@RestController
@RequiredArgsConstructor
public class WalletController implements WalletApi {

    private final WalletService walletService;

    @Override
    public ResponseEntity<WalletResponse> createWallet(CreateWalletRequest createWalletRequest) {

        WalletEntity createdWallet = walletService.createWallet(createWalletRequest.userId());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(createdWallet);

        return new ResponseEntity<>(walletResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<WalletResponse> getWalletById(UUID walletId) {

        WalletEntity foundWallet = walletService.getWalletById(walletId);
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(foundWallet);

        return new ResponseEntity<>(walletResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<WalletResponse>>>> getAllWallets(Pageable pageable) {

        Page<WalletEntity> walletEntities = walletService.getAllWallets(pageable);
        List<WalletResponse> walletResponses = WalletMapper.INSTANCE.toWalletResponseList(walletEntities.getContent());
        Map<UUID, List<WalletResponse>> walletMap = walletResponses.stream().collect(Collectors.groupingBy(WalletResponse::walletId));

        return new ResponseEntity<>(new PageImpl<>(List.of(walletMap), pageable, walletEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WalletResponse> loadBalance(LoadBalanceRequest loadBalanceRequest) {

        WalletEntity updatedWallet = walletService.loadBalance(loadBalanceRequest.walletId(), loadBalanceRequest.amount());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(updatedWallet);

        return new ResponseEntity<>(walletResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WalletResponse> payment(PaymentRequest paymentRequest) {

        WalletEntity updatedWallet = walletService.payment(paymentRequest.walletId(), paymentRequest.amount());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(updatedWallet);

        return new ResponseEntity<>(walletResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<WalletResponse> changeBalance(ChangeBalanceRequest changeBalanceRequest) {

        WalletEntity updatedWallet = walletService.changeBalance(changeBalanceRequest.walletId(), changeBalanceRequest.amount());
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(updatedWallet);

        return new ResponseEntity<>(walletResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteWallet(UUID walletId) {

        walletService.deleteWallet(walletId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<WalletResponse> getWalletByUserId(UUID userId) {

        WalletEntity foundWallet = walletService.getWalletByUserId(userId);
        WalletResponse walletResponse = WalletMapper.INSTANCE.toWalletResponse(foundWallet);

        return new ResponseEntity<>(walletResponse, HttpStatus.FOUND);
    }
}
