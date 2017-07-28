package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.ACityListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ACountrytListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ADealerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AStateListPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface A_DealerListInterface {
    @GET("/service/api/service/countryList")
    Call<ACountrytListPojo> getJson();

    @GET("/service/api/service/stateList")
    Call<AStateListPojo> getJson(@Query("c_id") String c_id);

    @GET("/service/api/service/cityList")
    Call<ACityListPojo> getJson_city(@Query("s_id") String s_id);

    @GET("/service/api/service/dealerList")
    Call<ADealerListPojo> getJson_dealers(@Query("city_id") String city_id);
}
