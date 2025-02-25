package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.response.wallet.WalletResponse;
import com.fatih.marketplace_app.entity.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting wallet-related entities to DTOs and vice versa.
 */
@Mapper
public interface WalletMapper {

    /**
     * Singleton instance of the WalletMapper.
     */
    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    /**
     * Converts a {@link WalletEntity} to a {@link WalletResponse}.
     * Maps the entity's {@code id} to {@code walletId},
     * and user-related fields accordingly.
     *
     * @param walletEntity The wallet entity to be converted.
     * @return The mapped {@link WalletResponse}.
     */
    @Mapping(target = "walletId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    WalletResponse toWalletResponse(WalletEntity walletEntity);

    /**
     * Converts a list of {@link WalletEntity} objects to a list of {@link WalletResponse} objects.
     *
     * @param content The list of wallet entities to be converted.
     * @return The mapped list of {@link WalletResponse} objects.
     */
    List<WalletResponse> toWalletResponseList(List<WalletEntity> content);
}