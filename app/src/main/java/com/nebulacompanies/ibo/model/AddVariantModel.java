package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddVariantModel {
    @SerializedName("ok")
    @Expose
    private int ok;
    @SerializedName("response")
    @Expose
    private Response response;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("statusCode")
        @Expose
        private int statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }

    public class Datum {

        @SerializedName("promoId")
        @Expose
        private int promoId;
        @SerializedName("optionId")
        @Expose
        private int optionId;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("productId")
        @Expose
        private int productId;
        @SerializedName("cartQuantity")
        @Expose
        private int cartQuantity;
        @SerializedName("volWt")
        @Expose
        private String volWt;
        @SerializedName("mrpPrice")
        @Expose
        private double mrpPrice;
        @SerializedName("pricePerPiece")
        @Expose
        private double pricePerPiece;
        @SerializedName("distributorPrice")
        @Expose
        private double distributorPrice;
        @SerializedName("homestorePrice")
        @Expose
        private int homestorePrice;
        @SerializedName("weightInGrams")
        @Expose
        private double weightInGrams;
        @SerializedName("mainImage")
        @Expose
        private String mainImage;
        @SerializedName("pv")
        @Expose
        private double pv;
        @SerializedName("bv")
        @Expose
        private double bv;
        @SerializedName("nv")
        @Expose
        private double nv;
        @SerializedName("isPromo")
        @Expose
        private boolean isPromo;

        public boolean isIsselected() {
            return isselected;
        }

        public void setIsselected(boolean isselected) {
            this.isselected = isselected;
        }

        boolean isselected=false;

        public int getPromoId() {
            return promoId;
        }

        public void setPromoId(int promoId) {
            this.promoId = promoId;
        }

        public int getOptionId() {
            return optionId;
        }

        public void setOptionId(int optionId) {
            this.optionId = optionId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getCartQuantity() {
            return cartQuantity;
        }

        public void setCartQuantity(int cartQuantity) {
            this.cartQuantity = cartQuantity;
        }

        public String getVolWt() {
            return volWt;
        }

        public void setVolWt(String volWt) {
            this.volWt = volWt;
        }

        public double getMrpPrice() {
            return mrpPrice;
        }

        public void setMrpPrice(double mrpPrice) {
            this.mrpPrice = mrpPrice;
        }

        public double getPricePerPiece() {
            return pricePerPiece;
        }

        public void setPricePerPiece(double pricePerPiece) {
            this.pricePerPiece = pricePerPiece;
        }

        public double getDistributorPrice() {
            return distributorPrice;
        }

        public void setDistributorPrice(double distributorPrice) {
            this.distributorPrice = distributorPrice;
        }

        public int getHomestorePrice() {
            return homestorePrice;
        }

        public void setHomestorePrice(int homestorePrice) {
            this.homestorePrice = homestorePrice;
        }

        public double getWeightInGrams() {
            return weightInGrams;
        }

        public void setWeightInGrams(double weightInGrams) {
            this.weightInGrams = weightInGrams;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public double getPv() {
            return pv;
        }

        public void setPv(double pv) {
            this.pv = pv;
        }

        public double getBv() {
            return bv;
        }

        public void setBv(double bv) {
            this.bv = bv;
        }

        public double getNv() {
            return nv;
        }

        public void setNv(double nv) {
            this.nv = nv;
        }

        public boolean isIsPromo() {
            return isPromo;
        }

        public void setIsPromo(boolean isPromo) {
            this.isPromo = isPromo;
        }

    }

}
