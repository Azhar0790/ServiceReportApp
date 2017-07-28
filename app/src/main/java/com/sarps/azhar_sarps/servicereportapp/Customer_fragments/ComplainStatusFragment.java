/*
 * Copyright (C) 2015 Brent Marriott
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.hookedonplay.decoviewlib.events.DecoEvent.EventType;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.C_ComplaintStatusInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ComplaintStatusPojo;
import com.sarps.azhar_sarps.servicereportapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComplainStatusFragment extends SampleFragment {
    private int mSeries1Index;
    String msg, c_id;
    TextView textPercent;

    public ComplainStatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample_generic, container, false);
        c_id = getArguments().getString("c_id");
        getStatusProgress(c_id);
        System.out.println("c_id :- " + c_id);
        return view;
    }

    @Override
    protected void createTracks() {
        setDemoFinished(false);
        final DecoView decoView = getDecoView();
        final View view = getView();
        if (decoView == null || view == null) {
            return;
        }
        decoView.deleteAll();
        decoView.configureAngles(280, 0);
        final float seriesMax = 50f;

        decoView.addSeries(new SeriesItem.Builder(Color.argb(255, 64, 255, 64), Color.argb(255, 255, 0, 0))
                .setRange(0, seriesMax, seriesMax)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(50f))
                .build());

        decoView.addSeries(new SeriesItem.Builder(Color.argb(255, 0, 0, 0))
                .setRange(0, seriesMax, seriesMax)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(24f))
                .build());

        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 255, 64), Color.argb(255, 255, 0, 0))
                .setRange(0, seriesMax, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(50f))
                .setCapRounded(true)
                .setShowPointWhenEmpty(true)
                .build();

        mSeries1Index = decoView.addSeries(seriesItem1);
        textPercent = (TextView) view.findViewById(R.id.textPercentage);
        textPercent.setText("");
        addProgressListener(seriesItem1, textPercent, "%.0f%%");
    }

    public void getStatusProgress(String c_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        C_ComplaintStatusInterface c_complaintStatusInterface = retrofit.create(C_ComplaintStatusInterface.class);
        Call<ComplaintStatusPojo> call = c_complaintStatusInterface.getJson(c_id);
        call.enqueue(new Callback<ComplaintStatusPojo>() {
            @Override
            public void onResponse(Call<ComplaintStatusPojo> call, Response<ComplaintStatusPojo> response) {
                ComplaintStatusPojo complaintStatusPojo = response.body();

                msg = complaintStatusPojo.getMessage();
                System.out.println("message :- " + msg);
                if (msg.equals("No Result")) {
                    textPercent.setText("No Status");
                    textPercent.setTextSize(15);
                } else {
                    setupEvents(msg);
                }
            }

            @Override
            public void onFailure(Call<ComplaintStatusPojo> call, Throwable t) {

            }
        });

    }

    @Override
    protected void setupEvents(String msg) {
        final DecoView decoView = getDecoView();
        if (decoView == null || decoView.isEmpty()) {
            throw new IllegalStateException("Unable to add events to empty DecoView");
        }
        decoView.executeReset();
        decoView.addEvent(new DecoEvent.Builder(EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(1000)
                .build());

        System.out.println("msg :- " + msg);
        try {
            if (msg != null) {
                if (msg.equals("No Result")) {

                } else {
                    int status_msg = Integer.parseInt(msg);
                    int final_percent = status_msg / 2;
                    decoView.addEvent(new DecoEvent.Builder(final_percent).setIndex(mSeries1Index).setDelay(3300).build());
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }


}