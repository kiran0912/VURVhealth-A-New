package com.VURVhealth.vurvhealth.vision.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;

public class SearchForVisionResPayload {
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
        public final Creator<Datum> CREATOR = new Creator<Datum>() {
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };
        @SerializedName("AddressLine1")
        @Expose
        private String addressLine1;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("CountyName")
        @Expose
        private String countyName;
        @SerializedName("Degree")
        @Expose
        private String degree;
        @SerializedName("DoctarLanguage")
        @Expose
        private String doctarLanguage;
        @SerializedName("DoctorFridayHours")
        @Expose
        private String doctorFridayHours;
        @SerializedName("DoctorMondayHours")
        @Expose
        private String doctorMondayHours;
        @SerializedName("DoctorSaturdayHours")
        @Expose
        private String doctorSaturdayHours;
        @SerializedName("DoctorSundayHours")
        @Expose
        private String doctorSundayHours;
        @SerializedName("DoctorThursDayHours")
        @Expose
        private String doctorThursDayHours;
        @SerializedName("DoctorTuesdayHours")
        @Expose
        private String doctorTuesdayHours;
        @SerializedName("DoctorWednesDayHours")
        @Expose
        private String doctorWednesDayHours;
        @SerializedName("DoctorsHour")
        @Expose
        private String doctorsHour;
        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Latitude")
        @Expose
        private Double latitude;
        @SerializedName("locationcount")
        @Expose
        private String locationcount;
        @SerializedName("Longitude")
        @Expose
        private Double longitude;
        @SerializedName("OfficeFridayHours")
        @Expose
        private String officeFridayHours;
        @SerializedName("OfficeHours")
        @Expose
        private String officeHours;
        @SerializedName("OfficeLanguage")
        @Expose
        private String officeLanguage;
        @SerializedName("OfficeMondayHours")
        @Expose
        private String officeMondayHours;
        @SerializedName("OfficeSaturdayHours")
        @Expose
        private String officeSaturdayHours;
        @SerializedName("OfficeSundayHours")
        @Expose
        private String officeSundayHours;
        @SerializedName("OfficeThursdayHours")
        @Expose
        private String officeThursdayHours;
        @SerializedName("OfficeTuesdayHours")
        @Expose
        private String officeTuesdayHours;
        @SerializedName("OfficeWednesdayHours")
        @Expose
        private String officeWednesdayHours;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("PracticeName")
        @Expose
        private String practiceName;
        private int savedStatus;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("VisProviderId")
        @Expose
        private String visProviderId;
        @SerializedName("ZipCode")
        @Expose
        private String zipCode;

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

        public int isSavedStatus() {
            return this.savedStatus;
        }

        public void setSavedStatus(int savedStatus) {
            this.savedStatus = savedStatus;
        }

