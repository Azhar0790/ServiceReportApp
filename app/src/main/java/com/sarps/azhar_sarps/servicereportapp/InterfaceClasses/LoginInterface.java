package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.ApproveDeclineSPPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.LoginPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface LoginInterface {
    @POST("service/api/service/loginuser")
    @FormUrlEncoded
    Call<LoginPojo> getJson(@Field("emailid") String email, @Field("password") String password, @Field("u_role") String u_role, @Field("token") String token);

    @POST("service/api/service/forgetpassmail")
    @FormUrlEncoded
    Call<ApproveDeclineSPPojo> getJson(@Field("sender_mail") String email);
}
