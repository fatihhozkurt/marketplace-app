package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.AddressApi;
import com.fatih.marketplace_app.dto.request.address.CreateAddressRequest;
import com.fatih.marketplace_app.dto.request.address.UpdateAddressRequest;
import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import com.fatih.marketplace_app.entity.AddressEntity;
import com.fatih.marketplace_app.manager.service.AddressService;
import com.fatih.marketplace_app.mapper.AddressMapper;
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
 * Controller implementation for managing addresses.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AddressController implements AddressApi {

    private final AddressService addressService;

    /**
     * Creates a new address.
     *
     * @param createAddressRequest The request body containing address details.
     * @return The created address response.
     */
    @Override
    public ResponseEntity<AddressResponse> createAddress(CreateAddressRequest createAddressRequest) {

        log.info("Creating new address: {}", createAddressRequest);
        AddressEntity requestAddress = AddressMapper.INSTANCE.createAddressRequestToEntity(createAddressRequest);
        AddressEntity createdAddress = addressService.createAddress(requestAddress);
        AddressResponse addressResponse = AddressMapper.INSTANCE.toAddressResponse(createdAddress);
        log.info("Address created successfully with ID: {}", addressResponse.addressId());

        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves an address by its unique ID.
     *
     * @param addressId The unique identifier of the address.
     * @return The address response.
     */
    @Override
    public ResponseEntity<AddressResponse> getAddressById(UUID addressId) {

        log.info("Fetching address with ID: {}", addressId);
        AddressEntity foundAddress = addressService.getAddressById(addressId);
        AddressResponse addressResponse = AddressMapper.INSTANCE.toAddressResponse(foundAddress);
        log.info("Address found: {}", addressResponse);

        return new ResponseEntity<>(addressResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves all addresses with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of address responses grouped by address ID.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<AddressResponse>>>> getAllAddresses(Pageable pageable) {

        log.info("Fetching all addresses with pagination: {}", pageable);
        Page<AddressEntity> addressEntities = addressService.getAllAddresses(pageable);
        List<AddressResponse> addressResponses = AddressMapper.INSTANCE.toAddressResponseList(addressEntities.getContent());
        Map<UUID, List<AddressResponse>> addressMap = addressResponses.stream().collect(Collectors.groupingBy(AddressResponse::addressId));
        log.info("Fetched {} addresses.", addressResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(addressMap), pageable, addressEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Deletes an address by its unique ID.
     *
     * @param addressId The unique identifier of the address to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteAddress(UUID addressId) {

        log.warn("Deleting address with ID: {}", addressId);
        addressService.deleteAddress(addressId);
        log.warn("Address deleted successfully: {}", addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Updates an existing address.
     *
     * @param updateAddressRequest The request body containing updated address details.
     * @return The updated address response.
     */
    @Override
    public ResponseEntity<AddressResponse> updateAddress(UpdateAddressRequest updateAddressRequest) {

        log.info("Updating address: {}", updateAddressRequest);
        AddressEntity requestedAddress = AddressMapper.INSTANCE.updateAddressRequestToEntity(updateAddressRequest);
        AddressEntity updatedAddress = addressService.updateAddress(requestedAddress);
        AddressResponse addressResponse = AddressMapper.INSTANCE.toAddressResponse(updatedAddress);
        log.info("Address updated successfully: {}", addressResponse);

        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }
}
