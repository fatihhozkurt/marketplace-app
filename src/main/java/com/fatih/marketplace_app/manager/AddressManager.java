package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.AddressDao;
import com.fatih.marketplace_app.entity.AddressEntity;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.manager.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

/**
 * Manager implementation for managing addresses.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressManager implements AddressService {

    private final AddressDao addressDao;
    private final MessageSource messageSource;

    /**
     * Creates a new address.
     *
     * @param requestAddress The address entity to be created.
     * @return The created address entity.
     */
    @Transactional
    @Override
    public AddressEntity createAddress(AddressEntity requestAddress) {
        log.info("Creating a new address: {}", requestAddress);
        return addressDao.save(requestAddress);
    }

    /**
     * Retrieves an address by its ID.
     *
     * @param addressId The unique identifier of the address.
     * @return The found address entity.
     * @throws DataAlreadyExistException if the address does not exist.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public AddressEntity getAddressById(UUID addressId) {
        log.info("Fetching address with ID: {}", addressId);
        return addressDao.findById(addressId)
                .orElseThrow(() ->
                        new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.ADR001",
                                new Object[]{addressId},
                                Locale.getDefault())));
    }

    /**
     * Retrieves all addresses with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of addresses.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<AddressEntity> getAllAddresses(Pageable pageable) {

        log.info("Fetching all addresses with pagination: {}", pageable);
        return addressDao.findAll(pageable);
    }

    /**
     * Deletes an address by its ID.
     *
     * @param addressId The unique identifier of the address to be deleted.
     */
    @Transactional
    @Override
    public void deleteAddress(UUID addressId) {

        log.warn("Deleting address with ID: {}", addressId);
        AddressEntity foundAddress = getAddressById(addressId);
        addressDao.delete(foundAddress);
        log.info("Address with ID {} has been deleted successfully.", addressId);
    }

    /**
     * Updates an existing address.
     *
     * @param requestedAddress The address entity containing the updated information.
     * @return The updated address entity.
     */
    @Transactional
    @Override
    public AddressEntity updateAddress(AddressEntity requestedAddress) {

        log.info("Updating address with ID: {}", requestedAddress.getId());
        AddressEntity foundAddress = getAddressById(requestedAddress.getId());
        AddressEntity updatedAddress = checkAddressConditions(foundAddress, requestedAddress);

        return addressDao.save(updatedAddress);
    }

    /**
     * Checks and updates the fields of an address entity based on the requested changes.
     *
     * @param foundAddress     The existing address entity.
     * @param requestedAddress The new address data.
     * @return The modified address entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AddressEntity checkAddressConditions(AddressEntity foundAddress, AddressEntity requestedAddress) {

        log.info("Checking conditions for address update with ID: {}", foundAddress.getId());

        if (requestedAddress.getCountry() != null) {
            foundAddress.setCountry(requestedAddress.getCountry());
        }
        if (requestedAddress.getCity() != null) {
            foundAddress.setCity(requestedAddress.getCity());
        }
        if (requestedAddress.getDistrict() != null) {
            foundAddress.setDistrict(requestedAddress.getDistrict());
        }
        if (requestedAddress.getNeighbourhood() != null) {
            foundAddress.setNeighbourhood(requestedAddress.getNeighbourhood());
        }
        if (requestedAddress.getStreet() != null) {
            foundAddress.setStreet(requestedAddress.getStreet());
        }
        if (requestedAddress.getApartment() != null) {
            foundAddress.setApartment(requestedAddress.getApartment());
        }
        if (requestedAddress.getApartmentNumber() != null) {
            foundAddress.setApartmentNumber(requestedAddress.getApartmentNumber());
        }
        if (requestedAddress.getZipCode() != null) {
            foundAddress.setZipCode(requestedAddress.getZipCode());
        }

        log.info("Address with ID {} has been updated.", foundAddress.getId());

        return foundAddress;
    }
}
