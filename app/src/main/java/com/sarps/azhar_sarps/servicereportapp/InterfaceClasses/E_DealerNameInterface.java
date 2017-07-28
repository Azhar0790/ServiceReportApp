package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.DealerNamePojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.UpdatetStatusCompletedPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_DealerNameInterface {

    @GET("/service/api/service/getDealername")
    Call<DealerNamePojo> getJson(@Query("engg_id") String engg_id);
}
