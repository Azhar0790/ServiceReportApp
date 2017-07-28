
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerListMessage{

    @SerializedName("d_mail")
    @Expose
    private String dMail;
    @SerializedName("d_fname")
    @Expose
    private String dFname;
    @SerializedName("d_lname")
    @Expose
    private String dLname;
    @SerializedName("d_city")
    @Expose
    private String dCity;
    @SerializedName("city_name")
    @Expose
    private String cityName;

    public String getDMail() {
        return dMail;
    }

    public void setDMail(String dMail) {
        this.dMail = dMail;
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

    public String getDCity() {
        return dCity;
    }

    public void setDCity(String dCity) {
        this.dCity = dCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
