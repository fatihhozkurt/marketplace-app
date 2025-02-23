package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.AddressEntity;
import com.fatih.marketplace_app.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddressDao {

    private final AddressRepository addressRepository;

    public AddressEntity save(AddressEntity requestAddress) {
        return addressRepository.save(requestAddress);
    }

    public Optional<AddressEntity> findById(UUID addressId) {
        return addressRepository.findById(addressId);
    }

    public Page<AddressEntity> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public void delete(AddressEntity foundAddress) {
        addressRepository.delete(foundAddress);
    }
}
