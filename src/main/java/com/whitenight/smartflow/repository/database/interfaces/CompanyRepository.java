package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.entity.StaffInvite;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository {

    void addCompany(Company company);
    Company getCompany(UUID companyId);
    List<Company> getAllCompanies();
    Company getCompanyByRegistrationNumber(String companyRegNo);
    List<StaffInvite> getCompanyContactInviteDetails(String companyRegNo);
    void updateCompany(Company company);
    void unregisterCompany(UUID companyId);
}