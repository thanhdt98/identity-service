package com.thanhxv.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.thanhxv.dto.request.ApiResponse;
import com.thanhxv.dto.request.PermissionRequest;
import com.thanhxv.dto.response.PermissionResponse;
import com.thanhxv.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PermissionController {
    PermissionService permissionService;

    @PostMapping()
    ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping()
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.findAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String name) {
        permissionService.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
