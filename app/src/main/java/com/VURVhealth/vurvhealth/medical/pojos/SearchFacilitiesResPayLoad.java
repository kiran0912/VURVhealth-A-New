package com.VURVhealth.vurvhealth.medical.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

public class SearchFacilitiesResPayLoad implements Parcelable, ClusterItem {
    public static final Creator<SearchFacilitiesResPayLoad> CREATOR = new C04981();
    @SerializedName("Fac_ECPProvider")
    @Expose
    private String Fac_ECPProvider;
    @SerializedName("Fac_JCAHOAccrediated")
    @Expose
    private String Fac_JCAHOAccrediated;
    @SerializedName("Handicapped_Flag")
    @Expose
    private String Handicapped_Flag;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("FacilityName")
    @Expose
    private String facilityName;
    @SerializedName("FacilityType")
    @Expose
    private String facilityType;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("ProviderID")
    @Expose
    private String providerID;
    @SerializedName("Radius")
    @Expose
    private String radius;
    private int savedItem;

    @Override
    public LatLng getPosition() {
        return new LatLng(lat,lng);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    /* renamed from: com.VURVhealth.VURVhealth.medical.pojos.SearchFacilitiesResPayLoad$1 */
    static class C04981 implements Creator<SearchFacilitiesResPayLoad> {
        C04981() {
        }

        public SearchFacilitiesResPayLoad createFromParcel(Parcel in) {
            return new SearchFacilitiesResPayLoad(in);
        }

        public SearchFacilitiesResPayLoad[] newArray(int size) {
            return new SearchFacilitiesResPayLoad[size];
        }
    }

    public int getSavedItem() {
        return this.savedItem;
    }

    public void setSavedItem(int savedItem) {
        this.savedItem = savedItem;
    }

    public String getHandicapped_Flag() {
        return this.Handicapped_Flag;
    }

    public void setHandicapped_Flag(String handicapped_Flag) {
        this.Handicapped_Flag = handicapped_Flag;
    }

    public String getFac_ECPProvider() {
        return this.Fac_ECPProvider;
    }

    public void setFac_ECPProvider(String fac_ECPProvider) {
        this.Fac_ECPProvider = fac_ECPProvider;
    }

    public String getFac_JCAHOAccrediated() {
        return this.Fac_JCAHOAccrediated;
    }

    public void setFac_JCAHOAccrediated(String fac_JCAHOAccrediated) {
        this.Fac_JCAHOAccrediated = fac_JCAHOAccrediated;
    }

    public SearchFacilitiesResPayLoad(Parcel in) {
        this.providerID = in.readString();
        this.facilityName = in.readString();
        this.address = in.readString();
        this.mobileNo = in.readString();
        this.facilityType = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.radius = in.readString();
        this.savedItem = in.readInt();
    }
    public String getProviderID() {
        return this.providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getFacilityName() {
        return this.facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFacilityType() {
        return this.facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getRadius() {
        return this.radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public int isSavedStatus() {
        return this.savedItem;
    }

    public void setSavedStatus(int savedStatus) {
        this.savedItem = savedStatus;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.providerID);
        dest.writeString(this.facilityName);
        dest.writeString(this.address);
        dest.writeString(this.mobileNo);
        dest.writeString(this.facilityType);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeString(this.radius);
        dest.writeInt(this.savedItem);
    }
}
