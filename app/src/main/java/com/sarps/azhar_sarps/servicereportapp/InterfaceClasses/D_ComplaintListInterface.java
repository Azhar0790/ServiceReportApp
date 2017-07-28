package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintMessage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface D_ComplaintListInterface {
    @GET("/service/api/service/dealerComplaintList")
    Call<DComplaintListPojo> getJson(@Query("dealer_id") String dealer_id);
}
