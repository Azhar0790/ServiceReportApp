
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminMsgListPojo {

    @SerializedName("message")
    @Expose
    private List<AdminMessageList> message = null;

    public List<AdminMessageList> getMessage() {
        return message;
    }

    public void setMessage(List<AdminMessageList> message) {
        this.message = message;
    }

}