        protected Datum(Parcel in) {
            this.visProviderId = in.readString();
            this.degree = in.readString();
            this.gender = in.readString();
            this.fullName = in.readString();
            this.practiceName = in.readString();
            this.addressLine1 = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.zipCode = in.readString();
            this.phone = in.readString();
            this.countyName = in.readString();
            this.doctarLanguage = in.readString();
            this.officeLanguage = in.readString();
            this.doctorMondayHours = in.readString();
            this.doctorTuesdayHours = in.readString();
            this.doctorWednesDayHours = in.readString();
            this.doctorThursDayHours = in.readString();
            this.doctorFridayHours = in.readString();
            this.doctorSaturdayHours = in.readString();
            this.doctorSundayHours = in.readString();
            this.doctorsHour = in.readString();
            this.officeMondayHours = in.readString();
            this.officeTuesdayHours = in.readString();
            this.officeWednesdayHours = in.readString();
            this.officeThursdayHours = in.readString();
            this.officeFridayHours = in.readString();
            this.officeSaturdayHours = in.readString();
            this.officeSundayHours = in.readString();
            this.officeHours = in.readString();
            this.latitude = in.readDouble();
            this.longitude = in.readDouble();
            this.locationcount = in.readString();
            this.savedStatus = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.visProviderId);
            dest.writeString(this.degree);
            dest.writeString(this.gender);
            dest.writeString(this.fullName);
            dest.writeString(this.practiceName);
            dest.writeString(this.addressLine1);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.zipCode);
            dest.writeString(this.phone);
            dest.writeString(this.countyName);
            dest.writeString(this.doctarLanguage);
            dest.writeString(this.officeLanguage);
            dest.writeString(this.doctorMondayHours);
            dest.writeString(this.doctorTuesdayHours);
            dest.writeString(this.doctorWednesDayHours);
            dest.writeString(this.doctorThursDayHours);
            dest.writeString(this.doctorFridayHours);
            dest.writeString(this.doctorSaturdayHours);
            dest.writeString(this.doctorSundayHours);
            dest.writeString(this.doctorsHour);
            dest.writeString(this.officeMondayHours);
            dest.writeString(this.officeTuesdayHours);
            dest.writeString(this.officeWednesdayHours);
            dest.writeString(this.officeThursdayHours);
            dest.writeString(this.officeFridayHours);
            dest.writeString(this.officeSaturdayHours);
            dest.writeString(this.officeSundayHours);
            dest.writeString(this.officeHours);
            dest.writeDouble(this.latitude);
            dest.writeDouble(this.longitude);
            dest.writeString(this.locationcount);
            dest.writeInt(this.savedStatus);
        }

        public int describeContents() {
            return 0;
        }

        public String getVisProviderId() {
            return this.visProviderId;
        }

        public void setVisProviderId(String visProviderId) {
            this.visProviderId = visProviderId;
        }

        public String getDegree() {
            return this.degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFullName() {
            return this.fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPracticeName() {
            return this.practiceName;
        }

        public void setPracticeName(String practiceName) {
            this.practiceName = practiceName;
        }

        public String getAddressLine1() {
            return this.addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
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

        public String getPhone() {
            return this.phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCountyName() {
            return this.countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public String getDoctarLanguage() {
            return this.doctarLanguage;
        }

        public void setDoctarLanguage(String doctarLanguage) {
            this.doctarLanguage = doctarLanguage;
        }

        public String getOfficeLanguage() {
            return this.officeLanguage;
        }

        public void setOfficeLanguage(String officeLanguage) {
            this.officeLanguage = officeLanguage;
        }

        public String getDoctorMondayHours() {
            return this.doctorMondayHours;
        }

        public void setDoctorMondayHours(String doctorMondayHours) {
            this.doctorMondayHours = doctorMondayHours;
        }

        public String getDoctorTuesdayHours() {
            return this.doctorTuesdayHours;
        }

        public void setDoctorTuesdayHours(String doctorTuesdayHours) {
            this.doctorTuesdayHours = doctorTuesdayHours;
        }

        public String getDoctorWednesDayHours() {
            return this.doctorWednesDayHours;
        }

        public void setDoctorWednesDayHours(String doctorWednesDayHours) {
            this.doctorWednesDayHours = doctorWednesDayHours;
        }

        public String getDoctorThursDayHours() {
            return this.doctorThursDayHours;
        }

        public void setDoctorThursDayHours(String doctorThursDayHours) {
            this.doctorThursDayHours = doctorThursDayHours;
        }

        public String getDoctorFridayHours() {
            return this.doctorFridayHours;
        }

        public void setDoctorFridayHours(String doctorFridayHours) {
            this.doctorFridayHours = doctorFridayHours;
        }

        public String getDoctorSaturdayHours() {
            return this.doctorSaturdayHours;
        }

        public void setDoctorSaturdayHours(String doctorSaturdayHours) {
            this.doctorSaturdayHours = doctorSaturdayHours;
        }

        public String getDoctorSundayHours() {
            return this.doctorSundayHours;
        }

        public void setDoctorSundayHours(String doctorSundayHours) {
            this.doctorSundayHours = doctorSundayHours;
        }

        public String getDoctorsHour() {
            return this.doctorsHour;
        }

        public void setDoctorsHour(String doctorsHour) {
            this.doctorsHour = doctorsHour;
        }

        public String getOfficeMondayHours() {
            return this.officeMondayHours;
        }

        public void setOfficeMondayHours(String officeMondayHours) {
            this.officeMondayHours = officeMondayHours;
        }

        public String getOfficeTuesdayHours() {
            return this.officeTuesdayHours;
        }

        public void setOfficeTuesdayHours(String officeTuesdayHours) {
            this.officeTuesdayHours = officeTuesdayHours;
        }

        public String getOfficeWednesdayHours() {
            return this.officeWednesdayHours;
        }

        public void setOfficeWednesdayHours(String officeWednesdayHours) {
            this.officeWednesdayHours = officeWednesdayHours;
        }

        public String getOfficeThursdayHours() {
            return this.officeThursdayHours;
        }

        public void setOfficeThursdayHours(String officeThursdayHours) {
            this.officeThursdayHours = officeThursdayHours;
        }

        public String getOfficeFridayHours() {
            return this.officeFridayHours;
        }

        public void setOfficeFridayHours(String officeFridayHours) {
            this.officeFridayHours = officeFridayHours;
        }

        public String getOfficeSaturdayHours() {
            return this.officeSaturdayHours;
        }

        public void setOfficeSaturdayHours(String officeSaturdayHours) {
            this.officeSaturdayHours = officeSaturdayHours;
        }

        public String getOfficeSundayHours() {
            return this.officeSundayHours;
        }

        public void setOfficeSundayHours(String officeSundayHours) {
            this.officeSundayHours = officeSundayHours;
        }

        public String getOfficeHours() {
            return this.officeHours;
        }

        public void setOfficeHours(String officeHours) {
            this.officeHours = officeHours;
        }

        public Double getLatitude() {
            return this.latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return this.longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getLocationcount() {
            return this.locationcount;
        }

        public void setLocationcount(String locationcount) {
            this.locationcount = locationcount;
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
