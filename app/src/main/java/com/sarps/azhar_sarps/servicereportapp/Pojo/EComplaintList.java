
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EComplaintList {

    @SerializedName("message")
    @Expose
    private List<ECompalintMessage> message = null;

    public List<ECompalintMessage> getMessage() {
        return message;
    }

    public void setMessage(List<ECompalintMessage> message) {
        this.message = message;
    }

}
