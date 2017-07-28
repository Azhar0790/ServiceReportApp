package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DCustomerInfoPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface D_CustomerInfoInterface {
    @GET("/service/api/service/getCustrow")
    Call<DCustomerInfoPojo> getJson(@Query("cust_id") String cust_id);
}
