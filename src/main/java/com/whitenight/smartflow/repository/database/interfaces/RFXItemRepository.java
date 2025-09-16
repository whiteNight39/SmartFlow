package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.RFXItem;
import com.whitenight.smartflow.model.response.RFXItemResponse;

import java.util.UUID;

public interface RFXItemRepository {

    void addRFXItem(RFXItem rfxItem);
    RFXItemResponse getRFXItem(UUID rfxItemId);
    void updateRFXItem(RFXItem rfxItem);
    void deleteRFXItem(UUID rfxItemId);
}
