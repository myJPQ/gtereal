package com.example.administrator.gte.Home_Activity.City_List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.android.common.logging.Log;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.administrator.gte.Home_Activity.HomeActivity;
import com.example.administrator.gte.R;
import com.example.administrator.gte.newview.IndexableListView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/9/5.
 */
public class CityList_Activity extends Activity{
    private List<City> cityList=new ArrayList<City>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.city_listlayout);
        initCities();
        CityListAdapter adapter=new CityListAdapter(CityList_Activity.this,
                R.layout.city_item,cityList);
        final IndexableListView listView=(IndexableListView)findViewById((R.id.city_list));
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                City city=cityList.get(position);
                String text=city.getName();
                Pattern p =Pattern.compile("[a-zA-Z]");
                Matcher m = p.matcher(text);

                if(id==0){

                    return;
                }else if(m.matches()){

                    return;
                }else {
                    Intent intent=new Intent();
                    intent.putExtra("city_name",text);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });

    }

    private void initCities(){
        Resources res =getResources();
        String[]  cities=res.getStringArray(R.array.city_description_list);

        for(int j=0;j<cities.length;j++){
            City city=new City(cities[j]);
            cityList.add(city);
        }
    }



}
