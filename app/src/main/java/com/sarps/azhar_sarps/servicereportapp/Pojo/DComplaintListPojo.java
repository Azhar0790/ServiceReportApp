
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DComplaintListPojo {

    @SerializedName("message")
    @Expose
    private List<DComplaintMessage> message = null;

    public List<DComplaintMessage> getMessage() {
        return message;
    }

    public void setMessage(List<DComplaintMessage> message) {
        this.message = message;
    }

}
