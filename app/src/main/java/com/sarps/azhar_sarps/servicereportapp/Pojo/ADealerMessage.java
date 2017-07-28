
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ADealerMessage {

    @SerializedName("d_id")
    @Expose
    private String dId;
    @SerializedName("d_fname")
    @Expose
    private String dFname;
    @SerializedName("d_lname")
    @Expose
    private String dLname;
    @SerializedName("d_address")
    @Expose
    private String dAddress;
    @SerializedName("d_mobile")
    @Expose
    private String dMobile;
    @SerializedName("d_mail")
    @Expose
    private String dMail;
    @SerializedName("d_role")
    @Expose
    private String dRole;
    @SerializedName("d_dob")
    @Expose
    private String dDob;
    @SerializedName("d_country")
    @Expose
    private String dCountry;
    @SerializedName("d_state")
    @Expose
    private String dState;
    @SerializedName("d_city")
    @Expose
    private String dCity;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("is_actve")
    @Expose
    private String isActve;

    public String getDId() {
        return dId;
    }

    public void setDId(String dId) {
        this.dId = dId;
    }

    public String getDFname() {
        return dFname;
    }

    public void setDFname(String dFname) {
        this.dFname = dFname;
    }

    public String getDLname() {
        return dLname;
    }

    public void setDLname(String dLname) {
        this.dLname = dLname;
    }

    public String getDAddress() {
        return dAddress;
    }

    public void setDAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getDMobile() {
        return dMobile;
    }

    public void setDMobile(String dMobile) {
        this.dMobile = dMobile;
    }

    public String getDMail() {
        return dMail;
    }

    public void setDMail(String dMail) {
        this.dMail = dMail;
    }

    public String getDRole() {
        return dRole;
    }

    public void setDRole(String dRole) {
        this.dRole = dRole;
    }

    public String getDDob() {
        return dDob;
    }

    public void setDDob(String dDob) {
        this.dDob = dDob;
    }

    public String getDCountry() {
        return dCountry;
    }

    public void setDCountry(String dCountry) {
        this.dCountry = dCountry;
    }

    public String getDState() {
        return dState;
    }

    public void setDState(String dState) {
        this.dState = dState;
    }

    public String getDCity() {
        return dCity;
    }

    public void setDCity(String dCity) {
        this.dCity = dCity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsActve() {
        return isActve;
    }

    public void setIsActve(String isActve) {
        this.isActve = isActve;
    }

}
