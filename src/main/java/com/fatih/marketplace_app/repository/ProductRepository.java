package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link ProductEntity} persistence operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
