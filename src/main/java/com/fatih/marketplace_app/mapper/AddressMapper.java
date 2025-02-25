package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.address.CreateAddressRequest;
import com.fatih.marketplace_app.dto.request.address.UpdateAddressRequest;
import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import com.fatih.marketplace_app.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting address-related DTOs to entities and vice versa.
 */
@Mapper
public interface AddressMapper {

    /**
     * Singleton instance of the AddressMapper.
     */
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    /**
     * Converts a {@link CreateAddressRequest} to an {@link AddressEntity}.
     *
     * @param createAddressRequest The request DTO containing address creation details.
     * @return The mapped {@link AddressEntity}.
     */
    AddressEntity createAddressRequestToEntity(CreateAddressRequest createAddressRequest);

    /**
     * Converts an {@link UpdateAddressRequest} to an {@link AddressEntity}.
     * Maps the {@code addressId} field from {@link UpdateAddressRequest} to the {@code id} field in {@link AddressEntity}.
     *
     * @param updateAddressRequest The request DTO containing address update details.
     * @return The mapped {@link AddressEntity}.
     */
    @Mapping(target = "id", source = "addressId")
    AddressEntity updateAddressRequestToEntity(UpdateAddressRequest updateAddressRequest);

    /**
     * Converts an {@link AddressEntity} to an {@link AddressResponse}.
     * Maps the {@code id} field from {@link AddressEntity} to the {@code addressId} field in {@link AddressResponse}.
     *
     * @param addressEntity The address entity to be converted.
     * @return The mapped {@link AddressResponse}.
     */
    @Mapping(target = "addressId", source = "id")
    AddressResponse toAddressResponse(AddressEntity addressEntity);

    /**
     * Converts a list of {@link AddressEntity} objects to a list of {@link AddressResponse} objects.
     *
     * @param addressEntities The list of address entities to be converted.
     * @return The mapped list of {@link AddressResponse} objects.
     */
    List<AddressResponse> toAddressResponseList(List<AddressEntity> addressEntities);
}
