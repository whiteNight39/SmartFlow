package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.model.entity.RFXItem;
import com.whitenight.smartflow.model.response.RFXItemResponse;
import com.whitenight.smartflow.repository.database.interfaces.RFXItemRepository;
import com.whitenight.smartflow.repository.database.query.RFXItemQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RFXItemRepositoryImpl implements RFXItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RFXItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addRFXItem(RFXItem rfxItem) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxItemRfxId", rfxItem.getRfxItemRFXId())
                .addValue("rfxItemName", rfxItem.getRfxItemName())
                .addValue("rfxItemDescription", rfxItem.getRfxItemDescription())
                .addValue("rfxItemUnit", rfxItem.getRfxItemUnit())
                .addValue("rfxItemQuantity", rfxItem.getRfxItemQuantity())
                .addValue("rfxItemUnitPriceEstimate", rfxItem.getRfxItemUnitPriceEstimate())
                .addValue("rfxItemTotalEstimate", rfxItem.getRfxItemTotalEstimate());

        jdbcTemplate.update(RFXItemQuery.ADD_RFX_ITEM, params);
    }

    @Override
    public RFXItemResponse getRFXItem(UUID rfxItemId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxItemId", rfxItemId);

        return jdbcTemplate.queryForObject(
                RFXItemQuery.GET_RFX_ITEM_BY_ID,
                params,
                (rs, rowNum) -> RFXItemResponse.builder()
                        .rfxItemName(rs.getString("rfx_item_name"))
                        .rfxItemDescription(rs.getString("rfx_item_description"))
                        .rfxItemUnit(rs.getString("rfx_item_unit"))
                        .rfxItemQuantity(rs.getInt("rfx_item_quantity"))
                        .rfxItemUnitPriceEstimate(rs.getDouble("rfx_item_unit_price_estimate"))
                        .rfxItemTotalEstimate(rs.getDouble("rfx_item_total_estimate"))
                        .build()
        );
    }

    @Override
    public void updateRFXItem(RFXItem rfxItem) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxItemId", rfxItem.getRfxItemId())
                .addValue("rfxItemName", rfxItem.getRfxItemName())
                .addValue("rfxItemDescription", rfxItem.getRfxItemDescription())
                .addValue("rfxItemUnit", rfxItem.getRfxItemUnit())
                .addValue("rfxItemQuantity", rfxItem.getRfxItemQuantity())
                .addValue("rfxItemUnitPriceEstimate", rfxItem.getRfxItemUnitPriceEstimate())
                .addValue("rfxItemTotalEstimate", rfxItem.getRfxItemTotalEstimate());

        jdbcTemplate.update(RFXItemQuery.UPDATE_RFX_ITEM, params);
    }


    @Override
    public void deleteRFXItem(UUID rfxItemId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rfxItemId", rfxItemId);

        jdbcTemplate.update(RFXItemQuery.DELETE_RFX_ITEM, params);
    }

}
