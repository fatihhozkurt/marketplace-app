package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.wallet.CreateWalletRequest;
import com.fatih.marketplace_app.dto.response.wallet.WalletResponse;
import com.fatih.marketplace_app.entity.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(target = "walletId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    WalletResponse toWalletResponse(WalletEntity walletEntity);

    List<WalletResponse> toWalletResponseList(List<WalletEntity> content);

}
