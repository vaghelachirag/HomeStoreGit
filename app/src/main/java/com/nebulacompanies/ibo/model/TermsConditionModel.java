package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Response;

public class TermsConditionModel
{
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
        private List<TermsConditionData> data = null;

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

        public List<TermsConditionData> getData() {
            return data;
        }

        public void setData(List<TermsConditionData> data) {
            this.data = data;
        }

    }

    public class TermsConditionData
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("policyName")
        @Expose
        private String policyName;
        @SerializedName("settingKey")
        @Expose
        private String settingKey;
        @SerializedName("supportConfig")
        @Expose
        private String supportConfig;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("updatedOn")
        @Expose
        private String updatedOn;
        @SerializedName("createdBy")
        @Expose
        private Object createdBy;
        @SerializedName("updatedBy")
        @Expose
        private Object updatedBy;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("isDelete")
        @Expose
        private Boolean isDelete;
        @SerializedName("createdByNavigation")
        @Expose
        private Object createdByNavigation;
        @SerializedName("updatedByNavigation")
        @Expose
        private Object updatedByNavigation;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPolicyName() {
            return policyName;
        }

        public void setPolicyName(String policyName) {
            this.policyName = policyName;
        }

        public String getSettingKey() {
            return settingKey;
        }

        public void setSettingKey(String settingKey) {
            this.settingKey = settingKey;
        }

        public String getSupportConfig() {
            return supportConfig;
        }

        public void setSupportConfig(String supportConfig) {
            this.supportConfig = supportConfig;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Boolean getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Boolean isDelete) {
            this.isDelete = isDelete;
        }

        public Object getCreatedByNavigation() {
            return createdByNavigation;
        }

        public void setCreatedByNavigation(Object createdByNavigation) {
            this.createdByNavigation = createdByNavigation;
        }

        public Object getUpdatedByNavigation() {
            return updatedByNavigation;
        }

        public void setUpdatedByNavigation(Object updatedByNavigation) {
            this.updatedByNavigation = updatedByNavigation;
        }
    }



}
