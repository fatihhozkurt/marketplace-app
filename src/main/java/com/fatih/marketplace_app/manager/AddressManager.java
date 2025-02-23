package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.AddressDao;
import com.fatih.marketplace_app.entity.AddressEntity;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.manager.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressManager implements AddressService {

    private final AddressDao addressDao;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public AddressEntity createAddress(AddressEntity requestAddress) {
        return addressDao.save(requestAddress);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public AddressEntity getAddressById(UUID addressId) {
        return addressDao.findById(addressId)
                .orElseThrow(() ->
                        new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.ADR001",
                                new Object[]{addressId},
                                Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<AddressEntity> getAllAddresses(Pageable pageable) {

        return addressDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteAddress(UUID addressId) {

        AddressEntity foundAddress = getAddressById(addressId);
        addressDao.delete(foundAddress);
    }

    @Transactional
    @Override
    public AddressEntity updateAddress(AddressEntity requestedAddress) {
        AddressEntity foundAddress = getAddressById(requestedAddress.getId());
        AddressEntity updatedAddress = checkAddressConditions(foundAddress, requestedAddress);

        return addressDao.save(updatedAddress);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AddressEntity checkAddressConditions(AddressEntity foundAddress, AddressEntity requestedAddress) {

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

        return foundAddress;
    }


}
