package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.CSVUploadMapper;
import com.whitenight.smartflow.model.entity.CSVUpload;
import com.whitenight.smartflow.repository.database.interfaces.CSVUploadRepository;
import com.whitenight.smartflow.repository.database.query.CSVUploadQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class CSVUploadRepositoryImpl implements CSVUploadRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CSVUploadRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void uploadStaffCSV(CSVUpload csvUpload) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("csvUploadUploadedBy", csvUpload.getCsvUploadUploadedBy())
                .addValue("csvUploadFileName", csvUpload.getCsvUploadFileName())
                .addValue("csvUploadNumEntries", csvUpload.getCsvUploadNumEntries())
                .addValue("csvUploadAssignedTo", csvUpload.getCsvUploadAssignedTo())
                .addValue("csvUploadApproved", csvUpload.getCsvUploadApproved())
                .addValue("csvUploadFile", csvUpload.getCsvUploadFile());

        jdbcTemplate.update(CSVUploadQuery.UPLOAD_STAFF_CSV, params);
    }

    @Override
    public void updateStaffCSV(CSVUpload csvUpload) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("csvUploadId", csvUpload.getCsvUploadId())
                .addValue("csvUploadUploadedBy", csvUpload.getCsvUploadUploadedBy())
                .addValue("csvUploadFileName", csvUpload.getCsvUploadFileName())
                .addValue("csvUploadNumEntries", csvUpload.getCsvUploadNumEntries())
                .addValue("csvUploadAssignedTo", csvUpload.getCsvUploadAssignedTo())
                .addValue("csvUploadApproved", csvUpload.getCsvUploadApproved())
                .addValue("csvUploadFile", csvUpload.getCsvUploadFile());

        jdbcTemplate.update(CSVUploadQuery.UPDATE_STAFF_CSV, params);
    }

    @Override
    public CSVUpload getCSVUploadById(UUID csvUploadId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("csvUploadId", csvUploadId);

        List<CSVUpload> csvUploads = jdbcTemplate.query(CSVUploadQuery.GET_CSV_UPLOAD_BY_ID, params,
                new CSVUploadMapper());

        return csvUploads.isEmpty() ? null : csvUploads.getFirst();
    }

    @Override
    public void deleteCSVUploadById(UUID csvUploadId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("csvUploadId", csvUploadId);

        jdbcTemplate.update(CSVUploadQuery.DELETE_CSV_UPLOAD_BY_ID, params);
    }
}
