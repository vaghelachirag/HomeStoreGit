package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyCartReview implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer ID;

    @SerializedName("productId")
    @Expose
    private Integer productId;

    @SerializedName("selectedQuantity")
    @Expose
    private Integer selectedQuantity;

    @SerializedName("mainImage")
    @Expose
    private String mainImage;


    @SerializedName("availableQty")
    @Expose
    private Integer availableQty;

    @SerializedName("outOfStockQty")
    @Expose
    private Integer outOfStockQty;


    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("productName")
    @Expose
    private String productName;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public Integer getOutOfStockQty() {
        return outOfStockQty;
    }

    public void setOutOfStockQty(Integer outOfStockQty) {
        this.outOfStockQty = outOfStockQty;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
