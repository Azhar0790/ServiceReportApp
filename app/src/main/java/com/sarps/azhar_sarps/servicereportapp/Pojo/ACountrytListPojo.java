
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ACountrytListPojo {

    @SerializedName("message")
    @Expose
    private List<ACountryMessage> message = null;

    public List<ACountryMessage> getMessage() {
        return message;
    }

    public void setMessage(List<ACountryMessage> message) {
        this.message = message;
    }

}
