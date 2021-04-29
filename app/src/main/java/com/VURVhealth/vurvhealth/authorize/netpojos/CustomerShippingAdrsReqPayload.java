package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 19/7/17.
 */

public class CustomerShippingAdrsReqPayload {

    @SerializedName("createCustomerShippingAddressRequest")
    @Expose
    private CreateCustomerShippingAddressRequest createCustomerShippingAddressRequest;

    public CreateCustomerShippingAddressRequest getCreateCustomerShippingAddressRequest() {
        return createCustomerShippingAddressRequest;
    }

    public void setCreateCustomerShippingAddressRequest(CreateCustomerShippingAddressRequest createCustomerShippingAddressRequest) {
        this.createCustomerShippingAddressRequest = createCustomerShippingAddressRequest;
    }

    public class MerchantAuthentication {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("transactionKey")
        @Expose
        private String transactionKey;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTransactionKey() {
            return transactionKey;
        }

        public void setTransactionKey(String transactionKey) {
            this.transactionKey = transactionKey;
        }

    }

    public class CreateCustomerShippingAddressRequest {

        @SerializedName("merchantAuthentication")
        @Expose
        private MerchantAuthentication merchantAuthentication;
        @SerializedName("customerProfileId")
        @Expose
        private String customerProfileId;
        @SerializedName("address")
        @Expose
        private Address address;
        @SerializedName("defaultShippingAddress")
        @Expose
        private Boolean defaultShippingAddress;

        public MerchantAuthentication getMerchantAuthentication() {
            return merchantAuthentication;
        }

        public void setMerchantAuthentication(MerchantAuthentication merchantAuthentication) {
            this.merchantAuthentication = merchantAuthentication;
        }

        public String getCustomerProfileId() {
            return customerProfileId;
        }

        public void setCustomerProfileId(String customerProfileId) {
            this.customerProfileId = customerProfileId;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Boolean getDefaultShippingAddress() {
            return defaultShippingAddress;
        }

        public void setDefaultShippingAddress(Boolean defaultShippingAddress) {
            this.defaultShippingAddress = defaultShippingAddress;
        }

    }

    public class Address {

        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("zip")
        @Expose
        private String zip;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("faxNumber")
        @Expose
        private String faxNumber;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getFaxNumber() {
            return faxNumber;
        }

        public void setFaxNumber(String faxNumber) {
            this.faxNumber = faxNumber;
        }

    }

}
