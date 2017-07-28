
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustListPojo {

    @SerializedName("message")
    @Expose
    private List<CustListMessage> message = null;

    public List<CustListMessage> getMessage() {
        return message;
    }

    public void setMessage(List<CustListMessage> message) {
        this.message = message;
    }

}
