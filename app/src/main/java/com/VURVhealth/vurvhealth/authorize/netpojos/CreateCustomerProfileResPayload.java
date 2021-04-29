package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by yqlabs on 28/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCustomerProfileResPayload {

    @JsonProperty("customerProfileId")
    private String customerProfileId;
    @JsonProperty("customerPaymentProfileIdList")
    private List<String> customerPaymentProfileIdList = null;
    @JsonProperty("customerShippingAddressIdList")
    private List<String> customerShippingAddressIdList = null;
    @JsonProperty("validationDirectResponseList")
    private List<String> validationDirectResponseList = null;
    @JsonProperty("messages")
    private Messages messages;

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    @JsonProperty("customerProfileId")
    public String getCustomerProfileId() {
        return customerProfileId;
    }

    @JsonProperty("customerProfileId")
    public void setCustomerProfileId(String customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    @JsonProperty("customerPaymentProfileIdList")
    public List<String> getCustomerPaymentProfileIdList() {
        return customerPaymentProfileIdList;
    }

    @JsonProperty("customerPaymentProfileIdList")
    public void setCustomerPaymentProfileIdList(List<String> customerPaymentProfileIdList) {
        this.customerPaymentProfileIdList = customerPaymentProfileIdList;
    }

    @JsonProperty("customerShippingAddressIdList")
    public List<String> getCustomerShippingAddressIdList() {
        return customerShippingAddressIdList;
    }

    @JsonProperty("customerShippingAddressIdList")
    public void setCustomerShippingAddressIdList(List<String> customerShippingAddressIdList) {
        this.customerShippingAddressIdList = customerShippingAddressIdList;
    }

    @JsonProperty("validationDirectResponseList")
    public List<String> getValidationDirectResponseList() {
        return validationDirectResponseList;
    }

    @JsonProperty("validationDirectResponseList")
    public void setValidationDirectResponseList(List<String> validationDirectResponseList) {
        this.validationDirectResponseList = validationDirectResponseList;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        @JsonProperty("code")
        private String code;
        @JsonProperty("text")
        private String text;

        @JsonProperty("code")
        public String getCode() {
            return code;
        }

        @JsonProperty("code")
        public void setCode(String code) {
            this.code = code;
        }

        @JsonProperty("text")
        public String getText() {
            return text;
        }

        @JsonProperty("text")
        public void setText(String text) {
            this.text = text;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Messages {

        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("message")
        private List<Message> message = null;

        @JsonProperty("resultCode")
        public String getResultCode() {
            return resultCode;
        }

        @JsonProperty("resultCode")
        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        @JsonProperty("message")
        public List<Message> getMessage() {
            return message;
        }

        @JsonProperty("message")
        public void setMessage(List<Message> message) {
            this.message = message;
        }

    }
}
