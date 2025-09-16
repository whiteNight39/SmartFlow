package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.request.StaffCreateRequest;
import com.whitenight.smartflow.model.request.StaffUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaffRequestMapper {

    Staff toEntity(StaffCreateRequest staffCreateRequest);
    Staff toEntity(StaffUpdateRequest staffUpdateRequest);
}
