package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 19/7/17.
 */

public class UpgradePackageReqPayload {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_profile_id")
    @Expose
    private String customerProfileId;
    @SerializedName("shipping_address_id")
    @Expose
    private String shippingAddressId;
    @SerializedName("shipping_address_hash")
    @Expose
    private String shippingAddressHash;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("cim_credit_card_trans_date")
    @Expose
    private String cimCreditCardTransDate;
    @SerializedName("cim_credit_card_environment")
    @Expose
    private String cimCreditCardEnvironment;
    @SerializedName("cim_credit_card_customer_id")
    @Expose
    private String cimCreditCardCustomerId;
    @SerializedName("cim_credit_card_payment_token")
    @Expose
    private String cimCreditCardPaymentToken;
    @SerializedName("cim_credit_card_account_four")
    @Expose
    private String cimCreditCardAccountFour;
    @SerializedName("cim_credit_card_charge_captured")
    @Expose
    private String cimCreditCardChargeCaptured;
    @SerializedName("cim_credit_card_card_expiry_date")
    @Expose
    private String cimCreditCardCardExpiryDate;
    @SerializedName("cim_credit_card_card_type")
    @Expose
    private String cimCreditCardCardType;
    @SerializedName("cim_credit_card_trans_id")
    @Expose
    private String cimCreditCardTransId;
    @SerializedName("_transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("cim_credit_card_authorization_code")
    @Expose
    private String cimCreditCardAuthorizationCode;
    @SerializedName("subscription_start_date")
    @Expose
    private String subscriptionStartDate;
    @SerializedName("subscription_end_date")
    @Expose
    private String subscriptionEndDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("product_id")
    @Expose
    private String productId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(String customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getShippingAddressHash() {
        return shippingAddressHash;
    }

    public void setShippingAddressHash(String shippingAddressHash) {
        this.shippingAddressHash = shippingAddressHash;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public String getCimCreditCardTransDate() {
        return cimCreditCardTransDate;
    }

    public void setCimCreditCardTransDate(String cimCreditCardTransDate) {
        this.cimCreditCardTransDate = cimCreditCardTransDate;
    }

    public String getCimCreditCardEnvironment() {
        return cimCreditCardEnvironment;
    }

    public void setCimCreditCardEnvironment(String cimCreditCardEnvironment) {
        this.cimCreditCardEnvironment = cimCreditCardEnvironment;
    }

    public String getCimCreditCardCustomerId() {
        return cimCreditCardCustomerId;
    }

    public void setCimCreditCardCustomerId(String cimCreditCardCustomerId) {
        this.cimCreditCardCustomerId = cimCreditCardCustomerId;
    }

    public String getCimCreditCardPaymentToken() {
        return cimCreditCardPaymentToken;
    }

    public void setCimCreditCardPaymentToken(String cimCreditCardPaymentToken) {
        this.cimCreditCardPaymentToken = cimCreditCardPaymentToken;
    }

    public String getCimCreditCardAccountFour() {
        return cimCreditCardAccountFour;
    }

    public void setCimCreditCardAccountFour(String cimCreditCardAccountFour) {
        this.cimCreditCardAccountFour = cimCreditCardAccountFour;
    }

    public String getCimCreditCardChargeCaptured() {
        return cimCreditCardChargeCaptured;
    }

    public void setCimCreditCardChargeCaptured(String cimCreditCardChargeCaptured) {
        this.cimCreditCardChargeCaptured = cimCreditCardChargeCaptured;
    }

    public String getCimCreditCardCardExpiryDate() {
        return cimCreditCardCardExpiryDate;
    }

    public void setCimCreditCardCardExpiryDate(String cimCreditCardCardExpiryDate) {
        this.cimCreditCardCardExpiryDate = cimCreditCardCardExpiryDate;
    }

    public String getCimCreditCardCardType() {
        return cimCreditCardCardType;
    }

    public void setCimCreditCardCardType(String cimCreditCardCardType) {
        this.cimCreditCardCardType = cimCreditCardCardType;
    }

    public String getCimCreditCardTransId() {
        return cimCreditCardTransId;
    }

    public void setCimCreditCardTransId(String cimCreditCardTransId) {
        this.cimCreditCardTransId = cimCreditCardTransId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCimCreditCardAuthorizationCode() {
        return cimCreditCardAuthorizationCode;
    }

    public void setCimCreditCardAuthorizationCode(String cimCreditCardAuthorizationCode) {
        this.cimCreditCardAuthorizationCode = cimCreditCardAuthorizationCode;
    }

    public String getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(String subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public String getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
