
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ACityListPojo {

    @SerializedName("message")
    @Expose
    private List<ACityMessage> message = null;

    public List<ACityMessage> getMessage() {
        return message;
    }

    public void setMessage(List<ACityMessage> message) {
        this.message = message;
    }

}
