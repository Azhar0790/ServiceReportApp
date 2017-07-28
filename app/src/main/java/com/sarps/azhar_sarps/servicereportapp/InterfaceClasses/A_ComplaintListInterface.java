package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintListPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface A_ComplaintListInterface {
    @GET("/service/api/service/superComplaintList")
    Call<AComplaintListPojo> getJson();
}
