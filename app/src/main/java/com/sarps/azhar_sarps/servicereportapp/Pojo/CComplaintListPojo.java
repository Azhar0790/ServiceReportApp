
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CComplaintListPojo {

    @SerializedName("complaintlist")
    @Expose
    private List<Complaintlist> complaintlist = null;

    public List<Complaintlist> getComplaintlist() {
        return complaintlist;
    }

    public void setComplaintlist(List<Complaintlist> complaintlist) {
        this.complaintlist = complaintlist;
    }

}
