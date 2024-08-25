package com.thanhxv.mapper;

import com.thanhxv.dto.request.PermissionRequest;
import com.thanhxv.dto.request.UserCreationRequest;
import com.thanhxv.dto.request.UserUpdateRequest;
import com.thanhxv.dto.response.PermissionResponse;
import com.thanhxv.dto.response.UserResponse;
import com.thanhxv.entity.Permission;
import com.thanhxv.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * componentModel = "spring"
 * bao cho mapstruct biet generate mapper nay su dung trong spring
 * do do no se theo kieu dependency injection
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
