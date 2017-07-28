package com.sarps.azhar_sarps.servicereportapp.Custom_Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sarps.azhar_sarps.servicereportapp.Pojo.Pager_Item;
import com.sarps.azhar_sarps.servicereportapp.R;

import java.util.ArrayList;

/**
 * Created by azhar-sarps on 27-Jul-17.
 */

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<Pager_Item> list;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context, ArrayList<Pager_Item> list) {
        mContext = context;
        this.list = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(list.get(position).getImage());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}