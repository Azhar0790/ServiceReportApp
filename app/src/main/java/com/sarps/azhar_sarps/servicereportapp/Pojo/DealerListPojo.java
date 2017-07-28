
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerListPojo {

    @SerializedName("message")
    @Expose
    private List<DealerListMessage> message = null;

    public List<DealerListMessage> getMessage() {
        return message;
    }

    public void setMessage(List<DealerListMessage> message) {
        this.message = message;
    }

}
