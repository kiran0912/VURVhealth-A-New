package com.VURVhealth.vurvhealth.save.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveForLaterAllResponsePojo {
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("detail1")
    @Expose
    private String detail1;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail1() {
        return this.detail1;
    }

    public void setDetail1(String detail1) {
        this.detail1 = detail1;
    }
}
