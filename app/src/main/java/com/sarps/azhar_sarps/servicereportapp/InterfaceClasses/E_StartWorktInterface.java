package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.EComplaintList;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ESparePartrequest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_StartWorktInterface {

    @GET("/service/api/service/updateStatusInprocess")
    Call<ESparePartrequest> getJson(@Query("c_id") String c_id);
}
