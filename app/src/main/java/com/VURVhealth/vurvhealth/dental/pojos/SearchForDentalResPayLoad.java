package com.VURVhealth.vurvhealth.dental.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;

public class SearchForDentalResPayLoad {
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    @SerializedName("StateList")
    @Expose
    private ArrayList<StateList> stateList = null;
    @SerializedName("status")
    @Expose
    private String status;

    public static class Datum implements Parcelable, ClusterItem {
        @SerializedName("Add1")
        @Expose
        private String add1;
        @SerializedName("Add2")
        @Expose
        private String add2;
        @SerializedName("Center")
        @Expose
        private String center;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("ContractDate")
        @Expose
        private String contractDate;
        @SerializedName("DedicatedFlag")
        @Expose
        private String dedicatedFlag;
        @SerializedName("Dist")
        @Expose
        private String dist;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("GdOrSpCode")
        @Expose
        private String gdOrSpCode;
        @SerializedName("Language")
        @Expose
        private String language;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Latitude")
        @Expose
        private double latitude;
        @SerializedName("locationcount")
        @Expose
        private String locationcount;
        @SerializedName("Longitude")
        @Expose
        private double longitude;
        @SerializedName("MidInitName")
        @Expose
        private String midInitName;
        @SerializedName("Npid")
        @Expose
        private String npid;
        @SerializedName("PhoneNo")
        @Expose
        private String phoneNo;
        @SerializedName("PostName")
        @Expose
        private String postName;
        @SerializedName("PracticeType")
        @Expose
        private String practiceType;
        @SerializedName("ProviderId")
        @Expose
        private String providerId;
        private int savedStatus;
        @SerializedName("Spec")
        @Expose
        private String spec;
        @SerializedName("SsnOrTin")
        @Expose
        private String ssnOrTin;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("ZipCode")
        @Expose
        private String zipCode;


        public Datum(Parcel in) {
            add1 = in.readString();
            add2 = in.readString();
            center = in.readString();
            city = in.readString();
            contractDate = in.readString();
            dedicatedFlag = in.readString();
            dist = in.readString();
            firstName = in.readString();
            gdOrSpCode = in.readString();
            language = in.readString();
            lastName = in.readString();
            latitude = in.readDouble();
            locationcount = in.readString();
            longitude = in.readDouble();
            midInitName = in.readString();
            npid = in.readString();
            phoneNo = in.readString();
            postName = in.readString();
            practiceType = in.readString();
            providerId = in.readString();
            savedStatus = in.readInt();
            spec = in.readString();
            ssnOrTin = in.readString();
            state = in.readString();
            status = in.readString();
            zipCode = in.readString();
        }

        public static final Creator<Datum> CREATOR = new Creator<Datum>() {
            @Override
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            @Override
            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };

        public int isSavedStatus() {
            return this.savedStatus;
        }

        public void setSavedStatus(int savedStatus) {
            this.savedStatus = savedStatus;
        }



        public String getProviderId() {
            return this.providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getLastName() {
            return this.lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMidInitName() {
            return this.midInitName;
        }

        public void setMidInitName(String midInitName) {
            this.midInitName = midInitName;
        }

        public String getPostName() {
            return this.postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getCenter() {
            return this.center;
        }

        public void setCenter(String center) {
            this.center = center;
        }

        public String getAdd1() {
            return this.add1;
        }

        public void setAdd1(String add1) {
            this.add1 = add1;
        }

        public String getAdd2() {
            return this.add2;
        }

        public void setAdd2(String add2) {
            this.add2 = add2;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return this.state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipCode() {
            return this.zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getPhoneNo() {
            return this.phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getSsnOrTin() {
            return this.ssnOrTin;
        }

        public void setSsnOrTin(String ssnOrTin) {
            this.ssnOrTin = ssnOrTin;
        }

        public String getGdOrSpCode() {
            return this.gdOrSpCode;
        }

        public void setGdOrSpCode(String gdOrSpCode) {
            this.gdOrSpCode = gdOrSpCode;
        }

        public String getSpec() {
            return this.spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPracticeType() {
            return this.practiceType;
        }

        public void setPracticeType(String practiceType) {
            this.practiceType = practiceType;
        }

        public String getContractDate() {
            return this.contractDate;
        }

        public void setContractDate(String contractDate) {
            this.contractDate = contractDate;
        }

        public String getNpid() {
            return this.npid;
        }

        public void setNpid(String npid) {
            this.npid = npid;
        }

        public String getDedicatedFlag() {
            return this.dedicatedFlag;
        }

        public void setDedicatedFlag(String dedicatedFlag) {
            this.dedicatedFlag = dedicatedFlag;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return this.longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getDist() {
            return this.dist;
        }

        public void setDist(String dist) {
            this.dist = dist;
        }

        public String getLocationcount() {
            return this.locationcount;
        }

        public void setLocationcount(String locationcount) {
            this.locationcount = locationcount;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.providerId);
            dest.writeString(this.lastName);
            dest.writeString(this.firstName);
            dest.writeString(this.midInitName);
            dest.writeString(this.postName);
            dest.writeString(this.center);
            dest.writeString(this.add1);
            dest.writeString(this.add2);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.zipCode);
            dest.writeString(this.phoneNo);
            dest.writeString(this.ssnOrTin);
            dest.writeString(this.gdOrSpCode);
            dest.writeString(this.spec);
            dest.writeString(this.language);
            dest.writeString(this.status);
            dest.writeString(this.practiceType);
            dest.writeString(this.contractDate);
            dest.writeString(this.npid);
            dest.writeString(this.dedicatedFlag);
            dest.writeDouble(this.latitude);
            dest.writeDouble(this.longitude);
            dest.writeString(this.dist);
            dest.writeString(this.locationcount);
            dest.writeInt(this.savedStatus);
        }

        @Override
        public LatLng getPosition() {
            return new LatLng(latitude,longitude);
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public String getSnippet() {
            return null;
        }
    }

    public class StateList {
        @SerializedName("RestrictedState")
        @Expose
        private String restrictedState;

        public String getRestrictedState() {
            return this.restrictedState;
        }

        public void setRestrictedState(String restrictedState) {
            this.restrictedState = restrictedState;
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Datum> getData() {
        return this.data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public ArrayList<StateList> getStateList() {
        return this.stateList;
    }

    public void setStateList(ArrayList<StateList> stateList) {
        this.stateList = stateList;
    }
}
