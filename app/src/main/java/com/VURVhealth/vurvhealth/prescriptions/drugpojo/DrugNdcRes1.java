package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugNdcRes1 {
    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("ErrorOccurred")
    @Expose
    private Boolean errorOccurred;
    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("ErrorSeverity")
    @Expose
    private String errorSeverity;
    @SerializedName("ResponseText")
    @Expose
    private Object responseText;
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Boolean getErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(Boolean errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public Object getResponseText() {
        return responseText;
    }

    public void setResponseText(Object responseText) {
        this.responseText = responseText;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public static class Result {

        @SerializedName("DefaultKey")
        @Expose
        private DefaultKey defaultKey;
        @SerializedName("Drugs")
        @Expose
        private List<Drug> drugs = null;

        public DefaultKey getDefaultKey() {
            return defaultKey;
        }

        public void setDefaultKey(DefaultKey defaultKey) {
            this.defaultKey = defaultKey;
        }

        public List<Drug> getDrugs() {
            return drugs;
        }

        public void setDrugs(List<Drug> drugs) {
            this.drugs = drugs;
        }

    }

    public static class DefaultKey {

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Form")
        @Expose
        private String form;
        @SerializedName("Dosage")
        @Expose
        private String dosage;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("DisplayQuantity")
        @Expose
        private String displayQuantity;
        @SerializedName("NDC")
        @Expose
        private String nDC;
        @SerializedName("IsGeneric")
        @Expose
        private Boolean isGeneric;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDisplayQuantity() {
            return displayQuantity;
        }

        public void setDisplayQuantity(String displayQuantity) {
            this.displayQuantity = displayQuantity;
        }

        public String getNDC() {
            return nDC;
        }

        public void setNDC(String nDC) {
            this.nDC = nDC;
        }

        public Boolean getIsGeneric() {
            return isGeneric;
        }

        public void setIsGeneric(Boolean isGeneric) {
            this.isGeneric = isGeneric;
        }

    }

    public static class Drug {

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Values")
        @Expose
        private List<Value> values = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Value> getValues() {
            return values;
        }

        public void setValues(List<Value> values) {
            this.values = values;
        }

        public static class Value {

            @SerializedName("Form")
            @Expose
            private String form;
            @SerializedName("Values")
            @Expose
            private List<Value_> values = null;

            public String getForm() {
                return form;
            }

            public void setForm(String form) {
                this.form = form;
            }

            public List<Value_> getValues() {
                return values;
            }

            public void setValues(List<Value_> values) {
                this.values = values;
            }

            public static class Value_ {

                @SerializedName("Dosage")
                @Expose
                private String dosage;
                @SerializedName("Values")
                @Expose
                private List<Value__> values = null;

                public String getDosage() {
                    return dosage;
                }

                public void setDosage(String dosage) {
                    this.dosage = dosage;
                }

                public List<Value__> getValues() {
                    return values;
                }

                public void setValues(List<Value__> values) {
                    this.values = values;
                }

                public static class Value__ {

                    @SerializedName("Quantity")
                    @Expose
                    private String quantity;
                    @SerializedName("Value")
                    @Expose
                    private Value___ value;

                    public String getQuantity() {
                        return quantity;
                    }

                    public void setQuantity(String quantity) {
                        this.quantity = quantity;
                    }

                    public Value___ getValue() {
                        return value;
                    }

                    public void setValue(Value___ value) {
                        this.value = value;
                    }

                    public class Value___ {

                        @SerializedName("FullName")
                        @Expose
                        private String fullName;
                        @SerializedName("Name")
                        @Expose
                        private String name;
                        @SerializedName("Strength")
                        @Expose
                        private Double strength;
                        @SerializedName("StrengthString")
                        @Expose
                        private String strengthString;
                        @SerializedName("StrengthUnitOfMeasure")
                        @Expose
                        private String strengthUnitOfMeasure;
                        @SerializedName("Form")
                        @Expose
                        private String form;
                        @SerializedName("RxNorm")
                        @Expose
                        private String rxNorm;
                        @SerializedName("GPI")
                        @Expose
                        private String gPI;
                        @SerializedName("NDC")
                        @Expose
                        private String nDC;
                        @SerializedName("Quantity")
                        @Expose
                        private Double quantity;
                        @SerializedName("PackageSize")
                        @Expose
                        private Double packageSize;
                        @SerializedName("PackageSizeUOM")
                        @Expose
                        private String packageSizeUOM;
                        @SerializedName("PackageQuantity")
                        @Expose
                        private Integer packageQuantity;
                        @SerializedName("NumScripts")
                        @Expose
                        private Integer numScripts;
                        @SerializedName("Inactive")
                        @Expose
                        private Boolean inactive;
                        @SerializedName("Repack")
                        @Expose
                        private Boolean repack;
                        @SerializedName("SEONoIndex")
                        @Expose
                        private Boolean sEONoIndex;
                        @SerializedName("CanonicalURL")
                        @Expose
                        private String canonicalURL;
                        @SerializedName("IsGeneric")
                        @Expose
                        private Boolean isGeneric;
                        @SerializedName("PriceUsualAndCustomary")
                        @Expose
                        private Double priceUsualAndCustomary;
                        @SerializedName("UnitDoseUnitUsePkgCode")
                        @Expose
                        private String unitDoseUnitUsePkgCode;
                        @SerializedName("Dosage")
                        @Expose
                        private String dosage;
                        @SerializedName("DisplayQuantity")
                        @Expose
                        private Double displayQuantity;
                        @SerializedName("SEOName")
                        @Expose
                        private String sEOName;
                        @SerializedName("TopPrescriptionSortOrder")
                        @Expose
                        private Integer topPrescriptionSortOrder;
                        @SerializedName("MarketingForm")
                        @Expose
                        private String marketingForm;
                        @SerializedName("MarketingName")
                        @Expose
                        private String marketingName;
                        @SerializedName("MarketingSEOName")
                        @Expose
                        private String marketingSEOName;
                        @SerializedName("Treatment")
                        @Expose
                        private String treatment;
                        @SerializedName("Description")
                        @Expose
                        private String description;
                        @SerializedName("MetaTitle")
                        @Expose
                        private String metaTitle;
                        @SerializedName("MetaDescription")
                        @Expose
                        private String metaDescription;
                        @SerializedName("DeaClassCode")
                        @Expose
                        private String deaClassCode;

                        public String getFullName() {
                            return fullName;
                        }

                        public void setFullName(String fullName) {
                            this.fullName = fullName;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public Double getStrength() {
                            return strength;
                        }

                        public void setStrength(Double strength) {
                            this.strength = strength;
                        }

                        public String getStrengthString() {
                            return strengthString;
                        }

                        public void setStrengthString(String strengthString) {
                            this.strengthString = strengthString;
                        }

                        public String getStrengthUnitOfMeasure() {
                            return strengthUnitOfMeasure;
                        }

                        public void setStrengthUnitOfMeasure(String strengthUnitOfMeasure) {
                            this.strengthUnitOfMeasure = strengthUnitOfMeasure;
                        }

                        public String getForm() {
                            return form;
                        }

                        public void setForm(String form) {
                            this.form = form;
                        }

                        public String getRxNorm() {
                            return rxNorm;
                        }

                        public void setRxNorm(String rxNorm) {
                            this.rxNorm = rxNorm;
                        }

                        public String getGPI() {
                            return gPI;
                        }

                        public void setGPI(String gPI) {
                            this.gPI = gPI;
                        }

                        public String getNDC() {
                            return nDC;
                        }

                        public void setNDC(String nDC) {
                            this.nDC = nDC;
                        }

                        public Double getQuantity() {
                            return quantity;
                        }

                        public void setQuantity(Double quantity) {
                            this.quantity = quantity;
                        }

                        public Double getPackageSize() {
                            return packageSize;
                        }

                        public void setPackageSize(Double packageSize) {
                            this.packageSize = packageSize;
                        }

                        public String getPackageSizeUOM() {
                            return packageSizeUOM;
                        }

                        public void setPackageSizeUOM(String packageSizeUOM) {
                            this.packageSizeUOM = packageSizeUOM;
                        }

                        public Integer getPackageQuantity() {
                            return packageQuantity;
                        }

                        public void setPackageQuantity(Integer packageQuantity) {
                            this.packageQuantity = packageQuantity;
                        }

                        public Integer getNumScripts() {
                            return numScripts;
                        }

                        public void setNumScripts(Integer numScripts) {
                            this.numScripts = numScripts;
                        }

                        public Boolean getInactive() {
                            return inactive;
                        }

                        public void setInactive(Boolean inactive) {
                            this.inactive = inactive;
                        }

                        public Boolean getRepack() {
                            return repack;
                        }

                        public void setRepack(Boolean repack) {
                            this.repack = repack;
                        }

                        public Boolean getSEONoIndex() {
                            return sEONoIndex;
                        }

                        public void setSEONoIndex(Boolean sEONoIndex) {
                            this.sEONoIndex = sEONoIndex;
                        }

                        public String getCanonicalURL() {
                            return canonicalURL;
                        }

                        public void setCanonicalURL(String canonicalURL) {
                            this.canonicalURL = canonicalURL;
                        }

                        public Boolean getIsGeneric() {
                            return isGeneric;
                        }

                        public void setIsGeneric(Boolean isGeneric) {
                            this.isGeneric = isGeneric;
                        }

                        public Double getPriceUsualAndCustomary() {
                            return priceUsualAndCustomary;
                        }

                        public void setPriceUsualAndCustomary(Double priceUsualAndCustomary) {
                            this.priceUsualAndCustomary = priceUsualAndCustomary;
                        }

                        public String getUnitDoseUnitUsePkgCode() {
                            return unitDoseUnitUsePkgCode;
                        }

                        public void setUnitDoseUnitUsePkgCode(String unitDoseUnitUsePkgCode) {
                            this.unitDoseUnitUsePkgCode = unitDoseUnitUsePkgCode;
                        }

                        public String getDosage() {
                            return dosage;
                        }

                        public void setDosage(String dosage) {
                            this.dosage = dosage;
                        }

                        public Double getDisplayQuantity() {
                            return displayQuantity;
                        }

                        public void setDisplayQuantity(Double displayQuantity) {
                            this.displayQuantity = displayQuantity;
                        }

                        public String getSEOName() {
                            return sEOName;
                        }

                        public void setSEOName(String sEOName) {
                            this.sEOName = sEOName;
                        }

                        public Integer getTopPrescriptionSortOrder() {
                            return topPrescriptionSortOrder;
                        }

                        public void setTopPrescriptionSortOrder(Integer topPrescriptionSortOrder) {
                            this.topPrescriptionSortOrder = topPrescriptionSortOrder;
                        }

                        public String getMarketingForm() {
                            return marketingForm;
                        }

                        public void setMarketingForm(String marketingForm) {
                            this.marketingForm = marketingForm;
                        }

                        public String getMarketingName() {
                            return marketingName;
                        }

                        public void setMarketingName(String marketingName) {
                            this.marketingName = marketingName;
                        }

                        public String getMarketingSEOName() {
                            return marketingSEOName;
                        }

                        public void setMarketingSEOName(String marketingSEOName) {
                            this.marketingSEOName = marketingSEOName;
                        }

                        public String getTreatment() {
                            return treatment;
                        }

                        public void setTreatment(String treatment) {
                            this.treatment = treatment;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public String getMetaTitle() {
                            return metaTitle;
                        }

                        public void setMetaTitle(String metaTitle) {
                            this.metaTitle = metaTitle;
                        }

                        public String getMetaDescription() {
                            return metaDescription;
                        }

                        public void setMetaDescription(String metaDescription) {
                            this.metaDescription = metaDescription;
                        }

                        public String getDeaClassCode() {
                            return deaClassCode;
                        }

                        public void setDeaClassCode(String deaClassCode) {
                            this.deaClassCode = deaClassCode;
                        }

                    }

                }


            }

        }

    }

}
