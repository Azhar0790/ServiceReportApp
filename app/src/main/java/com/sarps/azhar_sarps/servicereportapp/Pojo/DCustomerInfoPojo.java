
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DCustomerInfoPojo {

    @SerializedName("message")
    @Expose
    private DCustomerMessage message;

    public DCustomerMessage getMessage() {
        return message;
    }

    public void setMessage(DCustomerMessage message) {
        this.message = message;
    }

}
