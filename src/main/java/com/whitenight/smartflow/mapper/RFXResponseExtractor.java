package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.response.RFXItemResponse;
import com.whitenight.smartflow.model.response.RFXResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RFXResponseExtractor implements ResultSetExtractor<RFXResponse> {

    @Override
    public RFXResponse extractData(ResultSet rs) throws SQLException {
        RFXResponse rfxResponse = null;
        List<RFXItemResponse> itemList = new ArrayList<>();

        while (rs.next()) {
            if (rfxResponse == null) {
                rfxResponse = RFXResponse.builder()
                        .rfxProjectId(UUID.fromString(rs.getString("rfx_project_id")))
                        .rfxTitle(rs.getString("rfx_title"))
                        .rfxDescription(rs.getString("rfx_description"))
                        .rfxCurrency(rs.getString("rfx_currency"))
                        .rfxStatus(rs.getString("rfx_status"))
                        .rfxIssuedAt(rs.getTimestamp("rfx_issued_at").toLocalDateTime())
                        .rfxDeadline(rs.getTimestamp("rfx_deadline").toLocalDateTime())
                        .rfxAwardedSupplierId(getUUIDOrNull(rs, "rfx_awarded_supplier_id"))
                        .rfxPaymentSplit(rs.getString("rfx_payment_split"))
                        .rfxItemList(itemList)
                        .build();
            }

            String itemName = rs.getString("rfx_item_name");
            if (itemName != null) {
                RFXItemResponse item = RFXItemResponse.builder()
                        .rfxItemName(itemName)
                        .rfxItemDescription(rs.getString("rfx_item_description"))
                        .rfxItemUnit(rs.getString("rfx_item_unit"))
                        .rfxItemQuantity(rs.getInt("rfx_item_quantity"))
                        .rfxItemUnitPriceEstimate(rs.getDouble("rfx_item_unit_price_estimate"))
                        .rfxItemTotalEstimate(rs.getDouble("rfx_item_total_estimate"))
                        .build();

                itemList.add(item);
            }
        }

        return rfxResponse;
    }

    private UUID getUUIDOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value != null ? UUID.fromString(value) : null;
    }
}
