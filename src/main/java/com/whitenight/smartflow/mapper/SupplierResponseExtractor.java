package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.response.SupplierResponse;
import com.whitenight.smartflow.model.response.SupplierStaffResponse;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SupplierResponseExtractor implements ResultSetExtractor<SupplierResponse> {

    @Override
    public SupplierResponse extractData(ResultSet rs) throws SQLException {
        SupplierResponse supplierResponse = null;
        List<SupplierStaffResponse> staffList = new ArrayList<>();

        while (rs.next()) {
            if (supplierResponse == null) {
                supplierResponse = SupplierResponse.builder()
                        .supplierCompanyId(UUID.fromString(rs.getString("supplier_company_id")))
                        .supplierName(rs.getString("supplier_name"))
                        .supplierEmail(rs.getString("supplier_email"))
                        .supplierPhone(rs.getString("supplier_phone"))
                        .supplierAddress(rs.getString("supplier_address"))
                        .supplierState(rs.getString("supplier_state"))
                        .supplierCountry(rs.getString("supplier_country"))

                        .supplierContactPersonFirstName(rs.getString("supplier_contact_person_first_name"))
                        .supplierContactPersonLastName(rs.getString("supplier_contact_person_last_name"))
                        .supplierContactPersonEmail(rs.getString("supplier_contact_person_email"))
                        .supplierContactPersonPhone(rs.getString("supplier_contact_person_phone"))

                        .supplierAddedBy(UUID.fromString(rs.getString("supplier_added_by")))

                        .supplierStaffList(staffList)
                        .build();
            }

            // If staff info is present in the row
            String staffFirstName = rs.getString("supplier_staff_first_name");
            if (staffFirstName != null) {
                SupplierStaffResponse staff = SupplierStaffResponse.builder()
                        .supplierStaffFirstName(staffFirstName)
                        .supplierStaffLastName(rs.getString("supplier_staff_last_name"))
                        .supplierStaffEmail(rs.getString("supplier_staff_email"))
                        .supplierStaffPhone(rs.getString("supplier_staff_phone"))
                        .supplierStaffRole(rs.getString("supplier_staff_role"))
                        .supplierStaffInvitedById(getUUIDOrNull(rs, "supplier_staff_invited_by_id"))
                        .supplierStaffInvitedByLabel(rs.getString("supplier_staff_invited_by_label"))
                        .build();

                staffList.add(staff);
            }
        }

        return supplierResponse;
    }

    private UUID getUUIDOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value != null ? UUID.fromString(value) : null;
    }
}
