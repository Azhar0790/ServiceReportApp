
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpareRequestStatusPojo {

    @SerializedName("sparereq")
    @Expose
    private String sparereq;
    @SerializedName("spare_req_status")
    @Expose
    private String spareReqStatus;

    public String getSparereq() {
        return sparereq;
    }

    public void setSparereq(String sparereq) {
        this.sparereq = sparereq;
    }

    public String getSpareReqStatus() {
        return spareReqStatus;
    }

    public void setSpareReqStatus(String spareReqStatus) {
        this.spareReqStatus = spareReqStatus;
    }

}
