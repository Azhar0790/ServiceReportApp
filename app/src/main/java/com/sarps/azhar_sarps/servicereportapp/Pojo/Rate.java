
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rate {

    @SerializedName("as_id")
    @Expose
    private String asId;
    @SerializedName("as_cust_id")
    @Expose
    private String asCustId;
    @SerializedName("as_pr_id")
    @Expose
    private String asPrId;
    @SerializedName("pr_id")
    @Expose
    private String prId;
    @SerializedName("pr_name")
    @Expose
    private String prName;
    @SerializedName("pr_modelno")
    @Expose
    private String prModelno;
    @SerializedName("pr_image")
    @Expose
    private String prImage;

    public String getAsId() {
        return asId;
    }

    public void setAsId(String asId) {
        this.asId = asId;
    }

    public String getAsCustId() {
        return asCustId;
    }

    public void setAsCustId(String asCustId) {
        this.asCustId = asCustId;
    }

    public String getAsPrId() {
        return asPrId;
    }

    public void setAsPrId(String asPrId) {
        this.asPrId = asPrId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getPrModelno() {
        return prModelno;
    }

    public void setPrModelno(String prModelno) {
        this.prModelno = prModelno;
    }

    public String getPrImage() {
        return prImage;
    }

    public void setPrImage(String prImage) {
        this.prImage = prImage;
    }

}
