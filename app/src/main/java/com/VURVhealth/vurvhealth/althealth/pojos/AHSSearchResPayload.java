package com.VURVhealth.vurvhealth.althealth.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;

public class AHSSearchResPayload {
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    @SerializedName("StateList")
    @Expose
    private ArrayList<StateList> stateList = null;
    @SerializedName("status")
    @Expose
    private String status;

    public class Datum implements Parcelable, ClusterItem {
        public final Creator<Datum> CREATOR = new C03101();
        @SerializedName("AHSProviderId")
        @Expose
        private String aHSProviderId;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("ClinicName")
        @Expose
        private String clinicName;
        @SerializedName("County")
        @Expose
        private String county;
        @SerializedName("Degree")
        @Expose
        private String degree;
        @SerializedName("Distance")
        @Expose
        private String distance;
        @SerializedName("Latitude")
        @Expose
        private double latitude;
        @SerializedName("locationcount")
        @Expose
        private String locationcount;
        @SerializedName("Longitude")
        @Expose
        private double longitude;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("PhoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("ProgramDegree")
        @Expose
        private String programDegree;
        private int savedStatus;
        @SerializedName("Specialty")
        @Expose
        private String specialty;
        @SerializedName("State")
        @Expose
        private String state;

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

        /* renamed from: com.VURVhealth.VURVhealth.althealth.pojos.AHSSearchResPayload$Datum$1 */
        class C03101 implements Creator<Datum> {
            C03101() {
            }

            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        }

        public int isSavedStatus() {
            return this.savedStatus;
        }

        public void setSavedStatus(int savedStatus) {
            this.savedStatus = savedStatus;
        }

        protected Datum(Parcel in) {
            this.aHSProviderId = in.readString();
            this.name = in.readString();
            this.specialty = in.readString();
            this.degree = in.readString();
            this.programDegree = in.readString();
            this.clinicName = in.readString();
            this.address = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.county = in.readString();
            this.phoneNumber = in.readString();
            this.latitude = in.readDouble();
            this.longitude = in.readDouble();
            this.distance = in.readString();
            this.locationcount = in.readString();
            this.savedStatus = in.readInt();
        }

        public String getAHSProviderId() {
            return this.aHSProviderId;
        }

        public void setAHSProviderId(String aHSProviderId) {
            this.aHSProviderId = aHSProviderId;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpecialty() {
            return this.specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getDegree() {
            return this.degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getProgramDegree() {
            return this.programDegree;
        }

        public void setProgramDegree(String programDegree) {
            this.programDegree = programDegree;
        }

        public String getClinicName() {
            return this.clinicName;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getCounty() {
            return this.county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getPhoneNumber() {
            return this.phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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

        public String getDistance() {
            return this.distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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
            dest.writeString(this.aHSProviderId);
            dest.writeString(this.name);
            dest.writeString(this.specialty);
            dest.writeString(this.degree);
            dest.writeString(this.programDegree);
            dest.writeString(this.clinicName);
            dest.writeString(this.address);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.county);
            dest.writeString(this.phoneNumber);
            dest.writeDouble(this.latitude);
            dest.writeDouble(this.longitude);
            dest.writeString(this.distance);
            dest.writeString(this.locationcount);
            dest.writeInt(this.savedStatus);
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
