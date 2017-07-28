
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ADealerListPojo {

    @SerializedName("message")
    @Expose
    private List<ADealerMessage> message = null;

    public List<ADealerMessage> getMessage() {
        return message;
    }

    public void setMessage(List<ADealerMessage> message) {
        this.message = message;
    }

}
