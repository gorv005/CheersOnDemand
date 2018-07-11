package com.cheersondemand.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GAURAV on 7/11/2018.
 */

public class ProfileUpdateRequest {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("customer_type")
    @Expose
    private String customerType;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    /*@SerializedName("profile_picture")
    @Expose
    private MultipartBody.Part profilePicture;*/
    @SerializedName("is_notification_enabled")
    @Expose
    private Boolean isNotificationEnabled;
    @SerializedName("delete_profile_picture")
    @Expose
    private Integer deleteProfilePicture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

   /* public MultipartBody.Part getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartBody.Part profilePicture) {
        this.profilePicture = profilePicture;
    }*/

    public Boolean getIsNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setIsNotificationEnabled(Boolean isNotificationEnabled) {
        this.isNotificationEnabled = isNotificationEnabled;
    }

    public Integer getDeleteProfilePicture() {
        return deleteProfilePicture;
    }

    public void setDeleteProfilePicture(Integer deleteProfilePicture) {
        this.deleteProfilePicture = deleteProfilePicture;
    }
}
