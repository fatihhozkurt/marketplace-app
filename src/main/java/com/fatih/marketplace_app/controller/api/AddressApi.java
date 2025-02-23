package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.address.CreateAddressRequest;
import com.fatih.marketplace_app.dto.request.address.UpdateAddressRequest;
import com.fatih.marketplace_app.dto.response.address.AddressResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

@RequestMapping(ADDRESS)
public interface AddressApi {

    @PostMapping
    ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid CreateAddressRequest createAddressRequest);

    @GetMapping(ID)
    ResponseEntity<AddressResponse> getAddressById(@RequestParam("addressId") @NotNull UUID addressId);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<AddressResponse>>>> getAllAddresses(Pageable pageable);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteAddress(@RequestParam("addressId") @NotNull UUID addressId);

    @PutMapping
    ResponseEntity<AddressResponse> updateAddress(@RequestBody @Valid UpdateAddressRequest updateAddressRequest);
}