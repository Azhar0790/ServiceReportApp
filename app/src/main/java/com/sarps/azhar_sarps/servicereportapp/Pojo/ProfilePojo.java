
package com.sarps.azhar_sarps.servicereportapp.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePojo {

    @SerializedName("message")
    @Expose
    private PMessagePojo message;

    public PMessagePojo getMessage() {
        return message;
    }

    public void setMessage(PMessagePojo message) {
        this.message = message;
    }

}
