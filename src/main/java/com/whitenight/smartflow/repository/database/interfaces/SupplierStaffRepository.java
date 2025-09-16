package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.SupplierStaff;
import com.whitenight.smartflow.model.response.SupplierStaffResponse;

import java.util.UUID;

public interface SupplierStaffRepository {

    void addSupplierStaff(SupplierStaff supplierStaff);
    SupplierStaff getSupplierStaff(UUID supplierStaffId);
    void updateSupplierStaff(SupplierStaff supplierStaff);
    void deleteSupplierStaff(UUID supplierStaffId);
}
