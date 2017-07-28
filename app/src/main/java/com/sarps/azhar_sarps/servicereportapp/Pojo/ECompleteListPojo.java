
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ECompleteListPojo {

    @SerializedName("message")
    @Expose
    private List<ECompleteMessage> message = null;

    public List<ECompleteMessage> getMessage() {
        return message;
    }

    public void setMessage(List<ECompleteMessage> message) {
        this.message = message;
    }

}
