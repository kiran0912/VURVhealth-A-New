package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugNdcRes {

    @SerializedName("ndcDts")
    @Expose
    private List<NdcDt> ndcDts = null;

    public List<NdcDt> getNdcDts() {
        return ndcDts;
    }

    public void setNdcDts(List<NdcDt> ndcDts) {
        this.ndcDts = ndcDts;
    }


    public static class NdcDt {

        @SerializedName("NDC")
        @Expose
        private String nDC;

        public String getNDC() {
            return nDC;
        }

        public void setNDC(String nDC) {
            this.nDC = nDC;
        }

    }
}
