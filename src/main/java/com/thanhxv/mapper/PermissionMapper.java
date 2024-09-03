package com.thanhxv.mapper;

import org.mapstruct.Mapper;

import com.thanhxv.dto.request.PermissionRequest;
import com.thanhxv.dto.response.PermissionResponse;
import com.thanhxv.entity.Permission;

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
