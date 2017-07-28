package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.AComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DGetComplaintMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DGetComplaintRecordPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 12-Jul-17.
 */

public interface D_ComplaintRecord {
    @GET("/service/api/service/getComplaintrecord")
    Call<DGetComplaintRecordPojo> getJson(@Query("c_id") String cid);
}
