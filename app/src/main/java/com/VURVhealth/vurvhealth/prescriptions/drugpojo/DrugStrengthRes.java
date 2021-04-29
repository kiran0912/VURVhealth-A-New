package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugStrengthRes {

    @SerializedName("drugStrengthDts")
    @Expose
    private List<DrugStrengthDt> drugStrengthDts = null;

    public List<DrugStrengthDt> getDrugStrengthDts() {
        return drugStrengthDts;
    }

    public void setDrugStrengthDts(List<DrugStrengthDt> drugStrengthDts) {
        this.drugStrengthDts = drugStrengthDts;
    }

    public static class DrugStrengthDt {

        @SerializedName("drugStrength")
        @Expose
        private String drugStrength;

        public String getDrugStrength() {
            return drugStrength;
        }

        public void setDrugStrength(String drugStrength) {
            this.drugStrength = drugStrength;
        }

    }
}
