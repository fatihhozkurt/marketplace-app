package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link AddressEntity} persistence operations.
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
