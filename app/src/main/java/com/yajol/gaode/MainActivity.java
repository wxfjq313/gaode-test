package com.yajol.gaode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    private static final String TAG = "MainActivity";
    MapView mMapView = null;
    TextView resultTv = null;
    AMap aMap;
    private UiSettings uiSettings;
    private AMapLocationClient mlocationClient;
    //    private OnLocationChangedListener mListener;
    // 定位配置选项
    private AMapLocationClientOption mLocationOption;
    private LatLng marrkerLatLng;
    private LatLng locationLatLng;
    //    private LatLng marrkerLatLng ;
    private Marker marker;
    private com.amap.api.maps2d.model.Polygon polygon_A;
    private boolean isToCurrentPosition = true;
    private GeocodeSearch geocodeSearch;
    private com.yajol.gaode.Polygon polygon1;
    private com.amap.api.maps2d.model.Polygon polygon_E;
    private com.amap.api.maps2d.model.Polygon polygon_C;

    //    lat/lng: (39.71924042759355,116.58192515373231)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Test().test();


        polygon1 = com.yajol.gaode.Polygon.Builder()

                //区域 测试 凹
//                .addVertex(new Point(39.9583581374, 116.8136749899))
//                .addVertex(new Point(39.9591465703, 116.8255276642))
//                .addVertex(new Point(39.9492669674, 116.8262848109))
//                .addVertex(new Point(39.9534110000, 116.8208300000))
//                .addVertex(new Point(39.9503190000, 116.8181690000))
//                .addVertex(new Point(39.9486294525, 116.8143971077))

                // 区域A
//                .addVertex(new Point(39.959151, 116.825533))
//                .addVertex(new Point(39.959578, 116.832313))
//                .addVertex(new Point(39.951498, 116.833504))
//                .addVertex(new Point(39.951428, 116.832141))
//                .addVertex(new Point(39.952958, 116.831862))
//                .addVertex(new Point(39.9529, 116.830945))
//                .addVertex(new Point(39.953295, 116.830875))
//                .addVertex(new Point(39.952904, 116.826015))


//        latLngs_C.add(new LatLng(40.026252, 116.754525));//左上
//        latLngs_C.add(new LatLng(40.026252, 116.918418));//右上
//        latLngs_C.add(new LatLng(39.881965, 116.918418));//右下
//        latLngs_C.add(new LatLng(39.881965, 116.754525));//左下

                //quyuC
                .addVertex(new Point(40.026252, 116.754525))
                .addVertex(new Point(40.026252, 116.918418))
                .addVertex(new Point(39.881965, 116.918418))
                .addVertex(new Point(39.881965, 116.754525))


                .build();

        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
//        resultTv = (TextView) findViewById(R.id.result_tv);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        assert mMapView != null;
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        //画文字
//        TextOptions textOptions = new TextOptions();
//        textOptions.text("文字").backgroundColor(Color.argb(34,34,34,34))
//                .fontSize(23).rotate(90);
//        textOptions.visible(true).position(aMap.getCameraPosition().target);
//        aMap.addText(textOptions);

        uiSettings = aMap.getUiSettings();
//        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setMyLocationButtonEnabled(true);

        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
//        //设置  定位 样式
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_drop_black_24dp));
//        myLocationStyle.anchor(0, 0);
//        aMap.setMyLocationStyle(myLocationStyle);

        //逆地理编码
        geocodeSearch = new GeocodeSearch(getApplicationContext());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            //            根据给定的经纬度和最大结果数返回逆地理编码的结果列表。
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int resultID) {
                String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                Log.e("formatAddress", "formatAddress:" + formatAddress);
                Log.e("formatAddress", "rCode:" + resultID);
            }

            //            根据给定的地理名称和查询城市，返回地理编码的结果列表。
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int resultID) {

            }
        });


        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
//                Log.i(TAG, "过程中  经纬度：" + cameraPosition.target);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                Log.i(TAG, "结束时  经纬度：" + cameraPosition.target);
//              LatLonPoint latLonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
//              RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
//              geocodeSearch.getFromLocationAsyn(query);
                Point point = new Point(cameraPosition.target.latitude, cameraPosition.target.longitude);
                LatLng latLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                Log.i(TAG, " The Point：" + cameraPosition.target.latitude + "," + cameraPosition.target.longitude
                        + (polygon1.contains(point) ? "" : " not") + " in Polygon1");

                Log.e(TAG, "C The Point：" + cameraPosition.target.latitude + "," + cameraPosition.target.longitude
                        + (polygon_C.contains(latLng) ? "" : " not") + " in Polygon_C");

                geocoderSearch(cameraPosition.target);
                cloudSearch(cameraPosition.target);

            }
        });


//        //折线
        List<LatLng> latLngs_A = new ArrayList<LatLng>();
        //区域 测试 凹
