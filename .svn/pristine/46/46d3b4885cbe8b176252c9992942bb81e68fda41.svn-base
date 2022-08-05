package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Palak Mehta on 13-Jan-18.
 */

public class CartListModelEcom {
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

        @SerializedName("data")
        @Expose
        private MyCartData data;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private int statusCode;

        public MyCartData getData() {
            return data;
        }

        public void setData(MyCartData data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

    }

}
