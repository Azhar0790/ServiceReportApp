package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DAssignedEngineerPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface D_AssignedEngInterface {
    @POST("/service/api/service/assignedEngineer")
    @FormUrlEncoded
    Call<DAssignedEngineerPojo> postData(@Field("c_id") String c_id, @Field("engg_id") String engg_id);
}
