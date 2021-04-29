package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by yqlabs on 19/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "createCustomerShippingAddressRequest"
})
public class CustomerShippingAdrsResPayload {

    @JsonProperty("createCustomerShippingAddressRequest")
    private CreateCustomerShippingAddressRequest createCustomerShippingAddressRequest;

    @JsonProperty("createCustomerShippingAddressRequest")
    public CreateCustomerShippingAddressRequest getCreateCustomerShippingAddressRequest() {
        return createCustomerShippingAddressRequest;
    }

    @JsonProperty("createCustomerShippingAddressRequest")
    public void setCreateCustomerShippingAddressRequest(CreateCustomerShippingAddressRequest createCustomerShippingAddressRequest) {
        this.createCustomerShippingAddressRequest = createCustomerShippingAddressRequest;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "name",
            "transactionKey"
    })
    public static class MerchantAuthentication {

        @JsonProperty("name")
        private String name;
        @JsonProperty("transactionKey")
        private String transactionKey;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("transactionKey")
        public String getTransactionKey() {
            return transactionKey;
        }

        @JsonProperty("transactionKey")
        public void setTransactionKey(String transactionKey) {
            this.transactionKey = transactionKey;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "firstName",
            "lastName",
            "company",
            "address",
            "city",
            "state",
            "zip",
            "country",
            "phoneNumber",
            "faxNumber"
    })
    public static class Address {

        @JsonProperty("firstName")
        private String firstName;
        @JsonProperty("lastName")
        private String lastName;
        @JsonProperty("company")
        private String company;
        @JsonProperty("address")
        private String address;
        @JsonProperty("city")
        private String city;
        @JsonProperty("state")
        private String state;
        @JsonProperty("zip")
        private String zip;
        @JsonProperty("country")
        private String country;
        @JsonProperty("phoneNumber")
        private String phoneNumber;
        @JsonProperty("faxNumber")
        private String faxNumber;

        @JsonProperty("firstName")
        public String getFirstName() {
            return firstName;
        }

        @JsonProperty("firstName")
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonProperty("lastName")
        public String getLastName() {
            return lastName;
        }

        @JsonProperty("lastName")
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @JsonProperty("company")
        public String getCompany() {
            return company;
        }

        @JsonProperty("company")
        public void setCompany(String company) {
            this.company = company;
        }

        @JsonProperty("address")
        public String getAddress() {
            return address;
        }

        @JsonProperty("address")
        public void setAddress(String address) {
            this.address = address;
        }

        @JsonProperty("city")
        public String getCity() {
            return city;
        }

        @JsonProperty("city")
        public void setCity(String city) {
            this.city = city;
        }

        @JsonProperty("state")
        public String getState() {
            return state;
        }

        @JsonProperty("state")
        public void setState(String state) {
            this.state = state;
        }

        @JsonProperty("zip")
        public String getZip() {
            return zip;
        }

        @JsonProperty("zip")
        public void setZip(String zip) {
            this.zip = zip;
        }

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        @JsonProperty("country")
        public void setCountry(String country) {
            this.country = country;
        }

        @JsonProperty("phoneNumber")
        public String getPhoneNumber() {
            return phoneNumber;
        }

        @JsonProperty("phoneNumber")
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @JsonProperty("faxNumber")
        public String getFaxNumber() {
            return faxNumber;
        }

        @JsonProperty("faxNumber")
        public void setFaxNumber(String faxNumber) {
            this.faxNumber = faxNumber;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "merchantAuthentication",
            "customerProfileId",
            "address",
            "defaultShippingAddress"
    })
    public static class CreateCustomerShippingAddressRequest {

        @JsonProperty("merchantAuthentication")
        private MerchantAuthentication merchantAuthentication;
        @JsonProperty("customerProfileId")
        private String customerProfileId;
        @JsonProperty("address")
        private Address address;
        @JsonProperty("defaultShippingAddress")
        private Boolean defaultShippingAddress;

        @JsonProperty("merchantAuthentication")
        public MerchantAuthentication getMerchantAuthentication() {
            return merchantAuthentication;
        }

        @JsonProperty("merchantAuthentication")
        public void setMerchantAuthentication(MerchantAuthentication merchantAuthentication) {
            this.merchantAuthentication = merchantAuthentication;
        }

        @JsonProperty("customerProfileId")
        public String getCustomerProfileId() {
            return customerProfileId;
        }

        @JsonProperty("customerProfileId")
        public void setCustomerProfileId(String customerProfileId) {
            this.customerProfileId = customerProfileId;
        }

        @JsonProperty("address")
        public Address getAddress() {
            return address;
        }

        @JsonProperty("address")
        public void setAddress(Address address) {
            this.address = address;
        }

        @JsonProperty("defaultShippingAddress")
        public Boolean getDefaultShippingAddress() {
            return defaultShippingAddress;
        }

        @JsonProperty("defaultShippingAddress")
        public void setDefaultShippingAddress(Boolean defaultShippingAddress) {
            this.defaultShippingAddress = defaultShippingAddress;
        }

    }
}
