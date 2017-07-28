package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EComplaintList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_ComplaintListInterface {
    @GET("/service/api/service/enggComplaintList")
    Call<EComplaintList> getJson(@Query("engg_id") String engg_id);
}
