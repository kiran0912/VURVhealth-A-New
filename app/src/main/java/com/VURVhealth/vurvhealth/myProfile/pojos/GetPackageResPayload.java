package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPackageResPayload {
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    private String plan;
    @SerializedName("term_id")
    @Expose
    private String termId;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTermId() {
        return this.termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
