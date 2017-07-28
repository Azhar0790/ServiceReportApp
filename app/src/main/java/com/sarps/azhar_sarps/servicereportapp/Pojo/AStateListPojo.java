
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AStateListPojo {

    @SerializedName("message")
    @Expose
    private List<AStateMessage> message = null;

    public List<AStateMessage> getMessage() {
        return message;
    }

    public void setMessage(List<AStateMessage> message) {
        this.message = message;
    }

}
