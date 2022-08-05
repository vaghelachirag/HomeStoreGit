package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nebulacompanies.ibo.model.AddVariantModel;

import java.util.List;

public class FreeProductListModel {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<AddVariantModel.Datum> data = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AddVariantModel.Datum> getData() {
        return data;
    }

    public void setData(List<AddVariantModel.Datum> data) {
        this.data = data;
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
        @SerializedName("mainImage")
        @Expose
        private String mainImage;
        @SerializedName("pv")
        @Expose
        private int pv;
        @SerializedName("bv")
        @Expose
        private int bv;
        @SerializedName("nv")
        @Expose
        private int nv;
        @SerializedName("isPromo")
        @Expose
        private boolean isPromo;
        @SerializedName("homestorePrice")
        @Expose
        private double homestorePrice;

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

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getBv() {
            return bv;
        }

        public void setBv(int bv) {
            this.bv = bv;
        }

        public int getNv() {
            return nv;
        }

        public void setNv(int nv) {
            this.nv = nv;
        }

        public boolean isIsPromo() {
            return isPromo;
        }

        public void setIsPromo(boolean isPromo) {
            this.isPromo = isPromo;
        }

        public double getHomestorePrice() {
            return homestorePrice;
        }

        public void setHomestorePrice(double homestorePrice) {
            this.homestorePrice = homestorePrice;
        }
        public boolean isIsselected() {
            return isselected;
        }

        public void setIsselected(boolean isselected) {
            this.isselected = isselected;
        }

        boolean isselected=false;
    }
}
