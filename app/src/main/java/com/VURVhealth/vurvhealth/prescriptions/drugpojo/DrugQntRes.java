package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugQntRes {

    @SerializedName("drugQuantityDts")
    @Expose
    private List<DrugQuantityDt> drugQuantityDts = null;

    public List<DrugQuantityDt> getDrugQuantityDts() {
        return drugQuantityDts;
    }

    public void setDrugQuantityDts(List<DrugQuantityDt> drugQuantityDts) {
        this.drugQuantityDts = drugQuantityDts;
    }

    public static class DrugQuantityDt {

        @SerializedName("drugQuantity")
        @Expose
        private String drugQuantity;

        public String getDrugQuantity() {
            return drugQuantity;
        }

        public void setDrugQuantity(String drugQuantity) {
            this.drugQuantity = drugQuantity;
        }

    }

}
