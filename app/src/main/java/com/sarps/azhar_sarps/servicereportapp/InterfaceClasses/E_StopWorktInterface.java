package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.ESparePartrequest;
import com.sarps.azhar_sarps.servicereportapp.Pojo.UpdatetStatusCompletedPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_StopWorktInterface {

    @GET("/service/api/service/updatetStatusCompleted")
    Call<UpdatetStatusCompletedPojo> getJson(@Query("c_id") String c_id);
}
