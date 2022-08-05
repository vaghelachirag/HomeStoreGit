package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickUpAddressModel {

    @SerializedName("id")
    @Expose
    private Integer ID;

    @SerializedName("address")
    @Expose
    private String pickUpAddress;

    @SerializedName("facility")
    @Expose
    private String pickUpAddressFacility;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }


    public String getPickUpAddressFacility() {
        return pickUpAddressFacility;
    }

    public void setPickUpAddressFacility(String pickUpAddressFacility) {
        this.pickUpAddressFacility = pickUpAddressFacility;
    }
}
