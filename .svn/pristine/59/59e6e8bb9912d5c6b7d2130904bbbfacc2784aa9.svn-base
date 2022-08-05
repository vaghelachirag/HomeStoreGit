package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyShoppyModel {
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
        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }


    }
    public class Data {

        @SerializedName("shoppyPackageId")
        @Expose
        private int shoppyPackageId;
        @SerializedName("userId")
        @Expose
        private Object userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("minAmount")
        @Expose
        private int minAmount;

        public int getRepurchase() {
            return repurchase;
        }

        public void setRepurchase(int repurchase) {
            this.repurchase = repurchase;
        }

        @SerializedName("repurchase")
        @Expose
        private int repurchase;

        public int getShoppyPackageId() {
            return shoppyPackageId;
        }

        public void setShoppyPackageId(int shoppyPackageId) {
            this.shoppyPackageId = shoppyPackageId;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(int minAmount) {
            this.minAmount = minAmount;
        }
    }
}
