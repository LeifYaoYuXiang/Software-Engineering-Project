package com.example.testdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MapActivity extends AppCompatActivity {
    private LocationClient locationClient;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; //记录是否是第一次定位
    private MyLocationConfiguration.LocationMode locationMode; //当前定位模式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListner());
        SDKInitializer.initialize(getApplicationContext()); //初始化地图SDK
        setContentView(R.layout.activity_map);


        mMapView = findViewById(R.id.bmapview); //获取地图组件
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        requestLocation();
    }

    private void requestLocation() {
        initLocation();
        locationClient.start();
    }

    private void initLocation() {
        LocationClientOption locationOption = new LocationClientOption();

        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("bd09ll");
        locationOption.setScanSpan(1000);
        locationOption.setIsNeedAddress(true);
        locationOption.setIsNeedLocationDescribe(true);
        locationOption.setNeedDeviceDirect(false);
        locationOption.setLocationNotify(true);
        locationOption.setIgnoreKillProcess(true);
        locationOption.setIsNeedLocationDescribe(true);
        locationOption.setIsNeedLocationPoiList(true);
        locationOption.SetIgnoreCacheException(false);
        locationOption.setOpenGps(true);
        locationOption.setIsNeedAltitude(false);
        locationOption.setOpenAutoNotifyMode();
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);

        locationClient.setLocOption(locationOption);
    }

    private void navigateTo(BDLocation location){
        if (isFirstLoc){
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLoc = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());

        MyLocationData locationData = builder.build();
        mBaiduMap.setMyLocationData(locationData);
    }
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        mMapView = null;
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mMapView = null;
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        mBaiduMap.setBaiduHeatMapEnabled(true);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mBaiduMap.setBaiduHeatMapEnabled(false);
        super.onStop();

    }


    public class MyLocationListner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();

            double longitude = location.getLongitude();

            float radius = location.getRadius();

            String coorType = location.getCoorType();

            int errorCode = location.getLocType();

            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(location);
            }

        }
    }
}


