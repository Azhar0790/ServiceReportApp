
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ECompleteMessage {

    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("pr_id")
    @Expose
    private String prId;
    @SerializedName("complaint_desc")
    @Expose
    private String complaintDesc;
    @SerializedName("complaint_status")
    @Expose
    private String complaintStatus;
    @SerializedName("complaint_solve_st_datetime")
    @Expose
    private String complaintSolveStDatetime;
    @SerializedName("complaint_solve_end_datetime")
    @Expose
    private String complaintSolveEndDatetime;
    @SerializedName("custfname")
    @Expose
    private String custfname;
    @SerializedName("custlname")
    @Expose
    private String custlname;
    @SerializedName("d_city")
    @Expose
    private String dCity;

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public void setComplaintDesc(String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaintSolveStDatetime() {
        return complaintSolveStDatetime;
    }

    public void setComplaintSolveStDatetime(String complaintSolveStDatetime) {
        this.complaintSolveStDatetime = complaintSolveStDatetime;
    }

    public String getComplaintSolveEndDatetime() {
        return complaintSolveEndDatetime;
    }

    public void setComplaintSolveEndDatetime(String complaintSolveEndDatetime) {
        this.complaintSolveEndDatetime = complaintSolveEndDatetime;
    }

    public String getCustfname() {
        return custfname;
    }

    public void setCustfname(String custfname) {
        this.custfname = custfname;
    }

    public String getCustlname() {
        return custlname;
    }

    public void setCustlname(String custlname) {
        this.custlname = custlname;
    }

    public String getDCity() {
        return dCity;
    }

    public void setDCity(String dCity) {
        this.dCity = dCity;
    }
}
