package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.Project;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ProjectMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Project.builder()
                .projectId(UUID.fromString(rs.getString("project_id")))
                .projectCompanyId(UUID.fromString(rs.getString("project_company_id")))
                .projectType(rs.getString("project_type"))
                .projectCreatedById(UUID.fromString(rs.getString("project_created_by_id")))
                .projectSupplierId(getUUIDOrNull(rs, "project_supplier_id"))
                .projectAssignedSmId(getUUIDOrNull(rs, "project_assigned_sm_id"))
                .projectSmApproved(rs.getBoolean("project_sm_approved"))
                .projectSmApprovalNotes(rs.getString("project_sm_approval_notes"))
                .projectDeptHeadId(getUUIDOrNull(rs, "project_dept_head_id"))
                .projectFinalAuthorised(rs.getBoolean("project_final_authorised"))
                .projectFinalApprovalNotes(rs.getString("project_final_approval_notes"))
                .projectContractStartDate(rs.getTimestamp("project_contract_start_date").toLocalDateTime())
                .projectContractEndDate(rs.getTimestamp("project_contract_end_date").toLocalDateTime())
                .projectContractCurrency(rs.getString("project_contract_currency"))
                .projectContractTotalAmount(rs.getDouble("project_contract_total_amount"))
                .projectRfxCurrency(rs.getString("project_rfx_currency"))
                .projectRfxDeadline(rs.getTimestamp("project_rfx_deadline").toLocalDateTime())
                .projectRfxPaymentSplit(rs.getString("project_rfx_payment_split"))
                .projectStatus(rs.getString("project_status"))
                .projectCreatedAt(rs.getTimestamp("project_created_at").toLocalDateTime())
                .projectUpdatedAt(rs.getTimestamp("project_updated_at").toLocalDateTime())
                .build();
    }

    private UUID getUUIDOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value != null ? UUID.fromString(value) : null;
    }
}
