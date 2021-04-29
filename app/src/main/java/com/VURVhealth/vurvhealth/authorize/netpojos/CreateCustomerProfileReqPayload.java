package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 28/6/17.
 */
@JsonPropertyOrder({
        "createCustomerProfileRequest"
})
public class CreateCustomerProfileReqPayload {

    @SerializedName("createCustomerProfileRequest")
    @Expose
    private CreateCustomerProfileRequest createCustomerProfileRequest;

    public CreateCustomerProfileRequest getCreateCustomerProfileRequest() {
        return createCustomerProfileRequest;
    }

    public void setCreateCustomerProfileRequest(CreateCustomerProfileRequest createCustomerProfileRequest) {
        this.createCustomerProfileRequest = createCustomerProfileRequest;
    }

    @JsonPropertyOrder(value = {
            "merchantAuthentication",
            "profile",
            "validationMode"
    })
    public static class CreateCustomerProfileRequest {

        @SerializedName("merchantAuthentication")
        @Expose
        private MerchantAuthentication merchantAuthentication;
        @SerializedName("profile")
        @Expose
        private Profile profile;
        @SerializedName("validationMode")
        @Expose
        private String validationMode;

        public MerchantAuthentication getMerchantAuthentication() {
            return merchantAuthentication;
        }

        public void setMerchantAuthentication(MerchantAuthentication merchantAuthentication) {
            this.merchantAuthentication = merchantAuthentication;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public String getValidationMode() {
            return validationMode;
        }

        public void setValidationMode(String validationMode) {
            this.validationMode = validationMode;
        }

    }

    @JsonPropertyOrder(value = {
            "creditCard"
    })
    public static class Payment {

        @SerializedName("creditCard")
        @Expose
        private CreditCard creditCard;

        public CreditCard getCreditCard() {
            return creditCard;
        }

        public void setCreditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
        }

    }

    @JsonPropertyOrder(value = {
            "customerType",
            "payment"
    })
    public static class PaymentProfiles {

        @SerializedName("customerType")
        @Expose
        private String customerType;
        @SerializedName("payment")
        @Expose
        private Payment payment;

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

    }

    @JsonPropertyOrder(value = {
            "cardNumber",
            "expirationDate"
    })
    public static class CreditCard {

        @SerializedName("cardNumber")
        @Expose
        private String cardNumber;
        @SerializedName("expirationDate")
        @Expose
        private String expirationDate;

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

    }

    @JsonPropertyOrder(value = {
            "name",
            "transactionKey"
    })
    public static class MerchantAuthentication {

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


    @JsonPropertyOrder(value = {
            "merchantCustomerId",
            "description",
            "email",
            "paymentProfiles"
    })
    public static class Profile {

        @SerializedName("merchantCustomerId")
        @Expose
        private String merchantCustomerId;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("paymentProfiles")
        @Expose
        private PaymentProfiles paymentProfiles;

        public String getMerchantCustomerId() {
            return merchantCustomerId;
        }

        public void setMerchantCustomerId(String merchantCustomerId) {
            this.merchantCustomerId = merchantCustomerId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public PaymentProfiles getPaymentProfiles() {
            return paymentProfiles;
        }

        public void setPaymentProfiles(PaymentProfiles paymentProfiles) {
            this.paymentProfiles = paymentProfiles;
        }

    }




}
