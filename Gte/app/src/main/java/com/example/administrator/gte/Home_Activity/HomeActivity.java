package com.example.administrator.gte.Home_Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.common.logging.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.example.administrator.gte.Home_Activity.City_List.CityList_Activity;
import com.example.administrator.gte.R;

public class HomeActivity extends Activity implements OnGetGeoCoderResultListener {
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListenner();
    public BaiduMap mBaiduMap;
    private Context mContext;
    private MapView mMapview;
    private BDLocation location;
    private boolean isFirstLocate = true;

    private Button eCity;
    private Button eEnter;
    private PopupWindow popupWindow = null;
    private boolean[] checks;
    private boolean isfirstcheckeded = true;
    GeoCoder mSearch = null;


    //private PoiSearch mPoiSearch = null;
    //private SuggestionSearch mSuggestionSearch = null;
    //private ArrayAdapter<String> sugAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        SDKInitializer.initialize(mContext);
        mLocationClient = new LocationClient(mContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_layout);
        mMapview = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapview.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient.registerLocationListener(myListener);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);


        eCity = (Button) findViewById(R.id.e_city);
        initLocation();
        //定位按钮
        ImageView lbs = (ImageView) findViewById(R.id.lbs);
        lbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLocationClient.start();
                location = mLocationClient.getLastKnownLocation();
                if (location != null) {
                    String describe1 = location.getLocationDescribe();
                    FindLocation(location, 1);
                    String cityname = null;
                    cityname = location.getAddrStr();
                    Toast.makeText(HomeActivity.this, cityname, Toast.LENGTH_SHORT)
                            .show();
                    eCity.setText(location.getCity());

                }

            }

        });


        eCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CityList_Activity.class);
                startActivityForResult(intent, 1);
            }
        });
        final EditText eSearch = (EditText) findViewById(R.id.e_search);
        eSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.
                        getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

//do something;
                    mSearch.geocode(new GeoCodeOption().city(eCity.getText().toString()).
                            address(eSearch.getText().toString()));
                    return true;
                }
                return false;
            }
        });
        checks = new boolean[6];
        if (isfirstcheckeded)
            for (int i = 0; i < 6; i++) {
                checks[i] = false;
                isfirstcheckeded = false;
            }

        eEnter = (Button) findViewById(R.id.e_enter
        );
        eEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {
                    initmPopupWindowView();
                    popupWindow.showAsDropDown(v);
                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("city_name");
                    Log.i("cityname", returnData);
                    eCity.setText(returnData);
                    mSearch.geocode(new GeoCodeOption().city(
                            returnData).address(
                            returnData));

                }
                break;
            default:
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        mSearch.destroy();


        //mPoiSearch.destroy();
        //mSuggestionSearch.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void FindLocation(BDLocation location, int i) {
        switch (i) {
            case 1: {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

                MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 16f);

                mBaiduMap.animateMapStatus(update);
            }
            break;
            default:
        }

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(location.getDirection())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        mBaiduMap.setMyLocationData(locData);
    }


    //定位监听函数
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null)
                return;
            FindLocation(location, 2);

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    public void onGetPoiDetailResult(PoiDetailResult result) {
        //获取Place详情页检索结果
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(HomeActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(HomeActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void initmPopupWindowView() {

        View customView = getLayoutInflater().inflate(R.layout.popview, null, false);
        popupWindow = new PopupWindow(customView, AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT);
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

                return false;
            }
        });


        //在这里实现筛选功能
        final CheckBox check1 = (CheckBox) customView.findViewById(R.id.my_plan);
        final CheckBox check2 = (CheckBox) customView.findViewById(R.id.recommend);
        final CheckBox check3 = (CheckBox) customView.findViewById(R.id.morning_tea);
        final CheckBox check4 = (CheckBox) customView.findViewById(R.id.lunch);
        final CheckBox check5 = (CheckBox) customView.findViewById(R.id.snack);
        final CheckBox check6 = (CheckBox) customView.findViewById(R.id.mid_snack);

        check1.setChecked(checks[0]);
        check2.setChecked(checks[1]);
        check3.setChecked(checks[2]);
        check4.setChecked(checks[3]);
        check5.setChecked(checks[4]);
        check6.setChecked(checks[5]);


        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[0] = check1.isChecked();
            }
        });
        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[1] = check2.isChecked();
            }
        });
        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[2] = check3.isChecked();
            }
        });
        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[3] = check4.isChecked();
            }
        });
        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[4] = check5.isChecked();
            }
        });
        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checks[5] = check6.isChecked();
            }
        });

    }

    //地理编码
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {

            Toast.makeText(HomeActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));


    }

    //反地理编码
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {

        }


    }


}











	


