package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentPackageResPayload {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("post_title")
    @Expose
    private Object postTitle;
    @SerializedName("term_id")
    @Expose
    private String termId;

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

    public Object getPostTitle() {
        return this.postTitle;
    }

    public void setPostTitle(Object postTitle) {
        this.postTitle = postTitle;
    }
}
