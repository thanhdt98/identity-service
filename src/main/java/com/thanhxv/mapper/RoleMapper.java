package com.thanhxv.mapper;

import com.thanhxv.dto.request.RoleRequest;
import com.thanhxv.dto.response.RoleResponse;
import com.thanhxv.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * componentModel = "spring"
 * bao cho mapstruct biet generate mapper nay su dung trong spring
 * do do no se theo kieu dependency injection
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
