
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DEngineerListPojo {

    @SerializedName("message")
    @Expose
    private List<DEngineerMessage> message = null;

    public List<DEngineerMessage> getMessage() {
        return message;
    }

    public void setMessage(List<DEngineerMessage> message) {
        this.message = message;
    }

}
