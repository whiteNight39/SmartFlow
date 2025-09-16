package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.ChangeRequestMapper;
import com.whitenight.smartflow.model.entity.ChangeRequest;
import com.whitenight.smartflow.repository.database.interfaces.ChangeRequestRepository;
import com.whitenight.smartflow.repository.database.query.ChangeRequestQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ChangeRequestRepositoryImpl implements ChangeRequestRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ChangeRequestRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createChangeRequest(ChangeRequest changeRequest) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("changeRequestRequestedByStaffId", changeRequest.getChangeRequestRequestedByStaffId())
                .addValue("changeRequestRequestedBySupplierStaffId", changeRequest.getChangeRequestRequestedBySupplierStaffId())
                .addValue("changeRequestHandledBy", changeRequest.getChangeRequestHandledBy())
                .addValue("changeRequestSupplierId", changeRequest.getChangeRequestSupplierId())
                .addValue("changeRequestOldData", changeRequest.getChangeRequestOldData())
                .addValue("changeRequestNewData", changeRequest.getChangeRequestNewData())
                .addValue("changeRequestReason", changeRequest.getChangeRequestReason())
                .addValue("changeRequestSmApproved", changeRequest.getChangeRequestSmApproved())
                .addValue("changeRequestSmApprovalNotes", changeRequest.getChangeRequestSmApprovalNotes());

        jdbcTemplate.update(ChangeRequestQuery.CREATE_CHANGE_REQUEST, params);
    }

    @Override
    public ChangeRequest getChangeRequest(UUID changeRequestId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("changeRequestId", changeRequestId);

        List<ChangeRequest> changeRequests = jdbcTemplate.query(ChangeRequestQuery.GET_CHANGE_REQUEST_BY_ID, params,
                new ChangeRequestMapper());

        return changeRequests.isEmpty() ? null : changeRequests.getFirst();
    }

    @Override
    public void updateChangeRequest(ChangeRequest changeRequest) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("changeRequestId", changeRequest.getChangeRequestId())
                .addValue("changeRequestHandledBy", changeRequest.getChangeRequestHandledBy())
                .addValue("changeRequestOldData", changeRequest.getChangeRequestOldData())
                .addValue("changeRequestNewData", changeRequest.getChangeRequestNewData())
                .addValue("changeRequestReason", changeRequest.getChangeRequestReason())
                .addValue("changeRequestSmApproved", changeRequest.getChangeRequestSmApproved())
                .addValue("changeRequestSmApprovalNotes", changeRequest.getChangeRequestSmApprovalNotes())
                .addValue("changeRequestStatus", changeRequest.getChangeRequestStatus())
                .addValue("changeRequestResolvedAt", changeRequest.getChangeRequestResolvedAt())
                .addValue("changeRequestSupplierId", changeRequest.getChangeRequestSupplierId());

        jdbcTemplate.update(ChangeRequestQuery.UPDATE_CHANGE_REQUEST, params);
    }

    @Override
    public void deleteChangeRequest(UUID changeRequestId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("changeRequestId", changeRequestId);

        jdbcTemplate.update(ChangeRequestQuery.DELETE_CHANGE_REQUEST, params);
    }
}
