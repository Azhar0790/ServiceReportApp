package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.EComplaintList;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ECompleteListPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_CompleteListInterface {
    @GET("/service/api/service/enggCompleteList")
    Call<ECompleteListPojo> getJson(@Query("engg_id") String engg_id);
}
