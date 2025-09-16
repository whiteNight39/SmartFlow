package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.RFX;
import com.whitenight.smartflow.model.response.RFXResponse;

import java.util.UUID;

public interface RFXRepository {

    void createRFX(RFX rfx);
    RFXResponse getRFXById(UUID rfxId);
    void updateRFX(RFX rfx);
    void deleteRFX(UUID rfxId);
}
