package com.VURVhealth.vurvhealth.upgrade.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 18/7/17.
 */

public class SubPackageDetailReqPayload {

    @SerializedName("sub_package_id")
    @Expose
    private Integer subPackageId;

    public Integer getSubPackageId() {
        return subPackageId;
    }

    public void setSubPackageId(Integer subPackageId) {
        this.subPackageId = subPackageId;
    }
}
