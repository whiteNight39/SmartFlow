package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.SupplierStaff;
import com.whitenight.smartflow.model.response.SupplierStaffResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class SupplierStaffMapper implements RowMapper<SupplierStaff> {

    @Override
    public SupplierStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
        return SupplierStaff.builder()
                .supplierStaffFirstName(rs.getString("supplier_staff_first_name"))
                .supplierStaffLastName(rs.getString("supplier_staff_last_name"))
                .supplierStaffEmail(rs.getString("supplier_staff_email"))
                .supplierStaffPhone(rs.getString("supplier_staff_phone"))
                .supplierStaffRole(rs.getString("supplier_staff_role"))
                .supplierStaffInvitedByStaffId(getUUIDOrNull(rs, "supplier_staff_invited_by_staff_id"))
                .supplierStaffInvitedBySupplierStaffId(getUUIDOrNull(rs, "supplier_staff_invited_by_supplier_staff_id"))
                .supplierStaffStatus(rs.getString("supplier_staff_status"))
                .supplierStaffCreatedAt(rs.getTimestamp("supplier_staff_created_at").toLocalDateTime())
                .supplierStaffUpdatedAt(rs.getTimestamp("supplier_staff_updated_at").toLocalDateTime())
                .build();
    }

    private UUID getUUIDOrNull(ResultSet rs, String column) throws SQLException {
        String value = rs.getString(column);
        return value != null ? UUID.fromString(value) : null;
    }
}
