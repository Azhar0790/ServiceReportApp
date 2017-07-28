package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CProductListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.SpareRequestStatusPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_SpareStatusRequest {
    @GET("/service/api/service/viewSparepart")
    Call<SpareRequestStatusPojo> getJson(@Query("c_id") String c_id);
}
