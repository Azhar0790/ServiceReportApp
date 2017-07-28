
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  ECompalintMessage{

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
    @SerializedName("escalate_date")
    @Expose
    private String escalateDate;
    @SerializedName("sparepart_request")
    @Expose
    private String sparepartRequest;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("d_fname")
    @Expose
    private String dFname;
    @SerializedName("d_lname")
    @Expose
    private String dLname;
    @SerializedName("d_address")
    @Expose
    private String dAddress;
    @SerializedName("d_city")
    @Expose
    private String dCity;
    @SerializedName("pr_name")
    @Expose
    private String prName;
    @SerializedName("pr_modelno")
    @Expose
    private String prModelno;
    @SerializedName("enggfname")
    @Expose
    private String enggfname;
    @SerializedName("engglname")
    @Expose
    private String engglname;

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

    public String getEscalateDate() {
        return escalateDate;
    }

    public void setEscalateDate(String escalateDate) {
        this.escalateDate = escalateDate;
    }

    public String getSparepartRequest() {
        return sparepartRequest;
    }

    public void setSparepartRequest(String sparepartRequest) {
        this.sparepartRequest = sparepartRequest;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
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

    public String getDCity() {
        return dCity;
    }

    public void setDCity(String dCity) {
        this.dCity = dCity;
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

}
