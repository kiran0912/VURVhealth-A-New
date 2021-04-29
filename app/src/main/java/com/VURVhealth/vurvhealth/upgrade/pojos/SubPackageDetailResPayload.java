package com.VURVhealth.vurvhealth.upgrade.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 18/7/17.
 */

public class SubPackageDetailResPayload {

    @SerializedName("meta_id")
    @Expose
    private Integer metaId;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("meta_key")
    @Expose
    private String metaKey;
    @SerializedName("meta_value")
    @Expose
    private String metaValue;

    public Integer getMetaId() {
        return metaId;
    }

    public void setMetaId(Integer metaId) {
        this.metaId = metaId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }
}
