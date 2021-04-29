package com.VURVhealth.vurvhealth.upgrade.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 7/7/17.
 */

public class GetSubPackageResPayload {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("sale_price")
    @Expose
    private Object salePrice;
    @SerializedName("sale_price_dates_to")
    @Expose
    private String salePriceDatesTo;
    @SerializedName("subscription_price")
    @Expose
    private String subscriptionPrice;
    @SerializedName("subscription_trial_length")
    @Expose
    private String subscriptionTrialLength;
    @SerializedName("subscription_sign_up_fee")
    @Expose
    private String subscriptionSignUpFee;
    @SerializedName("subscription_period_interval")
    @Expose
    private String subscriptionPeriodInterval;
    @SerializedName("subscription_period")
    @Expose
    private String subscriptionPeriod;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Object getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Object salePrice) {
        this.salePrice = salePrice;
    }

    public String getSalePriceDatesTo() {
        return salePriceDatesTo;
    }

    public void setSalePriceDatesTo(String salePriceDatesTo) {
        this.salePriceDatesTo = salePriceDatesTo;
    }

    public String getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(String subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public String getSubscriptionTrialLength() {
        return subscriptionTrialLength;
    }

    public void setSubscriptionTrialLength(String subscriptionTrialLength) {
        this.subscriptionTrialLength = subscriptionTrialLength;
    }

    public String getSubscriptionSignUpFee() {
        return subscriptionSignUpFee;
    }

    public void setSubscriptionSignUpFee(String subscriptionSignUpFee) {
        this.subscriptionSignUpFee = subscriptionSignUpFee;
    }

    public String getSubscriptionPeriodInterval() {
        return subscriptionPeriodInterval;
    }

    public void setSubscriptionPeriodInterval(String subscriptionPeriodInterval) {
        this.subscriptionPeriodInterval = subscriptionPeriodInterval;
    }

    public String getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public void setSubscriptionPeriod(String subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }
}
