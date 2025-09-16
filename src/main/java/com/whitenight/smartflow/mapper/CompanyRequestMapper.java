package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.request.CompanyCreateRequest;
import com.whitenight.smartflow.model.request.CompanyUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyRequestMapper {

    Company toEntity(CompanyCreateRequest request);
    Company toEntity(CompanyUpdateRequest request);
}
