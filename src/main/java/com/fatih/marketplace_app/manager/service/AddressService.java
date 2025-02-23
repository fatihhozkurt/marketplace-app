package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AddressService {

    AddressEntity createAddress(AddressEntity requestAddress);

    AddressEntity getAddressById(UUID addressId);

    Page<AddressEntity> getAllAddresses(Pageable pageable);

    void deleteAddress(UUID addressId);

    AddressEntity updateAddress(AddressEntity requestedAddress);
}
