package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChooseShoppyModel {
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
        @SerializedName("columns")
        @Expose
        private Object columns;
        @SerializedName("other")
        @Expose
        private Object other;
        @SerializedName("totalRecords")
        @Expose
        private Object totalRecords;

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

    public class Datum {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("minAmount")
        @Expose
        private int minAmount;
        @SerializedName("repurchase")
        @Expose
        private int repurchase;
        @SerializedName("imagePath")
        @Expose
        private String imagePath;
        @SerializedName("isActive")
        @Expose
        private boolean isActive;
        @SerializedName("isDelete")
        @Expose
        private boolean isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }

        public int getRepurchase() {
            return repurchase;
        }

        public void setRepurchase(int repurchase) {
            this.repurchase = repurchase;
        }
    }
}
