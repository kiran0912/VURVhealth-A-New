package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugNameRes1 {
    @SerializedName("hit_type")
    @Expose
    private String hitType;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("seo_name")
    @Expose
    private String seoName;
    @SerializedName("ndc")
    @Expose
    private String ndc;

    public String getHitType() {
        return hitType;
    }

    public void setHitType(String hitType) {
        this.hitType = hitType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSeoName() {
        return seoName;
    }

    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

    public String getNdc() {
        return ndc;
    }

    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    public class Location {

        @SerializedName("lat")
        @Expose
        private Integer lat;
        @SerializedName("lon")
        @Expose
        private Integer lon;

        public Integer getLat() {
            return lat;
        }

        public void setLat(Integer lat) {
            this.lat = lat;
        }

        public Integer getLon() {
            return lon;
        }

        public void setLon(Integer lon) {
            this.lon = lon;
        }

    }
}
