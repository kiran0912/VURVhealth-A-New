package com.VURVhealth.vurvhealth.prescriptions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchResultReqPayLoad {
    @SerializedName("avgRetailPrice")
    @Expose
    private String avgRetailPrice;
    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("dosages")
    @Expose
    private List<Dosage> dosages = null;
    @SerializedName("drugDose")
    @Expose
    private String drugDose;
    @SerializedName("drugForm")
    @Expose
    private String drugForm;
    @SerializedName("drugName")
    @Expose
    private String drugName;
    @SerializedName("drugType")
    @Expose
    private String drugType;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("forms")
    @Expose
    private List<Form> forms = null;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("NDC")
    @Expose
    private String nDC;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("types")
    @Expose
    private List<Type> types = null;

    public static class Dosage {
        @SerializedName("DRG_STRENGTH")
        @Expose
        private String dRGSTRENGTH;

        public String getDRGSTRENGTH() {
            return this.dRGSTRENGTH;
        }

        public void setDRGSTRENGTH(String dRGSTRENGTH) {
            this.dRGSTRENGTH = dRGSTRENGTH;
        }
    }

    public static class Form {
        @SerializedName("DOS_FRM_DESCN")
        @Expose
        private String dOSFRMDESCN;

        public String getDOSFRMDESCN() {
            return this.dOSFRMDESCN;
        }

        public void setDOSFRMDESCN(String dOSFRMDESCN) {
            this.dOSFRMDESCN = dOSFRMDESCN;
        }
    }

    public static class Response {
        @SerializedName("getDrug")
        @Expose
        private Double getDrug;
        @SerializedName("getPharm")
        @Expose
        private Double getPharm;
        @SerializedName("getPrice")
        @Expose
        private Double getPrice;
        @SerializedName("getResults")
        @Expose
        private Double getResults;
        @SerializedName("had_swapped_drug_type")
        @Expose
        private Boolean hadSwappedDrugType;

        public Double getGetDrug() {
            return this.getDrug;
        }

        public void setGetDrug(Double getDrug) {
            this.getDrug = getDrug;
        }

        public Double getGetPharm() {
            return this.getPharm;
        }

        public void setGetPharm(Double getPharm) {
            this.getPharm = getPharm;
        }

        public Double getGetPrice() {
            return this.getPrice;
        }

        public void setGetPrice(Double getPrice) {
            this.getPrice = getPrice;
        }

        public Double getGetResults() {
            return this.getResults;
        }

        public void setGetResults(Double getResults) {
            this.getResults = getResults;
        }

        public Boolean getHadSwappedDrugType() {
            return this.hadSwappedDrugType;
        }

        public void setHadSwappedDrugType(Boolean hadSwappedDrugType) {
            this.hadSwappedDrugType = hadSwappedDrugType;
        }
    }

    public static class Result implements Parcelable {
        public static final Creator<Result> CREATOR = new C06321();
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
        private Double discountPercent;
        @SerializedName("Distance")
        @Expose
        private String distance;
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
        private Double price;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("WalMart")
        @Expose
        private String walMart;
        @SerializedName("ZIP")
        @Expose
        private String zIP;

        /* renamed from: com.VURVhealth.VURVhealth.prescriptions.SearchResultReqPayLoad$Result$1 */
        static class C06321 implements Creator<Result> {
            C06321() {
            }

            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            public Result[] newArray(int size) {
                return new Result[size];
            }
        }

        protected Result(Parcel in) {
            this.nCPDP = in.readString();
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
            this.walMart = in.readString();
        }

        public String getNCPDP() {
            return this.nCPDP;
        }

        public void setNCPDP(String nCPDP) {
            this.nCPDP = nCPDP;
        }

        public Double getPrice() {
            return this.price;
        }

        public void setPrice(Double price) {
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

        public String getWalMart() {
            return this.walMart;
        }

        public void setWalMart(String walMart) {
            this.walMart = walMart;
        }

        public Double getDiscountPercent() {
            return this.discountPercent;
        }

        public void setDiscountPercent(Double discountPercent) {
            this.discountPercent = discountPercent;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.nCPDP);
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
            dest.writeString(this.walMart);
        }
    }

    public static class Type {
        @SerializedName("DRUG_TYPE")
        @Expose
        private String dRUGTYPE;

        public String getDRUGTYPE() {
            return this.dRUGTYPE;
        }

        public void setDRUGTYPE(String dRUGTYPE) {
            this.dRUGTYPE = dRUGTYPE;
        }
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getError() {
        return this.error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDrugName() {
        return this.drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugForm() {
        return this.drugForm;
    }

    public void setDrugForm(String drugForm) {
        this.drugForm = drugForm;
    }

    public String getDrugDose() {
        return this.drugDose;
    }

    public void setDrugDose(String drugDose) {
        this.drugDose = drugDose;
    }

    public String getDrugType() {
        return this.drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getNDC() {
        return this.nDC;
    }

    public void setNDC(String nDC) {
        this.nDC = nDC;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRadius() {
        return this.radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public List<Result> getResults() {
        return this.results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Response getResponse() {
        return this.response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getAvgRetailPrice() {
        return this.avgRetailPrice;
    }

    public void setAvgRetailPrice(String avgRetailPrice) {
        this.avgRetailPrice = avgRetailPrice;
    }

    public List<Type> getTypes() {
        return this.types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Form> getForms() {
        return this.forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<Dosage> getDosages() {
        return this.dosages;
    }

    public void setDosages(List<Dosage> dosages) {
        this.dosages = dosages;
    }
}
