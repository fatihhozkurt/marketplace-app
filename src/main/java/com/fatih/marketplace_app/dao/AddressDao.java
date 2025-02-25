package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.AddressEntity;
import com.fatih.marketplace_app.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link AddressEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class AddressDao {

    private final AddressRepository addressRepository;

    /**
     * Saves the given address entity to the database.
     *
     * @param requestAddress the address entity to save
     * @return the saved {@link AddressEntity}
     */
    public AddressEntity save(AddressEntity requestAddress) {
        return addressRepository.save(requestAddress);
    }

    /**
     * Finds an address by its unique identifier.
     *
     * @param addressId the UUID of the address
     * @return an {@link Optional} containing the found address, or empty if not found
     */
    public Optional<AddressEntity> findById(UUID addressId) {
        return addressRepository.findById(addressId);
    }

    /**
     * Retrieves a paginated list of all addresses.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link AddressEntity} objects
     */
    public Page<AddressEntity> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    /**
     * Deletes the given address entity from the database.
     *
     * @param foundAddress the address entity to delete
     */
    public void delete(AddressEntity foundAddress) {
        addressRepository.delete(foundAddress);
    }
}
