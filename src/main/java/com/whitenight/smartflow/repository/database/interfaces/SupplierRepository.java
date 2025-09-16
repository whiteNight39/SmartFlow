package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.Supplier;
import com.whitenight.smartflow.model.response.SupplierResponse;

import java.util.UUID;

public interface SupplierRepository {

    void addSupplier(Supplier supplier);
    SupplierResponse getSupplierById(UUID supplierId);
    void updateSupplier(Supplier supplier);
    void deleteSupplierById(String supplierId);
}
