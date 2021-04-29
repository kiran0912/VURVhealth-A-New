package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileResponsePojo {
    @SerializedName("Image Link")
    @Expose
    private String imageLink;

    public String getImageLink() {
        return this.imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
