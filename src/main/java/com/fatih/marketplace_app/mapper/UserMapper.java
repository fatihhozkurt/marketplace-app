package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.user.CreateUserRequest;
import com.fatih.marketplace_app.dto.request.user.UpdateUserRequest;
import com.fatih.marketplace_app.dto.response.user.UserResponse;
import com.fatih.marketplace_app.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity createUserRequestToEntity(CreateUserRequest createUserRequest);

    UserResponse toUserResponse(UserEntity userEntity);

    List<UserResponse> toUserResponseList(List<UserEntity> content);

    @Mapping(target = "id", source = "userId")
    UserEntity updateUserRequestToEntity(UpdateUserRequest updateUserRequest);
}
