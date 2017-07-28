
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DGetComplaintRecordPojo {

    @SerializedName("message")
    @Expose
    private List<DGetComplaintMessage> message = null;

    public List<DGetComplaintMessage> getMessage() {
        return message;
    }

    public void setMessage(List<DGetComplaintMessage> message) {
        this.message = message;
    }

}
