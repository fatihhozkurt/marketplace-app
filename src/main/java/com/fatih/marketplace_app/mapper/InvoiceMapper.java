package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.response.invoice.InvoiceResponse;
import com.fatih.marketplace_app.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting invoice-related entities to response DTOs.
 */
@Mapper(uses = {AddressMapper.class, OrderMapper.class, UserMapper.class, CartItemMapper.class, CartMapper.class})
public interface InvoiceMapper {

    /**
     * Singleton instance of the InvoiceMapper.
     */
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    /**
     * Converts an {@link InvoiceEntity} to an {@link InvoiceResponse}.
     *
     * @param invoice The invoice entity to be converted.
     * @return The mapped {@link InvoiceResponse}.
     */
    @Mapping(target = "invoiceId", source = "id")
    @Mapping(target = "invoiceDate", source = "createTime")
    @Mapping(target = "orderNumber", source = "order.orderNumber")
    @Mapping(target = "firstName", source = "order.user.firstName")
    @Mapping(target = "lastName", source = "order.user.lastName")
    @Mapping(target = "finalPrice", source = "order.finalPrice")
    @Mapping(target = "cartItemResponses", source = "order.cart.cartItem")
    @Mapping(target = "addressResponse", source = "order.address")
    InvoiceResponse toInvoiceResponse(InvoiceEntity invoice);

    /**
     * Converts a list of {@link InvoiceEntity} objects to a list of {@link InvoiceResponse} objects.
     *
     * @param invoices The list of invoice entities to be converted.
     * @return The mapped list of {@link InvoiceResponse} objects.
     */
    List<InvoiceResponse> toInvoiceResponseList(List<InvoiceEntity> invoices);
}