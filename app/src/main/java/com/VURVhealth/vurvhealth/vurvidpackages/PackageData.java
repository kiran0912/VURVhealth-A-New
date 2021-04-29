package com.VURVhealth.vurvhealth.vurvidpackages;

public class PackageData {
    private boolean activeStatus;
    private String cardName;
    private String description;
    private String heading;
    private int images;

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImages() {
        return this.images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isActiveStatus() {
        return this.activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
