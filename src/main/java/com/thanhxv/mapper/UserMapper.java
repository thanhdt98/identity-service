package com.thanhxv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.thanhxv.dto.request.UserCreationRequest;
import com.thanhxv.dto.request.UserUpdateRequest;
import com.thanhxv.dto.response.UserResponse;
import com.thanhxv.entity.User;

/**
 * componentModel = "spring"
 * bao cho mapstruct biet generate mapper nay su dung trong spring
 * do do no se theo kieu dependency injection
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);

    /**
     * @MappingTarget bao map request vao user
     * @param user
     * @param request
     */
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    /**
     * @Mapping(source = "firstName", target = "lastName") khi cac field khong giong nhau co the chi dinh field nao vao field nao
     * @Mapping(target = "lastName", ignore = true) khi muon ignore 1 field nao khong muon map
     * @param user
     * @return
     */
    //    @Mapping(source = "firstName", target = "lastName")
    //    @Mapping(target = "lastName", ignore = true)
    UserResponse toUserResponse(User user);
}
