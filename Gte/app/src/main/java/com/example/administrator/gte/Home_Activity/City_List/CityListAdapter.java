package com.example.administrator.gte.Home_Activity.City_List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.gte.R;

import java.util.List;

/**
 * Created by Administrator on 2015/9/5.
 */
public class CityListAdapter extends ArrayAdapter<City>{
    private int resourceId;



    public CityListAdapter(Context context,int textViewResourceId,List<City> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        City city=getItem(position);
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else {
            view=convertView;

        }
        TextView cityname = (TextView) view.findViewById(R.id.city_name);
        cityname.setText(city.getName());
        return view;
    }

} 