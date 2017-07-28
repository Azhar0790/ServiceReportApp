package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ComplaintStatusPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface C_ComplaintStatusInterface {
    @GET("/service/api/service/showProgress")
    Call<ComplaintStatusPojo> getJson(@Query("c_id") String id);
}
