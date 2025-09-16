package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.ChangeRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ChangeRequestMapper implements RowMapper<ChangeRequest> {

    @Override
    public ChangeRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ChangeRequest.builder()
                .changeRequestId(UUID.fromString(rs.getString("change_request_id")))
                .changeRequestRequestedByStaffId(getUUIDOrNull(rs, "change_request_requested_by_staff_id"))
                .changeRequestRequestedBySupplierStaffId(getUUIDOrNull(rs, "change_request_requested_by_supplier_staff_id"))
                .changeRequestHandledBy(getUUIDOrNull(rs, "change_request_handled_by"))
                .changeRequestSupplierId(getUUIDOrNull(rs, "change_request_supplier_id"))
                .changeRequestOldData((String[]) rs.getArray("change_request_old_data").getArray())
                .changeRequestNewData((String[]) rs.getArray("change_request_new_data").getArray())
                .changeRequestReason(rs.getString("change_request_reason"))
                .changeRequestStatus(rs.getString("change_request_status"))
                .changeRequestCreatedAt(rs.getTimestamp("change_request_created_at").toLocalDateTime())
                .changeRequestResolvedAt(rs.getTimestamp("change_request_resolved_at") != null
                        ? rs.getTimestamp("change_request_resolved_at").toLocalDateTime()
                        : null)
                .changeRequestSmApproved(rs.getBoolean("change_request_sm_approved"))
                .changeRequestSmApprovalNotes(rs.getString("change_request_sm_approval_notes"))
                .build();
    }

    private UUID getUUIDOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value != null ? UUID.fromString(value) : null;
    }
}
