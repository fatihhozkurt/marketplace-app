package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.AddressApi;
import com.fatih.marketplace_app.dto.request.address.CreateAddressRequest;
import com.fatih.marketplace_app.dto.request.address.UpdateAddressRequest;
import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import com.fatih.marketplace_app.entity.AddressEntity;
import com.fatih.marketplace_app.manager.service.AddressService;
import com.fatih.marketplace_app.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
public class AddressController implements AddressApi {

    private final AddressService addressService;

    @Override
    public ResponseEntity<AddressResponse> createAddress(CreateAddressRequest createAddressRequest) {

        AddressEntity requestAddress = AddressMapper.INSTANCE.createAddressRequestToEntity(createAddressRequest);
        AddressEntity createdAddress = addressService.createAddress(requestAddress);
        AddressResponse addressResponse = AddressMapper.INSTANCE.toAddressResponse(createdAddress);

        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AddressResponse> getAddressById(UUID addressId) {

        AddressEntity foundAddress = addressService.getAddressById(addressId);
        AddressResponse addressResponse = AddressMapper.INSTANCE.toAddressResponse(foundAddress);

        return new ResponseEntity<>(addressResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<AddressResponse>>>> getAllAddresses(Pageable pageable) {
        Page<AddressEntity> addressEntities = addressService.getAllAddresses(pageable);
        List<AddressResponse> addressResponses = AddressMapper.INSTANCE.toAddressResponseList(addressEntities.getContent());
        Map<UUID, List<AddressResponse>> addressMap = addressResponses.stream().collect(Collectors.groupingBy(AddressResponse::addressId));

        return new ResponseEntity<>(new PageImpl<>(List.of(addressMap), pageable, addressEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAddress(UUID addressId) {

        addressService.deleteAddress(addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AddressResponse> updateAddress(UpdateAddressRequest updateAddressRequest) {

        AddressEntity requestedAddress = AddressMapper.INSTANCE.updateAddressRequestToEntity(updateAddressRequest);
        AddressEntity updatedAddress = addressService.updateAddress(requestedAddress);
        AddressResponse addressResponse = AddressMapper.INSTANCE.toAddressResponse(updatedAddress);

        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }
}
