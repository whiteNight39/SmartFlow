package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.RFXResponseExtractor;
import com.whitenight.smartflow.model.entity.RFX;
import com.whitenight.smartflow.model.response.RFXResponse;
import com.whitenight.smartflow.repository.database.interfaces.RFXRepository;
import com.whitenight.smartflow.repository.database.query.RFXQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RFXRepositoryImpl implements RFXRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RFXRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createRFX(RFX rfx) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxProjectId", rfx.getRfxProjectId())
                .addValue("rfxTitle", rfx.getRfxTitle())
                .addValue("rfxDescription", rfx.getRfxDescription())
                .addValue("rfxCurrency", rfx.getRfxCurrency())
                .addValue("rfxStatus", rfx.getRfxStatus())
                .addValue("rfxIssuedAt", rfx.getRfxIssuedAt())
                .addValue("rfxDeadline", rfx.getRfxDeadline())
                .addValue("rfxAwardedSupplierId", rfx.getRfxAwardedSupplierId())
                .addValue("rfxPaymentSplit", rfx.getRfxPaymentSplit());

        jdbcTemplate.update(RFXQuery.CREATE_RFX, params);
    }

    @Override
    public RFXResponse getRFXById(UUID rfxId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxProjectId", rfxId);

        return jdbcTemplate.query(RFXQuery.GET_RFX_BY_ID, params,
                new RFXResponseExtractor());
    }

    @Override
    public void updateRFX(RFX rfx) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxId", rfx.getRfxId())
                .addValue("rfxTitle", rfx.getRfxTitle())
                .addValue("rfxDescription", rfx.getRfxDescription())
                .addValue("rfxStatus", rfx.getRfxStatus())
                .addValue("rfxIssuedAt", rfx.getRfxIssuedAt());

        jdbcTemplate.update(RFXQuery.UPDATE_RFX, params);
    }

    @Override
    public void deleteRFX(UUID rfxId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxId", rfxId);

        jdbcTemplate.update(RFXQuery.DELETE_RFX, params);
    }
}
