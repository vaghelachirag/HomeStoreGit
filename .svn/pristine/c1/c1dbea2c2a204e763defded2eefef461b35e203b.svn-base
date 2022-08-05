package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class DeleteAddressModel {


    @SerializedName("ok")
    @Expose
    private Integer ok;
    @SerializedName("response")
    @Expose
    private Response response;

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
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
        private Integer statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Integer data;
        @SerializedName("columns")
        @Expose
        private Object columns;
        @SerializedName("other")
        @Expose
        private Object other;
        @SerializedName("totalRecords")
        @Expose
        private Object totalRecords;

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

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public Object getColumns() {
            return columns;
        }

        public void setColumns(Object columns) {
            this.columns = columns;
        }

        public Object getOther() {
            return other;
        }

        public void setOther(Object other) {
            this.other = other;
        }

        public Object getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(Object totalRecords) {
            this.totalRecords = totalRecords;
        }

    }
}
