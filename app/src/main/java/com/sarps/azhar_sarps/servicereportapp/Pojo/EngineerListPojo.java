
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EngineerListPojo {

    @SerializedName("message")
    @Expose
    private List<EngineerListMessage> message = null;

    public List<EngineerListMessage> getMessage() {
        return message;
    }

    public void setMessage(List<EngineerListMessage> message) {
        this.message = message;
    }

}
