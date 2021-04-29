package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugNameRes {

    @SerializedName("drugNameDts")
    @Expose
    private List<DrugNameDt> drugNameDts = null;

    public List<DrugNameDt> getDrugNameDts() {
        return drugNameDts;
    }

    public void setDrugNameDts(List<DrugNameDt> drugNameDts) {
        this.drugNameDts = drugNameDts;
    }

    public static class DrugNameDt {

        @SerializedName("DrugName")
        @Expose
        private String drugName;

        public String getDrugName() {
            return drugName;
        }

        public void setDrugName(String drugName) {
            this.drugName = drugName;
        }

    }

}
