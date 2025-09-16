package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.enums.Status;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Company.builder()
                .companyId(UUID.fromString(rs.getString("company_id")))
                .companyName(rs.getString("company_name"))
                .companyAddress(rs.getString("company_address"))
                .companyState(rs.getString("company_state"))
                .companyCountry(rs.getString("company_country"))
                .companyRegistrationNumber(rs.getString("company_registration_number"))
                .companyContactFirstName(rs.getString("company_contact_first_name"))
                .companyContactLastName(rs.getString("company_contact_last_name"))
                .companyContactEmail(rs.getString("company_contact_email"))
                .companyContactPhone(rs.getString("company_contact_phone"))
                .companyContactJobTitle(rs.getString("company_contact_job_title"))
                .companyIndustry(rs.getString("company_industry"))
                .companyStatus(Status.valueOf(rs.getString("company_status")))
                .companyCreatedAt(rs.getTimestamp("company_created_at").toInstant())
                .companyUpdatedAt(rs.getTimestamp("company_updated_at").toInstant())
                .build();
    }
}
