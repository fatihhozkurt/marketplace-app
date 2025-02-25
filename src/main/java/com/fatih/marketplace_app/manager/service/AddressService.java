package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing addresses.
 */
public interface AddressService {

    /**
     * Creates a new address.
     *
     * @param requestAddress The address entity to be created.
     * @return The created AddressEntity.
     */
    AddressEntity createAddress(AddressEntity requestAddress);

    /**
     * Retrieves an address by its unique ID.
     *
     * @param addressId The unique identifier of the address.
     * @return The found AddressEntity.
     */
    AddressEntity getAddressById(UUID addressId);

    /**
     * Retrieves all addresses with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of AddressEntity.
     */
    Page<AddressEntity> getAllAddresses(Pageable pageable);

    /**
     * Deletes an address by its unique ID.
     *
     * @param addressId The unique identifier of the address to be deleted.
     */
    void deleteAddress(UUID addressId);

    /**
     * Updates an existing address.
     *
     * @param requestedAddress The updated address entity.
     * @return The updated AddressEntity.
     */
    AddressEntity updateAddress(AddressEntity requestedAddress);
}
