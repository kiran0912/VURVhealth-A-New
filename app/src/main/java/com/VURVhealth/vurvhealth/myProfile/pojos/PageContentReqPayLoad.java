package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageContentReqPayLoad {
    @SerializedName("pageid")
    @Expose
    private Integer pageid;

    public Integer getPageid() {
        return this.pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }
}
