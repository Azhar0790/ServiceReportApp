package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalateListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface C_EscalateListInterface {
    @GET("/service/api/service/escalateList")
    Call<CEscalateListPojo> getJson(@Query("cust_id") String id);
}
