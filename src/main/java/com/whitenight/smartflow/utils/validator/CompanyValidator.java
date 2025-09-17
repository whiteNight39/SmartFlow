package com.whitenight.smartflow.utils.validator;

import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.utils.exception.ApiException;
import jakarta.validation.ValidationException;

public class CompanyValidator {

    public static void validateCompany(Company company) {

        if (company == null) {
            throw new ApiException("44", "Company not found", null);
        }
    }
}
