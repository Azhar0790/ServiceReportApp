package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_InsertServiceReportInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_StopWorktInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.UploadImageInterface;
import com.sarps.azhar_sarps.servicereportapp.MainActivity_Engineers;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.InsertServicePojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.SignatureUploadPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.UpdatetStatusCompletedPojo;
import com.sarps.azhar_sarps.servicereportapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignatureFragment extends Fragment {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    String work_done, running_hour, service_type, lue_oil_brand_grade, oil_pressure, water_temp, volt, observation, recommendation, customer_remark, complaint_id, custid;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_signature, container, false);
        work_done = getArguments().getString("work_done");
        running_hour = getArguments().getString("running_hour");
        service_type = getArguments().getString("service_type");
        lue_oil_brand_grade = getArguments().getString("lue_oil_brand_grade");
        oil_pressure = getArguments().getString("oil_pressure");
        water_temp = getArguments().getString("water_temp");
        volt = getArguments().getString("volt");
        observation = getArguments().getString("observation");
        recommendation = getArguments().getString("recommendation");
        customer_remark = getArguments().getString("customer_remark");
        complaint_id = getArguments().getString("complaint_id");
        custid = getArguments().getString("custid");


        mSignaturePad = (SignaturePad) rootView.findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(getActivity(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton = (Button) rootView.findViewById(R.id.clear_button);
        mSaveButton = (Button) rootView.findViewById(R.id.save_button);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
//                addJpgSignatureToGallery(signatureBitmap);
                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
                if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                    Toast.makeText(getActivity(), "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {

        boolean result = false;
        File photo = new File(getAlbumStorageDir("ServiceReport_Signatures"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
        try {
            saveBitmapToJPG(signature, photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanMediaFile(photo);
//        System.out.println("Photo1:-" + photo);
//        post_signature("2", photo);
        if (photo != null) {
            post_data(complaint_id, work_done, running_hour, service_type, lue_oil_brand_grade, oil_pressure, water_temp, volt, observation,
                    recommendation, customer_remark);
            post_signature(custid, photo);
        } else {
            Toast.makeText(getActivity(), "Please sign again", Toast.LENGTH_SHORT).show();
        }

        result = true;

        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void post_data(String cid, String work_done, String running_hour, String service_type, String lue_oil_brand_grade, String oil_pressure, String water_temp, String volt, String observation,
                          String recommendation, String customer_remark) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_InsertServiceReportInterface eInsertServiceReportInterface = retrofit.create(E_InsertServiceReportInterface.class);


        Call<InsertServicePojo> call = eInsertServiceReportInterface.postData(cid, work_done, running_hour, service_type, lue_oil_brand_grade, oil_pressure, water_temp, volt, observation, recommendation, customer_remark);
        call.enqueue(new Callback<InsertServicePojo>() {
            @Override
            public void onResponse(Call<InsertServicePojo> call, Response<InsertServicePojo> response) {
                InsertServicePojo insertServicePojo = response.body();
                if (insertServicePojo.getMessage().equals("Inserted Successfully")) {


                } else {
                    Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertServicePojo> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void post_signature(String cust_id, File file) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sarps.sarpstechnologies.com").addConverterFactory(GsonConverterFactory.create()).build();

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), cust_id);


        UploadImageInterface uploadImageInterface = retrofit.create(UploadImageInterface.class);
//            Call<SignatureUploadPojo> call = uploadImageInterface.postData(requestBody, body);
//            Part("cust_id") RequestBody cust_id, @Part("file") MultipartBody.Part file

        Map<String, RequestBody> map = new HashMap<>();
        map.put("cust_id", toRequestBody(cust_id));
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        map.put("file\"; filename=\"signature.png\"", fileBody);
        Call<SignatureUploadPojo> call = uploadImageInterface.postData(map);
        call.enqueue(new Callback<SignatureUploadPojo>() {
            @Override
            public void onResponse(Call<SignatureUploadPojo> call, Response<SignatureUploadPojo> response) {
                SignatureUploadPojo insertServicePojo = response.body();
                String message = insertServicePojo.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                get_Post_complete(complaint_id);

            }

            @Override
            public void onFailure(Call<SignatureUploadPojo> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public void get_Post_complete(String cid) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_StopWorktInterface eStopWorktInterface = retrofit.create(E_StopWorktInterface.class);


        Call<UpdatetStatusCompletedPojo> call = eStopWorktInterface.getJson(cid);
        call.enqueue(new Callback<UpdatetStatusCompletedPojo>() {
            @Override
            public void onResponse(Call<UpdatetStatusCompletedPojo> call, Response<UpdatetStatusCompletedPojo> response) {
                UpdatetStatusCompletedPojo insertServicePojo = response.body();
                if (insertServicePojo.getMessage().equals("Updated")) {
                    startActivity(new Intent(getActivity(), MainActivity_Engineers.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatetStatusCompletedPojo> call, Throwable t) {
                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
