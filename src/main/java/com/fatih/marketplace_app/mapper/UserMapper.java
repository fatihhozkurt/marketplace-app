package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.user.CreateUserRequest;
import com.fatih.marketplace_app.dto.request.user.UpdateUserRequest;
import com.fatih.marketplace_app.dto.response.user.UserResponse;
import com.fatih.marketplace_app.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting user-related entities to DTOs and vice versa.
 */
@Mapper
public interface UserMapper {

    /**
     * Singleton instance of the UserMapper.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts a {@link CreateUserRequest} DTO to a {@link UserEntity}.
     *
     * @param createUserRequest The request object containing user details.
     * @return The mapped {@link UserEntity}.
     */
    UserEntity createUserRequestToEntity(CreateUserRequest createUserRequest);

    /**
     * Converts a {@link UserEntity} to a {@link UserResponse}.
     *
     * @param userEntity The user entity to be converted.
     * @return The mapped {@link UserResponse}.
     */
    UserResponse toUserResponse(UserEntity userEntity);

    /**
     * Converts a list of {@link UserEntity} objects to a list of {@link UserResponse} objects.
     *
     * @param content The list of user entities to be converted.
     * @return The mapped list of {@link UserResponse} objects.
     */
    List<UserResponse> toUserResponseList(List<UserEntity> content);

    /**
     * Converts an {@link UpdateUserRequest} DTO to a {@link UserEntity}.
     * Maps the {@code userId} field to the {@code id} field in the entity.
     *
     * @param updateUserRequest The request object containing updated user details.
     * @return The mapped {@link UserEntity}.
     */
    @Mapping(target = "id", source = "userId")
    UserEntity updateUserRequestToEntity(UpdateUserRequest updateUserRequest);
}