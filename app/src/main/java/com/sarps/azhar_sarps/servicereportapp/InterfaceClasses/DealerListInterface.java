package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.ACityListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ACountrytListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ADealerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.AStateListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CustListMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.CustListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DealerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EngineerListMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.EngineerListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.OInsertMsgPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface DealerListInterface {

    @GET("/service/api/service/alldealerList")
    Call<DealerListPojo> getJsonDealers();

    @GET("/service/api/service/allengineerList")
    Call<EngineerListPojo> getJsonEng();

    @GET("/service/api/service/custListofDealer")
    Call<CustListPojo> getJsonCust(@Query("dealer_id") String dealer_id);

    @POST("/service/api/service/createComplaint")
    @FormUrlEncoded
    Call<CComplaintPojo> postData(@Field("cust_id") String cust_id, @Field("pr_id") String pr_id, @Field("complaint_desc") String complaint_desc, @Field("user_role") String user_role);


    @POST("/service/api/service/insertOtherRequestEngg")
    @FormUrlEncoded
    Call<OInsertMsgPojo> postData(@Field("engg_id") String engg_id, @Field("receiver") String receiver, @Field("msg") String msg);

    @POST("/service/api/service/sendotherfromdealer")
    @FormUrlEncoded
    Call<OInsertMsgPojo> postData_E(@Field("sender_id") String dealer_id, @Field("receiver") String receiver, @Field("sender_msg") String msg,@Field("receiver_id") String receiver_id,@Field("sender_role") String sender_role);

    @POST("/service/api/service/sendotherfromdealer")
    @FormUrlEncoded
    Call<OInsertMsgPojo> post_reply(@Field("sender_id") String dealer_id, @Field("receiver") String receiver, @Field("sender_msg") String msg,@Field("sender_role") String sender_role);

    @GET("/service/api/service/dealerList")
    Call<ADealerListPojo> getJson_dealers(@Query("city_id") String city_id);
}
