package com.VURVhealth.vurvhealth.prescriptions;

public class RecentDrugPojo {
    public String brand_name;
    private String drug_form;
    public String drug_strength;
    public String id;
    public String lbl_name;
    private String zip_code;

    public String getZip_code() {
        return this.zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLbl_name() {
        return this.lbl_name;
    }

    public void setLbl_name(String lbl_name) {
        this.lbl_name = lbl_name;
    }

    public String getDrug_strength() {
        return this.drug_strength;
    }

    public void setDrug_strength(String drug_strength) {
        this.drug_strength = drug_strength;
    }

    public String getBrand_name() {
        return this.brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public RecentDrugPojo(String id, String lbl_name, String drug_strength, String drug_form, String brand_name, String zip_code) {
        this.id = id;
        this.lbl_name = lbl_name;
        this.drug_strength = drug_strength;
        this.drug_form = drug_form;
        this.brand_name = brand_name;
        this.zip_code = zip_code;
    }

    public String getDrug_form() {
        return this.drug_form;
    }

    public void setDrug_form(String drug_form) {
        this.drug_form = drug_form;
    }
}
