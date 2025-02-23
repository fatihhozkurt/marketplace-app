package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.address.CreateAddressRequest;
import com.fatih.marketplace_app.dto.request.address.UpdateAddressRequest;
import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import com.fatih.marketplace_app.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressEntity createAddressRequestToEntity(CreateAddressRequest createAddressRequest);

    @Mapping(target = "id", source = "addressId")
    AddressEntity updateAddressRequestToEntity(UpdateAddressRequest updateAddressRequest);

    @Mapping(target = "addressId", source = "id")
    AddressResponse toAddressResponse(AddressEntity addressEntity);

    List<AddressResponse> toAddressResponseList(List<AddressEntity> addressEntities);

}
