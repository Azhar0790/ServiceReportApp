
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DEngineerMessage {

    @SerializedName("d_id")
    @Expose
    private String dId;
    @SerializedName("d_city")
    @Expose
    private String dCity;
    @SerializedName("d_fname")
    @Expose
    private String dFname;
    @SerializedName("d_lname")
    @Expose
    private String dLname;
    @SerializedName("d_address")
    @Expose
    private String dAddress;
    @SerializedName("d_role")
    @Expose
    private String dRole;

    public String getDId() {
        return dId;
    }

    public void setDId(String dId) {
        this.dId = dId;
    }

    public String getDCity() {
        return dCity;
    }

    public void setDCity(String dCity) {
        this.dCity = dCity;
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

    public String getDRole() {
        return dRole;
    }

    public void setDRole(String dRole) {
        this.dRole = dRole;
    }

}
