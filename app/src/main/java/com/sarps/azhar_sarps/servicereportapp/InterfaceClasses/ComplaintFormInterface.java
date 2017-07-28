package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.LoginPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface ComplaintFormInterface {
    @POST("/service/api/service/createComplaint")
    @FormUrlEncoded
    Call<CComplaintPojo> postData(@Field("cust_id") String cust_id, @Field("pr_id") String pr_id, @Field("complaint_desc") String complaint_desc, @Field("user_role") String user_role);
}
