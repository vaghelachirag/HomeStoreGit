package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginValidate
{@SerializedName("StatusCode")
@Expose
private int statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
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
    public class Data {

        @SerializedName("IBOKeyID")
        @Expose
        private String iBOKeyID;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Username")
        @Expose
        private String username;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("IsKYC")
        @Expose
        private boolean isKYC;
        @SerializedName("IsExpired")
        @Expose
        private boolean isExpired;
        @SerializedName("Rank")
        @Expose
        private String rank;
        @SerializedName("IsSuspended")
        @Expose
        private boolean isSuspended;
        @SerializedName("IsBlocked")
        @Expose
        private boolean isBlocked;

        public String getIBOKeyID() {
            return iBOKeyID;
        }

        public void setIBOKeyID(String iBOKeyID) {
            this.iBOKeyID = iBOKeyID;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isIsKYC() {
            return isKYC;
        }

        public void setIsKYC(boolean isKYC) {
            this.isKYC = isKYC;
        }

        public boolean isIsExpired() {
            return isExpired;
        }

        public void setIsExpired(boolean isExpired) {
            this.isExpired = isExpired;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public boolean isIsSuspended() {
            return isSuspended;
        }

        public void setIsSuspended(boolean isSuspended) {
            this.isSuspended = isSuspended;
        }

        public boolean isIsBlocked() {
            return isBlocked;
        }

        public void setIsBlocked(boolean isBlocked) {
            this.isBlocked = isBlocked;
        }

    }

}
