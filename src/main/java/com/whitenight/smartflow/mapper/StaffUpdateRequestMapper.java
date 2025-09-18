package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.StaffUpdate;
import com.whitenight.smartflow.model.request.StaffUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaffUpdateRequestMapper {

    StaffUpdate toEntity(StaffUpdateRequest staffUpdateRequest);
}
