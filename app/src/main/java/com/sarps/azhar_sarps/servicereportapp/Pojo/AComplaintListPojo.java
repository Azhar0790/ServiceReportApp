
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AComplaintListPojo {

    @SerializedName("message")
    @Expose
    private List<AComplaintMessage> message = null;

    public List<AComplaintMessage> getMessage() {
        return message;
    }

    public void setMessage(List<AComplaintMessage> message) {
        this.message = message;
    }

}
