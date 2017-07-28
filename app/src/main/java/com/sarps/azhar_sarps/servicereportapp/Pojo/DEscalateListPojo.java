
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DEscalateListPojo {

    @SerializedName("message")
    @Expose
    private List<DEscListMessage> message = null;

    public List<DEscListMessage> getMessage() {
        return message;
    }

    public void setMessage(List<DEscListMessage> message) {
        this.message = message;
    }

}
