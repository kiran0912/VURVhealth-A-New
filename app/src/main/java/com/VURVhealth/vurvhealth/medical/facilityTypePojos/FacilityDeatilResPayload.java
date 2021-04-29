package com.VURVhealth.vurvhealth.medical.facilityTypePojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityDeatilResPayload implements Parcelable {
    public static final Creator<FacilityDeatilResPayload> CREATOR = new C04971();
    @SerializedName("Fac_AddLat")
    @Expose
    private String facAddLat;
    @SerializedName("Fac_AddLong")
    @Expose
    private String facAddLong;
    @SerializedName("Fac_AddressLine1")
    @Expose
    private String facAddressLine1;
    @SerializedName("Fac_AddressLine2")
    @Expose
    private String facAddressLine2;
    @SerializedName("Fac_AddressType")
    @Expose
    private String facAddressType;
    @SerializedName("Fac_City")
    @Expose
    private String facCity;
    @SerializedName("Fac_Country")
    @Expose
    private String facCountry;
    @SerializedName("Fac_FacilityCategory")
    @Expose
    private String facFacilityCategory;
    @SerializedName("Fac_FacilitySubType")
    @Expose
    private String facFacilitySubType;
    @SerializedName("Fac_FacilityType")
    @Expose
    private String facFacilityType;
    @SerializedName("Fac_FaxNumber")
    @Expose
    private String facFaxNumber;
    @SerializedName("Fac_PhoneNumber")
    @Expose
    private String facPhoneNumber;
    @SerializedName("Fac_ProviderName")
    @Expose
    private String facProviderName;
    @SerializedName("Fac_State")
    @Expose
    private String facState;
    @SerializedName("Fac_ZipCode")
    @Expose
    private String facZipCode;

    /* renamed from: com.VURVhealth.VURVhealth.medical.facilityTypePojos.FacilityDeatilResPayload$1 */
    static class C04971 implements Creator<FacilityDeatilResPayload> {
        C04971() {
        }

        public FacilityDeatilResPayload createFromParcel(Parcel in) {
            return new FacilityDeatilResPayload(in);
        }

        public FacilityDeatilResPayload[] newArray(int size) {
            return new FacilityDeatilResPayload[size];
        }
    }

    public String getFacAddressType() {
        return this.facAddressType;
    }

    public void setFacAddressType(String facAddressType) {
        this.facAddressType = facAddressType;
    }

    public String getFacAddressLine1() {
        return this.facAddressLine1;
    }

    public void setFacAddressLine1(String facAddressLine1) {
        this.facAddressLine1 = facAddressLine1;
    }

    public String getFacAddressLine2() {
        return this.facAddressLine2;
    }

    public void setFacAddressLine2(String facAddressLine2) {
        this.facAddressLine2 = facAddressLine2;
    }

    public String getFacCity() {
        return this.facCity;
    }

    public void setFacCity(String facCity) {
        this.facCity = facCity;
    }

    public String getFacState() {
        return this.facState;
    }

    public void setFacState(String facState) {
        this.facState = facState;
    }

    public String getFacCountry() {
        return this.facCountry;
    }

    public void setFacCountry(String facCountry) {
        this.facCountry = facCountry;
    }

    public String getFacZipCode() {
        return this.facZipCode;
    }

    public void setFacZipCode(String facZipCode) {
        this.facZipCode = facZipCode;
    }

    public String getFacProviderName() {
        return this.facProviderName;
    }

    public void setFacProviderName(String facProviderName) {
        this.facProviderName = facProviderName;
    }

    public String getFacPhoneNumber() {
        return this.facPhoneNumber;
    }

    public void setFacPhoneNumber(String facPhoneNumber) {
        this.facPhoneNumber = facPhoneNumber;
    }

    public String getFacFaxNumber() {
        return this.facFaxNumber;
    }

    public void setFacFaxNumber(String facFaxNumber) {
        this.facFaxNumber = facFaxNumber;
    }

    public String getFacFacilityType() {
        return this.facFacilityType;
    }

    public void setFacFacilityType(String facFacilityType) {
        this.facFacilityType = facFacilityType;
    }

    public String getFacFacilitySubType() {
        return this.facFacilitySubType;
    }

    public void setFacFacilitySubType(String facFacilitySubType) {
        this.facFacilitySubType = facFacilitySubType;
    }

    public String getFacFacilityCategory() {
        return this.facFacilityCategory;
    }

    public void setFacFacilityCategory(String facFacilityCategory) {
        this.facFacilityCategory = facFacilityCategory;
    }

    public String getFacAddLat() {
        return this.facAddLat;
    }

    public void setFacAddLat(String facAddLat) {
        this.facAddLat = facAddLat;
    }

    public String getFacAddLong() {
        return this.facAddLong;
    }

    public void setFacAddLong(String facAddLong) {
        this.facAddLong = facAddLong;
    }

    protected FacilityDeatilResPayload(Parcel in) {
        this.facAddressType = in.readString();
        this.facAddressLine1 = in.readString();
        this.facAddressLine2 = in.readString();
        this.facCity = in.readString();
        this.facState = in.readString();
        this.facCountry = in.readString();
        this.facZipCode = in.readString();
        this.facProviderName = in.readString();
        this.facPhoneNumber = in.readString();
        this.facFaxNumber = in.readString();
        this.facFacilityType = in.readString();
        this.facFacilitySubType = in.readString();
        this.facFacilityCategory = in.readString();
        this.facAddLat = in.readString();
        this.facAddLong = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.facAddressType);
        parcel.writeString(this.facAddressLine1);
        parcel.writeString(this.facAddressLine2);
        parcel.writeString(this.facCity);
        parcel.writeString(this.facState);
        parcel.writeString(this.facCountry);
        parcel.writeString(this.facZipCode);
        parcel.writeString(this.facProviderName);
        parcel.writeString(this.facPhoneNumber);
        parcel.writeString(this.facFaxNumber);
        parcel.writeString(this.facFacilityType);
        parcel.writeString(this.facFacilitySubType);
        parcel.writeString(this.facFacilityCategory);
        parcel.writeString(this.facAddLat);
        parcel.writeString(this.facAddLong);
    }
}