//        latLngs.add(new LatLng(39.9583581374, 116.8136749899));
//        latLngs.add(new LatLng(39.9591465703, 116.8255276642));
//        latLngs.add(new LatLng(39.9492669674, 116.8262848109));
//        latLngs.add(new LatLng(39.9534110000, 116.8208300000));
//        latLngs.add(new LatLng(39.9503190000, 116.8181690000));
//        latLngs.add(new LatLng(39.9486294525, 116.8143971077));

        //区域 A
        latLngs_A.add(new LatLng(39.972208, 116.82474));
        latLngs_A.add(new LatLng(39.973075, 116.852549));
        latLngs_A.add(new LatLng(39.966029, 116.850886));
        latLngs_A.add(new LatLng(39.966226, 116.855521));
        latLngs_A.add(new LatLng(39.962476, 116.855971));
        latLngs_A.add(new LatLng(39.962, 116.849834));
        latLngs_A.add(new LatLng(39.946604, 116.847989));
        latLngs_A.add(new LatLng(39.944379, 116.826687));

//        latLngs.add(new LatLng(39.979079, 116.859323));

        PolygonOptions polygonOptions_A = new PolygonOptions();
        polygonOptions_A.fillColor(Color.argb(112, 0, 0, 0))
                .visible(false)
                .strokeColor(Color.argb(255, 126, 2, 126))
                .addAll(latLngs_A)
                .strokeWidth(2)
                .zIndex(16);
        polygon_A = aMap.addPolygon(polygonOptions_A);


        //区域 B
        List<LatLng> latLngs_B = new ArrayList<LatLng>();

        latLngs_B.add(new LatLng(39.984931, 116.770898));
        latLngs_B.add(new LatLng(39.985542, 116.781859));
        latLngs_B.add(new LatLng(39.976335, 116.783375));

        latLngs_B.add(new LatLng(39.976783, 116.819277));
        latLngs_B.add(new LatLng(39.972244, 116.819255));
        latLngs_B.add(new LatLng(39.972195, 116.824705));
        latLngs_B.add(new LatLng(39.943995, 116.826687));

        latLngs_B.add(new LatLng(39.942861, 116.817095));//xin增节点

        latLngs_B.add(new LatLng(39.944045, 116.808684));
        latLngs_B.add(new LatLng(39.941265, 116.785682));
        latLngs_B.add(new LatLng(39.954392, 116.779008));

        latLngs_B.add(new LatLng(39.954902, 116.767743));
        latLngs_B.add(new LatLng(39.962369, 116.758581));
        latLngs_B.add(new LatLng(39.97697, 116.767517));

        PolygonOptions polygonOptions_B = new PolygonOptions();
        polygonOptions_B.fillColor(Color.argb(112, 0, 0, 0))
                .visible(false)
                .strokeColor(Color.argb(255, 0, 0, 255))
                .addAll(latLngs_B)
                .strokeWidth(2)
                .zIndex(16);
        aMap.addPolygon(polygonOptions_B);


        //区域 C 全燕郊
        List<LatLng> latLngs_C = new ArrayList<LatLng>();
        latLngs_C.add(new LatLng(40.026252, 116.754525));//左上
        latLngs_C.add(new LatLng(40.026252, 116.918418));//右上
        latLngs_C.add(new LatLng(39.881965, 116.918418));//右下
        latLngs_C.add(new LatLng(39.881965, 116.754525));//左下


        PolygonOptions polygonOptions_C = new PolygonOptions();
        polygonOptions_C.fillColor(Color.argb(112, 0, 0, 0))
                .visible(false)
                .strokeColor(Color.argb(255, 0, 255, 0))
                .strokeWidth(2)
                .addAll(latLngs_C)
                .zIndex(16);
        polygon_C = aMap.addPolygon(polygonOptions_C);
//
//
        //区域 D
        List<LatLng> latLngs_D = new ArrayList<LatLng>();
        latLngs_D.add(new LatLng(39.967157, 116.7773));
        latLngs_D.add(new LatLng(39.967709, 116.780863));
        latLngs_D.add(new LatLng(39.965916, 116.781485));
        latLngs_D.add(new LatLng(39.965604, 116.781067));
        latLngs_D.add(new LatLng(39.963852, 116.781432));
        latLngs_D.add(new LatLng(39.96358, 116.780057));


        PolygonOptions polygonOptions_D = new PolygonOptions();
        polygonOptions_D.fillColor(Color.argb(112, 0, 0, 0))
                .visible(true)
                .strokeColor(Color.argb(255, 255, 255, 0))
                .addAll(latLngs_D)
                .strokeWidth(2)
                .zIndex(16);
        aMap.addPolygon(polygonOptions_D);
