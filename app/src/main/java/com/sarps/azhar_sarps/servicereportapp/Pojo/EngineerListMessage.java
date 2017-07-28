
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EngineerListMessage{

    @SerializedName("d_mail")
    @Expose
    private String dMail;
    @SerializedName("enggfname")
    @Expose
    private String enggfname;
    @SerializedName("engglname")
    @Expose
    private String engglname;
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

    public String getEnggfname() {
        return enggfname;
    }

    public void setEnggfname(String enggfname) {
        this.enggfname = enggfname;
    }

    public String getEngglname() {
        return engglname;
    }

    public void setEngglname(String engglname) {
        this.engglname = engglname;
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
