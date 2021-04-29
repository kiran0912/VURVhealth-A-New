package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 20/7/17.
 */

public class ChargeCustomerProfileRequest {
    @SerializedName("createTransactionRequest")
    @Expose
    private CreateTransactionRequest createTransactionRequest;

    public CreateTransactionRequest getCreateTransactionRequest() {
        return createTransactionRequest;
    }

    public void setCreateTransactionRequest(CreateTransactionRequest createTransactionRequest) {
        this.createTransactionRequest = createTransactionRequest;
    }

    public static class CreateTransactionRequest {

        @SerializedName("merchantAuthentication")
        @Expose
        private MerchantAuthentication merchantAuthentication;
        @SerializedName("refId")
        @Expose
        private String refId;
        @SerializedName("transactionRequest")
        @Expose
        private TransactionRequest transactionRequest;

        public MerchantAuthentication getMerchantAuthentication() {
            return merchantAuthentication;
        }

        public void setMerchantAuthentication(MerchantAuthentication merchantAuthentication) {
            this.merchantAuthentication = merchantAuthentication;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public TransactionRequest getTransactionRequest() {
            return transactionRequest;
        }

        public void setTransactionRequest(TransactionRequest transactionRequest) {
            this.transactionRequest = transactionRequest;
        }

    }

    public static class LineItem {

        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("unitPrice")
        @Expose
        private String unitPrice;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

    }

    public static class LineItems {

        @SerializedName("lineItem")
        @Expose
        private LineItem lineItem;

        public LineItem getLineItem() {
            return lineItem;
        }

        public void setLineItem(LineItem lineItem) {
            this.lineItem = lineItem;
        }

    }

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

    public static class PaymentProfile {

        @SerializedName("paymentProfileId")
        @Expose
        private String paymentProfileId;

        public String getPaymentProfileId() {
            return paymentProfileId;
        }

        public void setPaymentProfileId(String paymentProfileId) {
            this.paymentProfileId = paymentProfileId;
        }

    }

    public static class Profile {

        @SerializedName("customerProfileId")
        @Expose
        private String customerProfileId;
        @SerializedName("paymentProfile")
        @Expose
        private PaymentProfile paymentProfile;

        public String getCustomerProfileId() {
            return customerProfileId;
        }

        public void setCustomerProfileId(String customerProfileId) {
            this.customerProfileId = customerProfileId;
        }

        public PaymentProfile getPaymentProfile() {
            return paymentProfile;
        }

        public void setPaymentProfile(PaymentProfile paymentProfile) {
            this.paymentProfile = paymentProfile;
        }

    }

    public static class TransactionRequest {

        @SerializedName("transactionType")
        @Expose
        private String transactionType;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("profile")
        @Expose
        private Profile profile;
        @SerializedName("lineItems")
        @Expose
        private LineItems lineItems;

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public LineItems getLineItems() {
            return lineItems;
        }

        public void setLineItems(LineItems lineItems) {
            this.lineItems = lineItems;
        }

    }
}
