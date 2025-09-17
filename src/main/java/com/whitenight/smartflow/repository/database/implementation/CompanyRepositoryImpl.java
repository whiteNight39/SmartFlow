package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.CompanyMapper;
import com.whitenight.smartflow.mapper.StaffInviteMapper;
import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.repository.database.interfaces.CompanyRepository;
import com.whitenight.smartflow.repository.database.query.CompanyQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CompanyRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCompany(Company company) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyName", company.getCompanyName())
                .addValue("companyAddress", company.getCompanyAddress())
                .addValue("companyState", company.getCompanyState())
                .addValue("companyCountry", company.getCompanyCountry())
                .addValue("companyRegistrationNumber", company.getCompanyRegistrationNumber())
                .addValue("companyContactFirstName", company.getCompanyContactFirstName())
                .addValue("companyContactLastName", company.getCompanyContactLastName())
                .addValue("companyContactEmail", company.getCompanyContactEmail())
                .addValue("companyContactPhone", company.getCompanyContactPhone())
                .addValue("companyContactJobTitle", company.getCompanyContactJobTitle())
                .addValue("companyIndustry", company.getCompanyIndustry());

        jdbcTemplate.update(CompanyQuery.ADD_COMPANY, params);
    }

    @Override
    public Company getCompany(UUID company_id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyId", company_id);

        return jdbcTemplate.queryForObject(CompanyQuery.GET_COMPANY_BY_ID, params,
                new CompanyMapper());
    }

    @Override
    public Company getCompanyByRegistrationNumber(String companyRegNo) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyRegistrationNumber", companyRegNo);

        return jdbcTemplate.queryForObject(CompanyQuery.GET_COMPANY_BY_REGISTRATION_NUMBER, params,
                new CompanyMapper());
    }

    @Override
    public List<StaffInvite> getCompanyContactInviteDetails(String companyRegNo) {
        MapSqlParameterSource params =  new MapSqlParameterSource()
                .addValue("companyRegistrationNumber", companyRegNo);

        return jdbcTemplate.query(CompanyQuery.GET_COMPANY_CONTACT_INVITE_DETAILS, params,
                new StaffInviteMapper());
    }

    @Override
    public List<Company> getAllCompanies() {

        return jdbcTemplate.query(CompanyQuery.GET_ALL_COMPANIES,
                new CompanyMapper());
    }

    @Override
    public void updateCompany(Company company) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyId", company.getCompanyId())
                .addValue("companyName", company.getCompanyName())
                .addValue("companyAddress", company.getCompanyAddress())
                .addValue("companyState", company.getCompanyState())
                .addValue("companyCountry", company.getCompanyCountry())
                .addValue("companyRegistrationNumber", company.getCompanyRegistrationNumber())
                .addValue("companyContactFirstName", company.getCompanyContactFirstName())
                .addValue("companyContactLastName", company.getCompanyContactLastName())
                .addValue("companyContactEmail", company.getCompanyContactEmail())
                .addValue("companyContactPhone", company.getCompanyContactPhone())
                .addValue("companyContactJobTitle", company.getCompanyContactJobTitle())
                .addValue("companyIndustry", company.getCompanyIndustry())
                .addValue("companyStatus", company.getCompanyStatus());

        jdbcTemplate.update(CompanyQuery.UPDATE_COMPANY, params);
    }

    @Override
    public void unregisterCompany(UUID companyId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyId", companyId);

        jdbcTemplate.update(CompanyQuery.UNREGISTER_COMPANY, params);
    }
}