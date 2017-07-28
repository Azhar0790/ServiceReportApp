package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface C_EscalateFormInterface {
    @POST("/service/api/service/escalateRequest")
    @FormUrlEncoded
    Call<CEscalatePojo> postData(@Field("c_id") String c_id, @Field("escalate_desc") String escalate_desc,@Field("cust_id") String cust_id);
}
