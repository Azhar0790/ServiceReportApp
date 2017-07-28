package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ProfilePojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface ProfileInterface {
    @POST("/service/api/service/showProfile")
    @FormUrlEncoded
    Call<ProfilePojo> postData(@Field("user_id") String user_id, @Field("user_role") String user_role);
}
