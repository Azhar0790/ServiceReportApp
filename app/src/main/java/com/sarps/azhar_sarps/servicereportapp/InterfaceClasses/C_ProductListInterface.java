package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CProductListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.LoginPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface C_ProductListInterface {
    @GET("/service/api/service/custProductlist")
    Call<CProductListPojo> getJson(@Query("cust_id") String id);
}
