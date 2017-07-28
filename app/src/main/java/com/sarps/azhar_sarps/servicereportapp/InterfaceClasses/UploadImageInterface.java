package com.sarps.azhar_sarps.servicereportapp.InterfaceClasses;

import com.sarps.azhar_sarps.servicereportapp.Pojo.CComplaintPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.SignatureUploadPojo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by azhar-sarps on 20-Jun-17.
 */

public interface UploadImageInterface {
    @Multipart
    @POST("/service/api/service/imageupload")
//    Call<SignatureUploadPojo> postData(@Part("cust_id") RequestBody cust_id, @Part("file") MultipartBody.Part file);
//    Call<SignatureUploadPojo> postData(@Part RequestBody cust_id,@Part MultipartBody.Part file);
    Call<SignatureUploadPojo> postData(@PartMap Map<String, RequestBody> params);
}
