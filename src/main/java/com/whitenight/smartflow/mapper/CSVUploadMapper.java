package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.CSVUpload;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Component
public class CSVUploadMapper implements RowMapper<CSVUpload> {

    @Override
    public CSVUpload mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CSVUpload.builder()
                .csvUploadId(UUID.fromString(rs.getString("csv_upload_id")))
                .csvUploadUploadedBy(UUID.fromString(rs.getString("csv_upload_uploaded_by")))
                .csvUploadFileName(rs.getString("csv_upload_file_name"))
                .csvUploadNumEntries(rs.getInt("csv_upload_num_entries"))
                .csvUploadAssignedTo(UUID.fromString(rs.getString("csv_upload_assigned_to")))
                .csvUploadApproved(rs.getBoolean("csv_upload_approved"))
                .csvUploadApprovalNotes(rs.getString("csv_upload_approval_notes"))
                .csvUploadStatus(rs.getString("csv_upload_status"))
                .csvUploadCreatedAt(rs.getTimestamp("csv_upload_created_at").toLocalDateTime())
                .csvUploadFile(rs.getBytes("csv_upload_file")) // This gets the byte[] from DB
                .build();
    }
}
