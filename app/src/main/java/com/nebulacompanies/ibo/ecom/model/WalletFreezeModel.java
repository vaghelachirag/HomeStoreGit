package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletFreezeModel {
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

        @SerializedName("ewalletBalance")
        @Expose
        private int ewalletBalance;
        @SerializedName("isEwalletfreeze")
        @Expose
        private boolean isEwalletfreeze;

        public int getEwalletBalance() {
            return ewalletBalance;
        }

        public void setEwalletBalance(int ewalletBalance) {
            this.ewalletBalance = ewalletBalance;
        }

        public boolean isIsEwalletfreeze() {
            return isEwalletfreeze;
        }

        public void setIsEwalletfreeze(boolean isEwalletfreeze) {
            this.isEwalletfreeze = isEwalletfreeze;
        }

    }
}


