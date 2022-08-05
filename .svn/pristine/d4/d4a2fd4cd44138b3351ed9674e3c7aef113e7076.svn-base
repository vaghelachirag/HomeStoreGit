package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Response;

public class OrderInventoryModel
{
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


        public String getPurchseQuantity() {
            return purchseQuantity;
        }

        public void setPurchseQuantity(String purchseQuantity) {
            this.purchseQuantity = purchseQuantity;
        }

        private String purchseQuantity;
        @SerializedName("productId")
        @Expose
        private int productId;
        @SerializedName("ecomProductID")
        @Expose
        private int ecomProductID;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("shortDescription")
        @Expose
        private String shortDescription;
        @SerializedName("iboPrice")
        @Expose
        private int iboPrice;
        @SerializedName("iboPriceWithOutTaxt")
        @Expose
        private double iboPriceWithOutTaxt;
        @SerializedName("mrp")
        @Expose
        private double mrp;
        @SerializedName("quantity")
        @Expose
        private int quantity;
        @SerializedName("pv")
        @Expose
        private double pv;
        @SerializedName("nv")
        @Expose
        private double nv;
        @SerializedName("bv")
        @Expose
        private int bv;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("homeStorePrice")
        @Expose
        private int  homeStorePrice ;

        public double getTax() {
            return tax;
        }

        public void setTax(double tax) {
            this.tax = tax;
        }

        @SerializedName("tax")
        @Expose
        private double tax;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getEcomProductID() {
            return ecomProductID;
        }

        public void setEcomProductID(int ecomProductID) {
            this.ecomProductID = ecomProductID;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public int getIboPrice() {
            return iboPrice;
        }

        public void setIboPrice(int iboPrice) {
            this.iboPrice = iboPrice;
        }

        public double getIboPriceWithOutTaxt() {
            return iboPriceWithOutTaxt;
        }

        public void setIboPriceWithOutTaxt(double iboPriceWithOutTaxt) {
            this.iboPriceWithOutTaxt = iboPriceWithOutTaxt;
        }

        public double getMrp() {
            return mrp;
        }

        public void setMrp(double mrp) {
            this.mrp = mrp;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPv() {
            return pv;
        }

        public void setPv(double pv) {
            this.pv = pv;
        }

        public double getNv() {
            return nv;
        }

        public void setNv(double nv) {
            this.nv = nv;
        }

        public int getBv() {
            return bv;
        }

        public void setBv(int bv) {
            this.bv = bv;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public int getHomeStorePrice() {
            return homeStorePrice;
        }

        public void setHomeStorePrice(int homeStorePrice) {
            this.homeStorePrice = homeStorePrice;
        }
    }
}
