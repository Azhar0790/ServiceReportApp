
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerNamePojo {

    @SerializedName("message")
    @Expose
    private List<DealerNameMessage> message = null;

    public List<DealerNameMessage> getMessage() {
        return message;
    }

    public void setMessage(List<DealerNameMessage> message) {
        this.message = message;
    }

}
