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

/**
 * API interface for managing addresses.
 */
@RequestMapping(ADDRESS)
public interface AddressApi {

    /**
     * Creates a new address.
     *
     * @param createAddressRequest Request body containing address details.
     * @return The created address response.
     */
    @PostMapping
    ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid CreateAddressRequest createAddressRequest);


    /**
     * Retrieves an address by its ID.
     *
     * @param addressId The unique identifier of the address.
     * @return The address response.
     */
    @GetMapping(ID)
    ResponseEntity<AddressResponse> getAddressById(@RequestParam("addressId") @NotNull UUID addressId);

    /**
     * Retrieves all addresses with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of address responses grouped by user ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<AddressResponse>>>> getAllAddresses(Pageable pageable);

    /**
     * Deletes an address by its ID.
     *
     * @param addressId The unique identifier of the address.
     * @return HTTP status indicating the result of the operation.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteAddress(@RequestParam("addressId") @NotNull UUID addressId);

    /**
     * Updates an existing address.
     *
     * @param updateAddressRequest Request body containing updated address details.
     * @return The updated address response.
     */
    @PutMapping
    ResponseEntity<AddressResponse> updateAddress(@RequestBody @Valid UpdateAddressRequest updateAddressRequest);
}