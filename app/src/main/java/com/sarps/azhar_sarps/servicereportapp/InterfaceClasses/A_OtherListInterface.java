package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.AdminMessageList;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AdminMsgListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CEscalatePojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 07-Jul-17.
 */

public interface A_OtherListInterface {
    @GET("/service/api/service/superOtherRequest")
    Call<AdminMsgListPojo> postDataAdmin(@Query("receiver_id") String receiver_id);

    @GET("/service/api/service/dealerOtherRequest")
    Call<AdminMsgListPojo> postDataDel(@Query("receiver_id") String receiver_id);


    @GET("/service/api/service/enggOtherRequest")
    Call<AdminMsgListPojo> postDataEng(@Query("receiver_id") String receiver_id);

    @GET("/service/api/service/custOtherRequest")
    Call<AdminMsgListPojo> postDataCust(@Query("receiver_id") String receiver_id);

}
