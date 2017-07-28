package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.ESparePartrequest;
import com.sarps.azhar_sarps.servicereportapp.Pojo.InsertServicePojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface E_InsertServiceReportInterface {

    @POST("/service/api/service/insertServiceReport")
    @FormUrlEncoded
    Call<InsertServicePojo> postData(@Field("complaint_id") String complaint_id, @Field("work_done") String work_done,
                                     @Field("running_hour") String running_hour, @Field("service_type") String service_type,
                                     @Field("lue_oil_brand_grade") String lue_oil_brand_grade, @Field("oil_pressure") String oil_pressure,
                                     @Field("water_temp") String water_temp, @Field("volt") String volt, @Field("observation") String observation,
                                     @Field("recommendation") String recommendation, @Field("customer_remark") String customer_remark);
}
