package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yqlabs on 20/7/17.
 */

public class ChargeCustomerProfileResponse {
    @SerializedName("transactionResponse")
    @Expose
    private TransactionResponse transactionResponse;
    @SerializedName("refId")
    @Expose
    private String refId;
    @SerializedName("messages")
    @Expose
    private Messages messages;

    public TransactionResponse getTransactionResponse() {
        return transactionResponse;
    }

    public void setTransactionResponse(TransactionResponse transactionResponse) {
        this.transactionResponse = transactionResponse;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public static class Message {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("description")
        @Expose
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public static class Message_ {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("text")
        @Expose
        private String text;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    public static class Messages {

        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("message")
        @Expose
        private List<Message_> message = null;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public List<Message_> getMessage() {
            return message;
        }

        public void setMessage(List<Message_> message) {
            this.message = message;
        }

    }

    public static class Profile {

        @SerializedName("customerProfileId")
        @Expose
        private String customerProfileId;
        @SerializedName("customerPaymentProfileId")
        @Expose
        private String customerPaymentProfileId;

        public String getCustomerProfileId() {
            return customerProfileId;
        }

        public void setCustomerProfileId(String customerProfileId) {
            this.customerProfileId = customerProfileId;
        }

        public String getCustomerPaymentProfileId() {
            return customerPaymentProfileId;
        }

        public void setCustomerPaymentProfileId(String customerPaymentProfileId) {
            this.customerPaymentProfileId = customerPaymentProfileId;
        }

    }

    public static class TransactionResponse {

        @SerializedName("responseCode")
        @Expose
        private String responseCode;
        @SerializedName("authCode")
        @Expose
        private String authCode;
        @SerializedName("avsResultCode")
        @Expose
        private String avsResultCode;
        @SerializedName("cvvResultCode")
        @Expose
        private String cvvResultCode;
        @SerializedName("cavvResultCode")
        @Expose
        private String cavvResultCode;
        @SerializedName("transId")
        @Expose
        private String transId;
        @SerializedName("refTransID")
        @Expose
        private String refTransID;
        @SerializedName("transHash")
        @Expose
        private String transHash;
        @SerializedName("testRequest")
        @Expose
        private String testRequest;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("accountType")
        @Expose
        private String accountType;
        @SerializedName("messages")
        @Expose
        private List<Message> messages = null;
        @SerializedName("transHashSha2")
        @Expose
        private String transHashSha2;
        @SerializedName("profile")
        @Expose
        private Profile profile;

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getAvsResultCode() {
            return avsResultCode;
        }

        public void setAvsResultCode(String avsResultCode) {
            this.avsResultCode = avsResultCode;
        }

        public String getCvvResultCode() {
            return cvvResultCode;
        }

        public void setCvvResultCode(String cvvResultCode) {
            this.cvvResultCode = cvvResultCode;
        }

        public String getCavvResultCode() {
            return cavvResultCode;
        }

        public void setCavvResultCode(String cavvResultCode) {
            this.cavvResultCode = cavvResultCode;
        }

        public String getTransId() {
            return transId;
        }

        public void setTransId(String transId) {
            this.transId = transId;
        }

        public String getRefTransID() {
            return refTransID;
        }

        public void setRefTransID(String refTransID) {
            this.refTransID = refTransID;
        }

        public String getTransHash() {
            return transHash;
        }

        public void setTransHash(String transHash) {
            this.transHash = transHash;
        }

        public String getTestRequest() {
            return testRequest;
        }

        public void setTestRequest(String testRequest) {
            this.testRequest = testRequest;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public String getTransHashSha2() {
            return transHashSha2;
        }

        public void setTransHashSha2(String transHashSha2) {
            this.transHashSha2 = transHashSha2;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

    }

}
