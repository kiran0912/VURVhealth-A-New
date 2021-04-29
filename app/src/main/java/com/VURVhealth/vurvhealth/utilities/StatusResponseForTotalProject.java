package com.VURVhealth.vurvhealth.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 10/5/17.
 */

public class StatusResponseForTotalProject {
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("PractitionerProviderId")
    @Expose
    private String practitionerProviderId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPractitionerProviderId() {
        return practitionerProviderId;
    }

    public void setPractitionerProviderId(String practitionerProviderId) {
        this.practitionerProviderId = practitionerProviderId;
    }

    @SerializedName("FacilityProviderId")
    @Expose
    private String facilityProviderId;

    public String getFacilityProviderId() {
        return facilityProviderId;
    }

    public void setFacilityProviderId(String facilityProviderId) {
        this.facilityProviderId = facilityProviderId;
    }

    @SerializedName("ProviderId")
    @Expose
    private String ProviderId;

    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        ProviderId = providerId;
    }
}