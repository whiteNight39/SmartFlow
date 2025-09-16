package com.whitenight.smartflow.utils.validator;

import com.whitenight.smartflow.model.entity.Company;
import jakarta.validation.ValidationException;

public class CompanyValidator {

    public static void validateCompany(Company company) {

        if (company == null) {
            throw new ValidationException("Company is not found");
        }
    }
}
