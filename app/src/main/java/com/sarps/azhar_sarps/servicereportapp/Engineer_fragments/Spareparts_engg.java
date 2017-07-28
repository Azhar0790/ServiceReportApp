package com.sarps.azhar_sarps.servicereportapp.Engineer_fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.Dealer_fragments.*;
import com.sarps.azhar_sarps.servicereportapp.Dealer_fragments.ComplaintListFragment;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.D_ComplaintListInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_DealerNameInterface;
import com.sarps.azhar_sarps.servicereportapp.InterfaceClasses.E_SparePartRequestInterface;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.SharedPrefClass;
import com.sarps.azhar_sarps.servicereportapp.MyUtils.WebserviceAPI;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DComplaintListPojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DealerNameMessage;
import com.sarps.azhar_sarps.servicereportapp.Pojo.DealerNamePojo;
import com.sarps.azhar_sarps.servicereportapp.Pojo.ESparePartrequest;
import com.sarps.azhar_sarps.servicereportapp.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sarps-preeti on 6/19/2017.
 */

public class Spareparts_engg extends Fragment implements OnItemSelectedListener {
    Context context;
    @BindView(R.id.btn_spare_request)
    Button btn_spare_request;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.cb_progress_bar)
    fr.castorflex.android.circularprogressbar.CircularProgressBar circularProgressBar;
    private SharedPrefClass mSharedPrefClass = null;
    String user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.engineer_fragment_spare_parts, container, false);
        ButterKnife.bind(this, rootView);
        mSharedPrefClass = new SharedPrefClass(getActivity());
        user_id = mSharedPrefClass.getUserId();

        final String cid = getArguments().getString("cid");
        btn_spare_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new Detail_issue();
//                Bundle bundle=new Bundle();
//                bundle.putString("cid",cid);
//                fragment.setArguments(bundle);
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.container, fragment);
//                ft.commit();
                circularProgressBar.setVisibility(View.VISIBLE);
                getDealerName(user_id,cid, et_description.getText().toString().trim());
            }
        });

        // Spinner element
        Spinner spinner = (Spinner) rootView.findViewById(R.id.sparepartsitems);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void getDealerName(final String user_id, final String cid, final String spare_request) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_DealerNameInterface eDealerNameInterface = retrofit.create(E_DealerNameInterface.class);
        Call<DealerNamePojo> call = eDealerNameInterface.getJson(user_id);
        call.enqueue(new Callback<DealerNamePojo>() {
            @Override
            public void onResponse(Call<DealerNamePojo> call, Response<DealerNamePojo> response) {
                DealerNamePojo dealerNamePojo = response.body();

                String deealer_id = dealerNamePojo.getMessage().get(0).getDealerId();
                post_data(cid, spare_request, deealer_id);


            }

            @Override
            public void onFailure(Call<DealerNamePojo> call, Throwable t) {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void post_data(String cid, String spare_request, String dealer_id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebserviceAPI.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        E_SparePartRequestInterface eSparePartRequestInterface = retrofit.create(E_SparePartRequestInterface.class);
        Call<ESparePartrequest> call = eSparePartRequestInterface.postData(cid, spare_request, dealer_id);
        call.enqueue(new Callback<ESparePartrequest>() {
            @Override
            public void onResponse(Call<ESparePartrequest> call, Response<ESparePartrequest> response) {
                circularProgressBar.setVisibility(View.GONE);
                ESparePartrequest dComplaintListPojo = response.body();
                if (dComplaintListPojo.getMessage().equals("Updated")) {
                    Toast.makeText(getActivity(), "Request Send Successfully", Toast.LENGTH_SHORT).show();
                    et_description.setText("");
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ESparePartrequest> call, Throwable t) {
                circularProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

}