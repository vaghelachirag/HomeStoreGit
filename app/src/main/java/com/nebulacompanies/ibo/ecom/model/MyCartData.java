package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyCartData {

    @SerializedName("calculatedMRP")
    @Expose
    private int calculatedMRP;
    @SerializedName("cart")
    @Expose
    private List<MyCart> cart = null;
    @SerializedName("ewalletAmount")
    @Expose
    private int ewalletAmount;
    @SerializedName("grandTotal")
    @Expose
    private int grandTotal;
    @SerializedName("grandTotalWithEwallet")
    @Expose
    private int grandTotalWithEwallet;
    @SerializedName("isEwalletfreeze")
    @Expose
    private boolean isEwalletfreeze;
    @SerializedName("mainOfferMessage")
    @Expose
    private String mainOfferMessage;
    @SerializedName("priceSlab")
    @Expose
    private String priceSlab;
    @SerializedName("pvSlab")
    @Expose
    private String pvSlab;
    @SerializedName("retailProfit")
    @Expose
    private int retailProfit;
    @SerializedName("secondaryOfferMessage")
    @Expose
    private String secondaryOfferMessage;
    @SerializedName("shippingCharge")
    @Expose
    private int shippingCharge;

    public int getHomeStoreProfit() {
        return homeStoreProfit;
    }

    public void setHomeStoreProfit(int homeStoreProfit) {
        this.homeStoreProfit = homeStoreProfit;
    }

    public int getHomeStoreProfitPercent() {
        return homeStoreProfitPercent;
    }

    public void setHomeStoreProfitPercent(int homeStoreProfitPercent) {
        this.homeStoreProfitPercent = homeStoreProfitPercent;
    }

    @SerializedName("homeStoreProfit")
    @Expose
    private int homeStoreProfit;
    @SerializedName("homeStoreProfitPercent")
    @Expose
    private int homeStoreProfitPercent;




    public String getShippingChargeInfo() {
        return shippingChargeInfo;
    }

    public void setShippingChargeInfo(String shippingChargeInfo) {
        this.shippingChargeInfo = shippingChargeInfo;
    }

    @SerializedName("shippingChargeInfo")
    @Expose
    private String shippingChargeInfo;

    @SerializedName("shippingWithEwallet")
    @Expose
    private int shippingWithEwallet;
    @SerializedName("showOfferMessage")
    @Expose
    private boolean showOfferMessage;
    @SerializedName("showRetail")
    @Expose
    private boolean showRetail;
    @SerializedName("subTotal")
    @Expose
    private int subTotal;
    @SerializedName("subTotalWithEwallet")
    @Expose
    private int subTotalWithEwallet;
    @SerializedName("totalBV")
    @Expose
    private int totalBV;
    @SerializedName("totalItemCount")
    @Expose
    private int totalItemCount;
    @SerializedName("totalNV")
    @Expose
    private int totalNV;
    @SerializedName("totalPV")
    @Expose
    private int totalPV;

    public double  getHomeStorePrice() {
        return homeStorePrice;
    }

    public void setHomeStorePrice(double  homeStorePrice) {
        this.homeStorePrice = homeStorePrice;
    }

    public double  getHomeStorePercent() {
        return homeStorePercent;
    }

    public void setHomeStorePercent(double  homeStorePercent) {
        this.homeStorePercent = homeStorePercent;
    }

    @SerializedName("HomeStorePrice")
    @Expose
    private double  homeStorePrice ;

    @SerializedName("HomeStorePercent")
    @Expose
    private double  homeStorePercent;
    public int getCalculatedMRP() {
        return calculatedMRP;
    }

    public void setCalculatedMRP(int calculatedMRP) {
        this.calculatedMRP = calculatedMRP;
    }

    public List<MyCart> getCart() {
        return cart;
    }

    public void setCart(List<MyCart> cart) {
        this.cart = cart;
    }

    public int getEwalletAmount() {
        return ewalletAmount;
    }

    public void setEwalletAmount(int ewalletAmount) {
        this.ewalletAmount = ewalletAmount;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getGrandTotalWithEwallet() {
        return grandTotalWithEwallet;
    }

    public void setGrandTotalWithEwallet(int grandTotalWithEwallet) {
        this.grandTotalWithEwallet = grandTotalWithEwallet;
    }

    public boolean isIsEwalletfreeze() {
        return isEwalletfreeze;
    }

    public void setIsEwalletfreeze(boolean isEwalletfreeze) {
        this.isEwalletfreeze = isEwalletfreeze;
    }

    public String getMainOfferMessage() {
        return mainOfferMessage;
    }

    public void setMainOfferMessage(String mainOfferMessage) {
        this.mainOfferMessage = mainOfferMessage;
    }

    public String getPriceSlab() {
        return priceSlab;
    }

    public void setPriceSlab(String priceSlab) {
        this.priceSlab = priceSlab;
    }

    public String getPvSlab() {
        return pvSlab;
    }

    public void setPvSlab(String pvSlab) {
        this.pvSlab = pvSlab;
    }

    public int getRetailProfit() {
        return retailProfit;
    }

    public void setRetailProfit(int retailProfit) {
        this.retailProfit = retailProfit;
    }

    public String getSecondaryOfferMessage() {
        return secondaryOfferMessage;
    }

    public void setSecondaryOfferMessage(String secondaryOfferMessage) {
        this.secondaryOfferMessage = secondaryOfferMessage;
    }

    public int getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(int shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public int getShippingWithEwallet() {
        return shippingWithEwallet;
    }

    public void setShippingWithEwallet(int shippingWithEwallet) {
        this.shippingWithEwallet = shippingWithEwallet;
    }

    public boolean isShowOfferMessage() {
        return showOfferMessage;
    }

    public void setShowOfferMessage(boolean showOfferMessage) {
        this.showOfferMessage = showOfferMessage;
    }

    public boolean isShowRetail() {
        return showRetail;
    }

    public void setShowRetail(boolean showRetail) {
        this.showRetail = showRetail;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getSubTotalWithEwallet() {
        return subTotalWithEwallet;
    }

    public void setSubTotalWithEwallet(int subTotalWithEwallet) {
        this.subTotalWithEwallet = subTotalWithEwallet;
    }

    public int getTotalBV() {
        return totalBV;
    }

    public void setTotalBV(int totalBV) {
        this.totalBV = totalBV;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public int getTotalNV() {
        return totalNV;
    }

    public void setTotalNV(int totalNV) {
        this.totalNV = totalNV;
    }

    public int getTotalPV() {
        return totalPV;
    }

    public void setTotalPV(int totalPV) {
        this.totalPV = totalPV;
    }


}
