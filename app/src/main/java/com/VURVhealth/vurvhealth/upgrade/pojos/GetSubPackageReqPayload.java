package com.VURVhealth.vurvhealth.upgrade.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 7/7/17.
 */

public class GetSubPackageReqPayload {

    @SerializedName("package_id")
    @Expose
    private Integer packageId;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }


}
