
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

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
    @SerializedName("complaint date")
    @Expose
    private String complaintDate;
    @SerializedName("assign_engg_id")
    @Expose
    private String assignEnggId;
    @SerializedName("complaint_solve_st_datetime")
    @Expose
    private String complaintSolveStDatetime;
    @SerializedName("complaint_solve_end_datetime")
    @Expose
    private String complaintSolveEndDatetime;
    @SerializedName("user_role")
    @Expose
    private String userRole;
    @SerializedName("escalate_status")
    @Expose
    private String escalateStatus;
    @SerializedName("escalate_desc")
    @Expose
    private String escalateDesc;
    @SerializedName("pr_name")
    @Expose
    private String prName;
    @SerializedName("pr_modelno")
    @Expose
    private String prModelno;
    @SerializedName("pr_image")
    @Expose
    private String prImage;

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

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getAssignEnggId() {
        return assignEnggId;
    }

    public void setAssignEnggId(String assignEnggId) {
        this.assignEnggId = assignEnggId;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEscalateStatus() {
        return escalateStatus;
    }

    public void setEscalateStatus(String escalateStatus) {
        this.escalateStatus = escalateStatus;
    }

    public String getEscalateDesc() {
        return escalateDesc;
    }

    public void setEscalateDesc(String escalateDesc) {
        this.escalateDesc = escalateDesc;
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