//
//
//        //区域 E
//        List<LatLng> latLngs_E = new ArrayList<LatLng>();
//        latLngs_E.add(new LatLng(39.975883, 116.776272));
//        latLngs_E.add(new LatLng(39.976606, 116.824552));
//        latLngs_E.add(new LatLng(39.953797, 116.825961));
//        latLngs_E.add(new LatLng(39.953024, 116.808731));
//        latLngs_E.add(new LatLng(39.950129, 116.785213));
//
//
//        PolygonOptions polygonOptions_E = new PolygonOptions();
//        polygonOptions_E.fillColor(Color.argb(112, 0, 0, 0))
//                .visible(true)
//                .strokeColor(Color.argb(255, 255, 0, 255))
//                .addAll(latLngs_E)
//                .strokeWidth(2)
//                .zIndex(16);
//        polygon_E = aMap.addPolygon(polygonOptions_E);


        Log.e(TAG, "C  区域是否包含 ：39.917438, 116.798302 ：" + polygon_C.contains(new LatLng(39.917438, 116.798302)));


        //关键字搜索
        PoiSearch.Query query = new PoiSearch.Query("区", "", "0316");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                Log.i(TAG, "关键字搜索 ：" + poiItems);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();


        if (marrkerLatLng != null) {
            marker = aMap.addMarker(new MarkerOptions()
                    .position(marrkerLatLng)
                    .title("")
                    .visible(false)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.location_marker)))
                    .snippet("DefaultMarker"));
        }


        mlocationClient = new AMapLocationClient(this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位


        mlocationClient.startLocation();

        Log.i("TAG", "中心 位置吧 ： " + aMap.getCameraPosition().target);


    }

    /**
     * 逆地理编码
     *
     * @param target
     */
    private void geocoderSearch(LatLng target) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                if (rCode == 1000) {
                    if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                            && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                        String addressName = regeocodeResult.getRegeocodeAddress().getFormatAddress()
                                + "附近";
                        Log.i(TAG, addressName);
                    }
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        LatLonPoint latLonPoint = new LatLonPoint(target.latitude, target.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);

        geocoderSearch.getFromLocationAsyn(query);
    }

    private void cloudSearch(LatLng target) {

        String types = "120000|120100|120200|120201|120202|120300|120301|120302|120303|120304|141200|141201|141202|141203|141204|141205|141206|190400|190401|190402|190403|170100|100104|100103|100102|100100|100101";

        PoiSearch.Query query = new PoiSearch.Query("", types, "0316");


        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(this, query);

        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(target.latitude,
                target.longitude), 1000));//设置周边搜索的中心点以及半径

        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int rCode) {
                if (rCode == 1000) {
                    Log.i(TAG, poiResult.getPois() + "");
                    Log.i(TAG, "搜到的结果数据页码：" + poiResult.getPageCount());
//                    List<SuggestionCity> citys = poiResult.getSearchSuggestionCitys();
//                    for (int i = 0; i < citys.size(); i++) {
//                        Log.i(TAG, "当前城市: " + citys.get(i).getCityName() +
//                                ",当前城市编码：" + citys.get(i).getCityCode());
//                    }
                    for (int i = 0; i < poiResult.getPois().size(); i++) {
//                        poiResult.getPois().get(i).get
//                        resultTv.append();
                        Log.i(TAG, poiResult.getPois().get(i).getTitle() + "经纬度：" + poiResult.getPois().get(i).getLatLonPoint());
                    }

                    Log.i(TAG, "当前搜索关键字 : " + poiResult.getSearchSuggestionKeywords() + "");
                } else {
                    Log.e(TAG, "错误吗:" + rCode);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });

        poiSearch.searchPOIAsyn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        Log.i(TAG, "地图视图关闭");
        if (mlocationClient != null) {
            if (mlocationClient.isStarted()) {
                mlocationClient.stopLocation();
            }
            mlocationClient.onDestroy();
            Log.i(TAG, "定位 服务 stop");
            mlocationClient = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.i(TAG, "激活 activate");
//        mListener = onLocationChangedListener;

        if (locationLatLng != null)
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 16));
    }

    @Override
    public void deactivate() {
        Log.i(TAG, "注销激活 deactivate");
//        mListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            Log.i(TAG, "定位");
//            Log.i(TAG, "mListner 是否为空：" + (mListener == null));
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功 切换地图中心

                if (locationLatLng == null) {
                    locationLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                    mListener.onLocationChanged(aMapLocation);//显示系统小蓝点
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 16));

                }


                if (marrkerLatLng == null) {
                    marrkerLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                    marker = aMap.addMarker(new MarkerOptions().
                            position(marrkerLatLng)
                            .visible(false)
                            .title("标记点")
                            .icon(bitmapDescriptor)
                            .snippet("DefaultMarker"));//当前marker 的文字片段
                }
                if (mlocationClient.isStarted())
                    mlocationClient.stopLocation();
//                Log.i(TAG, "是否包含中心点：" + polygon_A.contains(aMap.getCameraPosition().target));

            } else {
                //定位失败
                Log.e(TAG, "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}