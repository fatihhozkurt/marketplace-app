package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.response.invoice.InvoiceResponse;
import com.fatih.marketplace_app.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AddressMapper.class, OrderMapper.class, UserMapper.class, CartItemMapper.class, CartMapper.class})
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mapping(target = "invoiceId", source = "id")
    @Mapping(target = "invoiceDate", source = "createTime")
    @Mapping(target = "orderNumber", source = "order.orderNumber")
    @Mapping(target = "firstName", source = "order.user.firstName")
    @Mapping(target = "lastName", source = "order.user.lastName")
    @Mapping(target = "finalPrice", source = "order.finalPrice")
    @Mapping(target = "cartItemResponses", source = "order.cart.cartItem")
    @Mapping(target = "addressResponse", source = "order.address")
    InvoiceResponse toInvoiceResponse(InvoiceEntity invoice);

    List<InvoiceResponse> toInvoiceResponseList(List<InvoiceEntity> invoices);
}
