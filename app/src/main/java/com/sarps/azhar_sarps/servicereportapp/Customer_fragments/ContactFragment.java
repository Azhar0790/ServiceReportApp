package com.sarps.azhar_sarps.servicereportapp.Customer_fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.azhar_sarps.servicereportapp.MainActivity_Engineers;
import com.sarps.azhar_sarps.servicereportapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactFragment extends Fragment {

    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_contactno)
    TextView tv_contactno;
    public static final String[] PERMISSION_ALL = {
            Manifest.permission.CALL_PHONE,
    };
    public static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c_contact_us, container, false);
        ButterKnife.bind(this, rootView);
        tv_contactno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentapiVersion = Build.VERSION.SDK_INT;
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSION_ALL, PERMISSION_REQUEST_CODE);
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + ("08655880666")));
                try {
                    getActivity().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return rootView;
    }
}
