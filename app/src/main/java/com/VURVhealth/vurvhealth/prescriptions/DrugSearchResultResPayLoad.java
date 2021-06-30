package com.VURVhealth.vurvhealth.prescriptions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class DrugSearchResultResPayLoad {

    @SerializedName("Brand")
    @Expose
    private List<Brand> brand = null;
    @SerializedName("Generic")
    @Expose
    private List<Generic> generic = null;

    public List<Brand> getBrand() {
        return brand;
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public List<Generic> getGeneric() {
        return generic;
    }

    public void setGeneric(List<Generic> generic) {
        this.generic = generic;
    }

    public static class Brand {

        @SerializedName("NCPDP")
        @Expose
        private String nCPDP;
        @SerializedName("Price")
        @Expose
        private String price;
        @SerializedName("PharmacyName")
        @Expose
        private String pharmacyName;
        @SerializedName("Address1")
        @Expose
        private String address1;
        @SerializedName("Address2")
        @Expose
        private String address2;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("ZIP")
        @Expose
        private String zIP;
        @SerializedName("4Digit")
        @Expose
        private String _4Digit;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;

        public String getNCPDP() {
            return nCPDP;
        }

        public void setNCPDP(String nCPDP) {
            this.nCPDP = nCPDP;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPharmacyName() {
            return pharmacyName;
        }

        public void setPharmacyName(String pharmacyName) {
            this.pharmacyName = pharmacyName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZIP() {
            return zIP;
        }

        public void setZIP(String zIP) {
            this.zIP = zIP;
        }

        public String get4Digit() {
            return _4Digit;
        }

        public void set4Digit(String _4Digit) {
            this._4Digit = _4Digit;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

    }

    public static class Generic {

        @SerializedName("NCPDP")
        @Expose
        private String nCPDP;
        @SerializedName("Price")
        @Expose
        private String price;
        @SerializedName("PharmacyName")
        @Expose
        private String pharmacyName;
        @SerializedName("Address1")
        @Expose
        private String address1;
        @SerializedName("Address2")
        @Expose
        private String address2;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("ZIP")
        @Expose
        private String zIP;
        @SerializedName("4Digit")
        @Expose
        private String _4Digit;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;

        public String getNCPDP() {
            return nCPDP;
        }

        public void setNCPDP(String nCPDP) {
            this.nCPDP = nCPDP;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPharmacyName() {
            return pharmacyName;
        }

        public void setPharmacyName(String pharmacyName) {
            this.pharmacyName = pharmacyName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZIP() {
            return zIP;
        }

        public void setZIP(String zIP) {
            this.zIP = zIP;
        }

        public String get4Digit() {
            return _4Digit;
        }

        public void set4Digit(String _4Digit) {
            this._4Digit = _4Digit;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

    }


    public static class Datum implements Parcelable {
        public static final Creator<Datum> CREATOR = new Creator<Datum>() {
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };
        @SerializedName("4Digit")
        @Expose
        private String _4Digit;
        @SerializedName("Address1")
        @Expose
        private String address1;
        @SerializedName("Address2")
        @Expose
        private String address2;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("discountPercent")
        @Expose
        private String discountPercent;
        @SerializedName("Distance")
        @Expose
        private String distance;
        String drugType;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;
        @SerializedName("NCPDP")
        @Expose
        private String nCPDP;
        @SerializedName("PharmacyName")
        @Expose
        private String pharmacyName;
        @SerializedName("Phone")
        @Expose
        private String phone;
        @SerializedName("Price")
        @Expose
        private String price;
        private boolean savedItem;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("ZIP")
        @Expose
        private String zIP;

        public Datum() {

        }

        protected Datum(Parcel in) {
            this.nCPDP = in.readString();
            this.price = in.readString();
            this.pharmacyName = in.readString();
            this.address1 = in.readString();
            this.address2 = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.zIP = in.readString();
            this._4Digit = in.readString();
            this.phone = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.distance = in.readString();
            this.discountPercent = in.readString();
            this.drugType = in.readString();
            this.savedItem = in.readByte() != (byte) 0;
        }

        public String getnCPDP() {
            return this.nCPDP;
        }

        public void setnCPDP(String nCPDP) {
            this.nCPDP = nCPDP;
        }

        public String getzIP() {
            return this.zIP;
        }

        public void setzIP(String zIP) {
            this.zIP = zIP;
        }

        public String get_4Digit() {
            return this._4Digit;
        }

        public void set_4Digit(String _4Digit) {
            this._4Digit = _4Digit;
        }

        public boolean isSavedItem() {
            return this.savedItem;
        }

        public void setSavedItem(boolean savedItem) {
            this.savedItem = savedItem;
        }

        public String getNCPDP() {
            return this.nCPDP;
        }

        public void setNCPDP(String nCPDP) {
            this.nCPDP = nCPDP;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPharmacyName() {
            return this.pharmacyName;
        }

        public void setPharmacyName(String pharmacyName) {
            this.pharmacyName = pharmacyName;
        }

        public String getAddress1() {
            return this.address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return this.address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
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

        public String getZIP() {
            return this.zIP;
        }

        public void setZIP(String zIP) {
            this.zIP = zIP;
        }

        public String get4Digit() {
            return this._4Digit;
        }

        public void set4Digit(String _4Digit) {
            this._4Digit = _4Digit;
        }

        public String getPhone() {
            return this.phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLatitude() {
            return this.latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return this.longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getDistance() {
            return this.distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDiscountPercent() {
            return this.discountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            this.discountPercent = discountPercent;
        }

        public void setDrugType(String drugType) {
            this.drugType = drugType;
        }

        public String getDrugType() {
            return this.drugType;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.nCPDP);
            dest.writeString(this.price);
            dest.writeString(this.pharmacyName);
            dest.writeString(this.address1);
            dest.writeString(this.address2);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.zIP);
            dest.writeString(this._4Digit);
            dest.writeString(this.phone);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeString(this.distance);
            dest.writeString(this.discountPercent);
            dest.writeString(this.drugType);
            dest.writeByte((byte) (this.savedItem ? 1 : 0));
        }
    }

   /* public String getStatus() {
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
    }*/
}
