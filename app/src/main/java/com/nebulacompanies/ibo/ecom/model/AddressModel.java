package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @SerializedName("id")
    @Expose
    private Integer ID;

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("addressLine1")
    @Expose
    private String addressLineOne;

    @SerializedName("addressLine2")
    @Expose
    private String addressLineTwo;

    @SerializedName("landmark")
    @Expose
    private String addressLandMark;

    @SerializedName("city")
    @Expose
    private String addressCity;

    @SerializedName("state")
    @Expose
    private String addressState;

    @SerializedName("addressType")
    @Expose
    private String addressType;

    @SerializedName("pinCode")
    @Expose
    private String addressPincode;


    @SerializedName("isServiceable")
    @Expose
    private boolean addressServiceable;

    public boolean isPincodeAvailableHS() {
        return pincodeAvailableHS;
    }

    public void setPincodeAvailableHS(boolean pincodeAvailableHS) {
        this.pincodeAvailableHS = pincodeAvailableHS;
    }

    @SerializedName("isPincodeAvailable")
    @Expose
    private boolean pincodeAvailableHS;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getAddressLandMark() {
        return addressLandMark;
    }

    public void setAddressLandMark(String addressLandMark) {
        this.addressLandMark = addressLandMark;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressPincode() {
        return addressPincode;
    }

    public void setAddressPincode(String addressPincode) {
        this.addressPincode = addressPincode;
    }

    public boolean isAddressServiceable() {
        return addressServiceable;
    }

    public void setAddressServiceable(boolean addressServiceable) {
        this.addressServiceable = addressServiceable;
    }
}
