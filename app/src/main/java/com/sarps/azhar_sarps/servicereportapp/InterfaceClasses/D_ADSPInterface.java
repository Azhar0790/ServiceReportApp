package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.ApproveDeclineSPPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface D_ADSPInterface {
    @POST("/service/api/service/approvedeclineSparepartreq")
    @FormUrlEncoded
    Call<ApproveDeclineSPPojo> postData(@Field("c_id") String c_id, @Field("req_status") String req_status);
}
