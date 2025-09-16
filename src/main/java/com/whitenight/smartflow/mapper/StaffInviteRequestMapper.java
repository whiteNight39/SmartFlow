package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.model.request.StaffInviteCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaffInviteRequestMapper {

    StaffInvite toEntity(StaffInviteCreateRequest staffInviteCreateRequest);
}
