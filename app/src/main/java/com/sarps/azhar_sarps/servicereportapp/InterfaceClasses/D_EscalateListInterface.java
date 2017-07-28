package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalateListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DEscalateListPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface D_EscalateListInterface {
    @GET("/service/api/service/escalateDealerList")
    Call<DEscalateListPojo> getJson(@Query("dealer_id") String id);
}
