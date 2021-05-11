package com.VURVhealth.vurvhealth.prescriptions;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrugSearchResultResPayLoad1 {

    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("ErrorOccurred")
    @Expose
    private Boolean errorOccurred;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("ErrorSeverity")
    @Expose
    private String errorSeverity;
    @SerializedName("ResponseText")
    @Expose
    private Object responseText;
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Boolean getErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(Boolean errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public String  getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public Object getResponseText() {
        return responseText;
    }

    public void setResponseText(Object responseText) {
        this.responseText = responseText;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public static class Result {

        @SerializedName("NDC")
        @Expose
        private String nDC;
        @SerializedName("Quantity")
        @Expose
        private Double quantity;
        @SerializedName("PharmacyPricings")
        @Expose
        private List<PharmacyPricing> pharmacyPricings = new ArrayList<>();
        @SerializedName("IsLegalEligible")
        @Expose
        private Boolean isLegalEligible;

        public String getNDC() {
            return nDC;
        }

        public void setNDC(String nDC) {
            this.nDC = nDC;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public List<PharmacyPricing> getPharmacyPricings() {
            return pharmacyPricings;
        }

        public void setPharmacyPricings(List<PharmacyPricing> pharmacyPricings) {
            this.pharmacyPricings = pharmacyPricings;
        }

        public Boolean getIsLegalEligible() {
            return isLegalEligible;
        }

        public void setIsLegalEligible(Boolean isLegalEligible) {
            this.isLegalEligible = isLegalEligible;
        }

        public static class PharmacyPricing implements Parcelable, ClusterItem {

            @SerializedName("Pharmacy")
            @Expose
            private Pharmacy pharmacy;
            @SerializedName("Prices")
            @Expose
            private List<Price> prices = new ArrayList<>();
            private boolean savedItem;
            private String drugType;


            protected PharmacyPricing() {
            }


            protected PharmacyPricing(Parcel in) {
                pharmacy = in.readParcelable(Pharmacy.class.getClassLoader());
                prices = in.createTypedArrayList(Price.CREATOR);
                savedItem = in.readByte() != 0;
                drugType = in.readString();
            }

            public static final Creator<PharmacyPricing> CREATOR = new Creator<PharmacyPricing>() {
                @Override
                public PharmacyPricing createFromParcel(Parcel in) {
                    return new PharmacyPricing(in);
                }

                @Override
                public PharmacyPricing[] newArray(int size) {
                    return new PharmacyPricing[size];
                }
            };

            public Pharmacy getPharmacy() {
                return pharmacy;
            }

            public void setPharmacy(Pharmacy pharmacy) {
                this.pharmacy = pharmacy;
            }

            public List<Price> getPrices() {
                return prices;
            }

            public void setPrices(List<Price> prices) {
                this.prices = prices;
            }


            public boolean isSavedItem() {
                return this.savedItem;
            }

            public void setSavedItem(boolean savedItem) {
                this.savedItem = savedItem;
            }

            public String getDrugType() {
                return drugType;
            }

            public void setDrugType(String drugType) {
                this.drugType = drugType;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeParcelable(pharmacy, i);
                parcel.writeTypedList(prices);
                parcel.writeByte((byte) (savedItem ? 1 : 0));
                parcel.writeString(drugType);
            }

            @Override
            public LatLng getPosition() {
                return new LatLng(getPharmacy().getAddress().getLatitude(),getPharmacy().getAddress().getLongitude());
            }

            @Override
            public String getTitle() {
                return pharmacy.name;
            }

            @Override
            public String getSnippet() {
                return null;
            }


            public static class Pharmacy implements Parcelable {

                @SerializedName("NABP")
                @Expose
                private String nABP;
                @SerializedName("Name")
                @Expose
                private String name;
                @SerializedName("Address")
                @Expose
                private Address address;
                @SerializedName("Distance")
                @Expose
                private Double distance;
                @SerializedName("NcpdpChaincode")
                @Expose
                private String ncpdpChaincode;
                @SerializedName("GetPreferredPBAClient")
                @Expose
                private Integer getPreferredPBAClient;
                @SerializedName("LogoUrl")
                @Expose
                private String logoUrl;
                @SerializedName("NPI")
                @Expose
                private String nPI;

                protected Pharmacy(){}


                protected Pharmacy(Parcel in) {
                    nABP = in.readString();
                    name = in.readString();
                    if (in.readByte() == 0) {
                        distance = null;
                    } else {
                        distance = in.readDouble();
                    }
                    ncpdpChaincode = in.readString();
                    if (in.readByte() == 0) {
                        getPreferredPBAClient = null;
                    } else {
                        getPreferredPBAClient = in.readInt();
                    }
                    logoUrl = in.readString();
                    nPI = in.readString();
                    address = in.readParcelable(Address.class.getClassLoader());
                }

                public static final Creator<Pharmacy> CREATOR = new Creator<Pharmacy>() {
                    @Override
                    public Pharmacy createFromParcel(Parcel in) {
                        return new Pharmacy(in);
                    }

                    @Override
                    public Pharmacy[] newArray(int size) {
                        return new Pharmacy[size];
                    }
                };

                public String getNABP() {
                    return nABP;
                }

                public void setNABP(String nABP) {
                    this.nABP = nABP;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Address getAddress() {
                    return address;
                }

                public void setAddress(Address address) {
                    this.address = address;
                }

                public Double getDistance() {
                    return distance;
                }

                public void setDistance(Double distance) {
                    this.distance = distance;
                }

                public String getNcpdpChaincode() {
                    return ncpdpChaincode;
                }

                public void setNcpdpChaincode(String ncpdpChaincode) {
                    this.ncpdpChaincode = ncpdpChaincode;
                }

                public Integer getGetPreferredPBAClient() {
                    return getPreferredPBAClient;
                }

                public void setGetPreferredPBAClient(Integer getPreferredPBAClient) {
                    this.getPreferredPBAClient = getPreferredPBAClient;
                }

                public String getLogoUrl() {
                    return logoUrl;
                }

                public void setLogoUrl(String logoUrl) {
                    this.logoUrl = logoUrl;
                }

                public String getNPI() {
                    return nPI;
                }

                public void setNPI(String nPI) {
                    this.nPI = nPI;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeString(nABP);
                    parcel.writeString(name);
                    if (distance == null) {
                        parcel.writeByte((byte) 0);
                    } else {
                        parcel.writeByte((byte) 1);
                        parcel.writeDouble(distance);
                    }
                    parcel.writeString(ncpdpChaincode);
                    if (getPreferredPBAClient == null) {
                        parcel.writeByte((byte) 0);
                    } else {
                        parcel.writeByte((byte) 1);
                        parcel.writeInt(getPreferredPBAClient);
                    }
                    parcel.writeString(logoUrl);
                    parcel.writeString(nPI);
                    parcel.writeParcelable(address, i);
                }


                public static class Address implements Parcelable, ClusterItem {

                    @SerializedName("AddressId")
                    @Expose
                    private Integer addressId;
                    @SerializedName("AddressGuid")
                    @Expose
                    private String addressGuid;
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
                    @SerializedName("PostalCode")
                    @Expose
                    private String postalCode;
                    @SerializedName("CreateDate")
                    @Expose
                    private String createDate;
                    @SerializedName("ModifiedDate")
                    @Expose
                    private String modifiedDate;
                    @SerializedName("Latitude")
                    @Expose
                    private Double latitude;
                    @SerializedName("Longitude")
                    @Expose
                    private Double longitude;
                    @SerializedName("DoValidation")
                    @Expose
                    private Boolean doValidation;
                    @SerializedName("DoMelissaValidation")
                    @Expose
                    private Boolean doMelissaValidation;
                    @SerializedName("IsEmpty")
                    @Expose
                    private Boolean isEmpty;
                    @SerializedName("IsComplete")
                    @Expose
                    private Boolean isComplete;
                    @SerializedName("FullAddress")
                    @Expose
                    private String fullAddress;
                    @SerializedName("AllowOnlyState")
                    @Expose
                    private Boolean allowOnlyState;

                    protected Address(){}

                    protected Address(Parcel in) {
                        if (in.readByte() == 0) {
                            addressId = null;
                        } else {
                            addressId = in.readInt();
                        }
                        addressGuid = in.readString();
                        address1 = in.readString();
                        address2 = in.readString();
                        city = in.readString();
                        state = in.readString();
                        postalCode = in.readString();
                        createDate = in.readString();
                        modifiedDate = in.readString();
                        if (in.readByte() == 0) {
                            latitude = null;
                        } else {
                            latitude = in.readDouble();
                        }
                        if (in.readByte() == 0) {
                            longitude = null;
                        } else {
                            longitude = in.readDouble();
                        }
                        byte tmpDoValidation = in.readByte();
                        doValidation = tmpDoValidation == 0 ? null : tmpDoValidation == 1;
                        byte tmpDoMelissaValidation = in.readByte();
                        doMelissaValidation = tmpDoMelissaValidation == 0 ? null : tmpDoMelissaValidation == 1;
                        byte tmpIsEmpty = in.readByte();
                        isEmpty = tmpIsEmpty == 0 ? null : tmpIsEmpty == 1;
                        byte tmpIsComplete = in.readByte();
                        isComplete = tmpIsComplete == 0 ? null : tmpIsComplete == 1;
                        fullAddress = in.readString();
                        byte tmpAllowOnlyState = in.readByte();
                        allowOnlyState = tmpAllowOnlyState == 0 ? null : tmpAllowOnlyState == 1;
                    }

                    public static final Creator<Address> CREATOR = new Creator<Address>() {
                        @Override
                        public Address createFromParcel(Parcel in) {
                            return new Address(in);
                        }

                        @Override
                        public Address[] newArray(int size) {
                            return new Address[size];
                        }
                    };

                    public Integer getAddressId() {
                        return addressId;
                    }

                    public void setAddressId(Integer addressId) {
                        this.addressId = addressId;
                    }

                    public String getAddressGuid() {
                        return addressGuid;
                    }

                    public void setAddressGuid(String addressGuid) {
                        this.addressGuid = addressGuid;
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

                    public String getPostalCode() {
                        return postalCode;
                    }

                    public void setPostalCode(String postalCode) {
                        this.postalCode = postalCode;
                    }

                    public String getCreateDate() {
                        return createDate;
                    }

                    public void setCreateDate(String createDate) {
                        this.createDate = createDate;
                    }

                    public String getModifiedDate() {
                        return modifiedDate;
                    }

                    public void setModifiedDate(String modifiedDate) {
                        this.modifiedDate = modifiedDate;
                    }

                    public Double getLatitude() {
                        return latitude;
                    }

                    public void setLatitude(Double latitude) {
                        this.latitude = latitude;
                    }

                    public Double getLongitude() {
                        return longitude;
                    }

                    public void setLongitude(Double longitude) {
                        this.longitude = longitude;
                    }

                    public Boolean getDoValidation() {
                        return doValidation;
                    }

                    public void setDoValidation(Boolean doValidation) {
                        this.doValidation = doValidation;
                    }

                    public Boolean getDoMelissaValidation() {
                        return doMelissaValidation;
                    }

                    public void setDoMelissaValidation(Boolean doMelissaValidation) {
                        this.doMelissaValidation = doMelissaValidation;
                    }

                    public Boolean getIsEmpty() {
                        return isEmpty;
                    }

                    public void setIsEmpty(Boolean isEmpty) {
                        this.isEmpty = isEmpty;
                    }

                    public Boolean getIsComplete() {
                        return isComplete;
                    }

                    public void setIsComplete(Boolean isComplete) {
                        this.isComplete = isComplete;
                    }

                    public String getFullAddress() {
                        return fullAddress;
                    }

                    public void setFullAddress(String fullAddress) {
                        this.fullAddress = fullAddress;
                    }

                    public Boolean getAllowOnlyState() {
                        return allowOnlyState;
                    }

                    public void setAllowOnlyState(Boolean allowOnlyState) {
                        this.allowOnlyState = allowOnlyState;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel parcel, int i) {
                        if (addressId == null) {
                            parcel.writeByte((byte) 0);
                        } else {
                            parcel.writeByte((byte) 1);
                            parcel.writeInt(addressId);
                        }
                        parcel.writeString(addressGuid);
                        parcel.writeString(address1);
                        parcel.writeString(address2);
                        parcel.writeString(city);
                        parcel.writeString(state);
                        parcel.writeString(postalCode);
                        parcel.writeString(createDate);
                        parcel.writeString(modifiedDate);
                        if (latitude == null) {
                            parcel.writeByte((byte) 0);
                        } else {
                            parcel.writeByte((byte) 1);
                            parcel.writeDouble(latitude);
                        }
                        if (longitude == null) {
                            parcel.writeByte((byte) 0);
                        } else {
                            parcel.writeByte((byte) 1);
                            parcel.writeDouble(longitude);
                        }
                        parcel.writeByte((byte) (doValidation == null ? 0 : doValidation ? 1 : 2));
                        parcel.writeByte((byte) (doMelissaValidation == null ? 0 : doMelissaValidation ? 1 : 2));
                        parcel.writeByte((byte) (isEmpty == null ? 0 : isEmpty ? 1 : 2));
                        parcel.writeByte((byte) (isComplete == null ? 0 : isComplete ? 1 : 2));
                        parcel.writeString(fullAddress);
                        parcel.writeByte((byte) (allowOnlyState == null ? 0 : allowOnlyState ? 1 : 2));
                    }

                    @Override
                    public LatLng getPosition() {
                        return new LatLng(latitude, longitude);
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

            }

            public static class Price implements Parcelable{

                @SerializedName("PBAClientId")
                @Expose
                private Integer pBAClientId;
                @SerializedName("PBAClientName")
                @Expose
                private String pBAClientName;
                @SerializedName("PartnerUser")
                @Expose
                private String partnerUser;
                @SerializedName("PartnerPassword")
                @Expose
                private String partnerPassword;
                @SerializedName("RequiresMembership")
                @Expose
                private Boolean requiresMembership;
                @SerializedName("Price")
                @Expose
                private String price;
                @SerializedName("ExclusivePriceDetails")
                @Expose
                private Object exclusivePriceDetails;
                @SerializedName("FormattedPrice")
                @Expose
                private String formattedPrice;
                @SerializedName("UsualAndCustomary")
                @Expose
                private Double usualAndCustomary;
                @SerializedName("PercentageSavings")
                @Expose
                private String percentageSavings;

                public Price(){}

                protected Price(Parcel in) {
                    if (in.readByte() == 0) {
                        pBAClientId = null;
                    } else {
                        pBAClientId = in.readInt();
                    }
                    pBAClientName = in.readString();
                    partnerUser = in.readString();
                    partnerPassword = in.readString();
                    byte tmpRequiresMembership = in.readByte();
                    requiresMembership = tmpRequiresMembership == 0 ? null : tmpRequiresMembership == 1;
                    price = in.readString();
                    formattedPrice = in.readString();
                    if (in.readByte() == 0) {
                        usualAndCustomary = null;
                    } else {
                        usualAndCustomary = in.readDouble();
                    }
                    percentageSavings = in.readString();
                }

                public static final Creator<Price> CREATOR = new Creator<Price>() {
                    @Override
                    public Price createFromParcel(Parcel in) {
                        return new Price(in);
                    }

                    @Override
                    public Price[] newArray(int size) {
                        return new Price[size];
                    }
                };

                public Integer getPBAClientId() {
                    return pBAClientId;
                }

                public void setPBAClientId(Integer pBAClientId) {
                    this.pBAClientId = pBAClientId;
                }

                public String getPBAClientName() {
                    return pBAClientName;
                }

                public void setPBAClientName(String pBAClientName) {
                    this.pBAClientName = pBAClientName;
                }

                public String getPartnerUser() {
                    return partnerUser;
                }

                public void setPartnerUser(String partnerUser) {
                    this.partnerUser = partnerUser;
                }

                public String getPartnerPassword() {
                    return partnerPassword;
                }

                public void setPartnerPassword(String partnerPassword) {
                    this.partnerPassword = partnerPassword;
                }

                public Boolean getRequiresMembership() {
                    return requiresMembership;
                }

                public void setRequiresMembership(Boolean requiresMembership) {
                    this.requiresMembership = requiresMembership;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public Object getExclusivePriceDetails() {
                    return exclusivePriceDetails;
                }

                public void setExclusivePriceDetails(Object exclusivePriceDetails) {
                    this.exclusivePriceDetails = exclusivePriceDetails;
                }

                public String getFormattedPrice() {
                    return formattedPrice;
                }

                public void setFormattedPrice(String formattedPrice) {
                    this.formattedPrice = formattedPrice;
                }

                public Double getUsualAndCustomary() {
                    return usualAndCustomary;
                }

                public void setUsualAndCustomary(Double usualAndCustomary) {
                    this.usualAndCustomary = usualAndCustomary;
                }

                public String getPercentageSavings() {
                    return percentageSavings;
                }

                public void setPercentageSavings(String percentageSavings) {
                    this.percentageSavings = percentageSavings;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                    if (pBAClientId == null) {
                        parcel.writeByte((byte) 0);
                    } else {
                        parcel.writeByte((byte) 1);
                        parcel.writeInt(pBAClientId);
                    }
                    parcel.writeString(pBAClientName);
                    parcel.writeString(partnerUser);
                    parcel.writeString(partnerPassword);
                    parcel.writeByte((byte) (requiresMembership == null ? 0 : requiresMembership ? 1 : 2));
                    parcel.writeString(price);
                    parcel.writeString(formattedPrice);
                    if (usualAndCustomary == null) {
                        parcel.writeByte((byte) 0);
                    } else {
                        parcel.writeByte((byte) 1);
                        parcel.writeDouble(usualAndCustomary);
                    }
                    parcel.writeString(percentageSavings);
                }
            }

        }

    }

}
