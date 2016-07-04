package com.tongxin.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.sortlistview.SearchActivity;
import com.example.sortlistview.Weiso;
import com.tongxin.e_guide.AgentApp;
import com.tongxin.e_guide.MainActivity;
import com.tongxin.e_guide.R;


public class MapActivity extends Activity implements OnGetRoutePlanResultListener,BaiduMap.OnMapClickListener{
	private GeoFence geoFence;
	boolean isFirstLoc = true;
	private Cursor mCursor;
	private MyOrientationListener myorientation = new MyOrientationListener(this);
	private	MapView mMapView = null;  
	private	BaiduMap mBaiduMap=null;
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor="bd09ll";
    private LocationClient  mLocationClient;
    private MyLocationConfiguration.LocationMode locate_type = MyLocationConfiguration.LocationMode.NORMAL;
    public BDLocationListener myListener = new MyLocationListenner();
    
    private double mCurrentLantitude;
    private double mCurrentLongitude;
	private LatLng start,end;
    private Distance distance;
    private List<LatLng> points = new ArrayList<LatLng>();
	Button mBtnPre = null;//上一个节点
	Button mBtnNext = null;//下一个节点
	int nodeIndex = -1;//节点索引,供浏览节点时使用
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;
	private TextView popupText = null;//泡泡view
	RoutePlanSearch mSearch = null;

	@Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);   
        SDKInitializer.initialize(getApplicationContext());  
        setContentView(R.layout.activity_map);
        myorientation.start();
        mMapView = (MapView)findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        InitLocation();
        mLocationClient.start();
        distance = new Distance(mBaiduMap);
        //LatLng p1 = new LatLng(39.97923, 116.357428);
		//LatLng p2 = new LatLng(39.94923, 116.397428);
		//points.add(p1);
		//points.add(p2);
       /* search_tool.Db_initial();
        addCustomElementsDemo();*/
		/*Added by SPH*/
		//geoFence.detectGeoFence("39.965341", "116.365677");
		//System.out.println(geoFence.detectGeoFence("39.965341", "116.365677"));
		/*Added by SPH*/
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(20);
		mBaiduMap.setMapStatus(u);
		mBtnPre = (Button) findViewById(R.id.pre);
		mBtnNext = (Button) findViewById(R.id.next);
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		//地图点击事件处理
		mBaiduMap.setOnMapClickListener(this);
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
		geoFence = new GeoFence(mBaiduMap,this);
		AgentApp.getInstance().addActivity(this);
		//distance.Sign(55);
    }  
    protected void onDestroy() {
		// 退出时销毁定位
		mLocationClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mMapView = null;
    }
	public void nodeClick(View v) {
		if (route == null ||
				route.getAllStep() == null) {
			return;
		}
		if (nodeIndex == -1 && v.getId() == R.id.pre) {
			return;
		}
		//设置节点索引
		if (v.getId() == R.id.next) {
			if (nodeIndex < route.getAllStep().size() - 1) {
				nodeIndex++;
			} else {
				return;
			}
		} else if (v.getId() == R.id.pre) {
			if (nodeIndex > 0) {
				nodeIndex--;
			} else {
				return;
			}
		}
		//获取节结果信息
		LatLng nodeLocation = null;
		String nodeTitle = null;
		Object step = route.getAllStep().get(nodeIndex);
		if (step instanceof DrivingRouteLine.DrivingStep) {
			nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
			nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
		} else if (step instanceof WalkingRouteLine.WalkingStep) {
			nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
			nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
		} else if (step instanceof TransitRouteLine.TransitStep) {
			nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
			nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
		}

		if (nodeLocation == null || nodeTitle == null) {
			return;
		}
		//移动节点至中心
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
		// show popup
		popupText = new TextView(MapActivity.this);
		popupText.setBackgroundResource(R.drawable.popup);
		popupText.setTextColor(0xFF000000);
		popupText.setText(nodeTitle);
		mBaiduMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));

	}
	@Override
	public void onMapClick(LatLng point) {
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			//result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			//result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			//result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
			routeOverlay = overlay;
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}
	//定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.locate, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int item_id=item.getItemId();
		switch(item_id)
		{
			case R.id.show_route:
				distance.write_line(points);
				break;
			case R.id.hide_route:
				distance.Cancel();
				break;
			case R.id.search_place:
				Intent intent1 = new Intent(MapActivity.this,SearchActivity.class);
				startActivityForResult(intent1,1);
				break;
			case R.id.shot:
				Intent intent2 = new Intent(MapActivity.this,Weiso.class);
				startActivityForResult(intent2, 1);
				break;
		}
		return true;

	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(requestCode==1)
		{
			switch(resultCode) {
				case 1:
				{
					Bundle bundle = data.getExtras();
					String str = bundle.getString("position");
					int pos = Integer.valueOf(str).intValue();
					distance.Cancel();
					//WriteToSD w=new WriteToSD(this);
					//Vector<Place> ttrace=new Vector<Place>();
					//ttrace=Find.find(mCurrentLantitude, mCurrentLongitude, distance.platitude[pos], distance.plongitude[pos]);
					end = new LatLng(distance.platitude[pos], distance.plongitude[pos]);
					route = null;
					mBtnPre.setVisibility(View.INVISIBLE);
					mBtnNext.setVisibility(View.INVISIBLE);
					mBaiduMap.clear();
					// 处理搜索按钮响应;
					PlanNode stNode = PlanNode.withLocation(start);
					PlanNode enNode = PlanNode.withLocation(end);
					//搜索步行路径
					mSearch.walkingSearch((new WalkingRoutePlanOption())
							.from(stNode)
							.to(enNode));
         /*List<LatLng> trace=new ArrayList<LatLng>();
         for(Place e:ttrace)
            trace.add(new LatLng(e.latitude,e.longitude));
         if(trace.size()>3)
         distance.write_line(trace);
         else
         Toast.makeText(MapActivity.this, "目的地100米以内，已标出", Toast.LENGTH_SHORT).show();
         distance.Sign(pos);
         distance.Sign(17);*/
					break;
				}
				case 2:
				{
					Bundle bundle=data.getExtras();
					String str0=bundle.getString("position0");
					String str1=bundle.getString("position1");
					String str2=bundle.getString("position2");
					String str3=bundle.getString("position3");
					String str4=bundle.getString("position4");
					String str5=bundle.getString("position5");
					String str6=bundle.getString("position6");
					String str7=bundle.getString("position7");
					String str8=bundle.getString("position8");
					String str9=bundle.getString("position9");
					String str10=bundle.getString("position10");
					String str11=bundle.getString("position11");
					String str12=bundle.getString("position12");
					String str13=bundle.getString("position13");
					String str14=bundle.getString("position14");
					String str15=bundle.getString("position15");
					String str16=bundle.getString("position16");
					String str17=bundle.getString("position17");
					String str18=bundle.getString("position18");

					int pos0 =Integer.valueOf(str0).intValue();
					int pos1 =Integer.valueOf(str1).intValue();
					int pos2 =Integer.valueOf(str2).intValue();
					int pos3 =Integer.valueOf(str3).intValue();
					int pos4 =Integer.valueOf(str4).intValue();
					int pos5 =Integer.valueOf(str5).intValue();
					int pos6 =Integer.valueOf(str6).intValue();
					int pos7 =Integer.valueOf(str7).intValue();
					int pos8 =Integer.valueOf(str8).intValue();
					int pos9 =Integer.valueOf(str9).intValue();
					int pos10 =Integer.valueOf(str10).intValue();
					int pos11 =Integer.valueOf(str11).intValue();
					int pos12 =Integer.valueOf(str12).intValue();
					int pos13 =Integer.valueOf(str13).intValue();
					int pos14 =Integer.valueOf(str14).intValue();
					int pos15 =Integer.valueOf(str15).intValue();
					int pos16 =Integer.valueOf(str16).intValue();
					int pos17 =Integer.valueOf(str17).intValue();
					int pos18 =Integer.valueOf(str18).intValue();
					distance.Cancel();
					mBaiduMap.clear();
					if(pos0!=-1)
						distance.Sign(pos0);
					if(pos1!=-1)
						distance.Sign(pos1);
					if(pos2!=-1)
						distance.Sign(pos2);
					if(pos3!=-1)
						distance.Sign(pos3);
					if(pos4!=-1)
						distance.Sign(pos4);
					if(pos5!=-1)
						distance.Sign(pos5);
					if(pos6!=-1)
						distance.Sign(pos6);
					if(pos7!=-1)
						distance.Sign(pos7);
					if(pos8!=-1)
						distance.Sign(pos8);
					if(pos9!=-1)
						distance.Sign(pos9);
					if(pos10!=-1)
						distance.Sign(pos10);
					if(pos11!=-1)
						distance.Sign(pos11);
					if(pos12!=-1)
						distance.Sign(pos12);
					if(pos13!=-1)
						distance.Sign(pos13);
					if(pos14!=-1)
						distance.Sign(pos14);
					if(pos15!=-1)
						distance.Sign(pos15);
					if(pos16!=-1)
						distance.Sign(pos16);
					if(pos17!=-1)
						distance.Sign(pos17);
					if(pos18!=-1)
						distance.Sign(pos18);
					break;
				}
				default:break;
			}
		}
	}


	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setOpenGps(true);
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(20000);//设置发起定位请求的间隔时间为1000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
	}
    public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
							// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(myorientation.get_angel()).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory  
                    .fromResource(R.drawable.locate_symbol);  
            MyLocationConfiguration config =new MyLocationConfiguration(locate_type, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);  
            geoFence.detectGeoFence(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				points.add(ll);
				mCurrentLantitude=ll.latitude;
				mCurrentLongitude=ll.longitude;
			}
			else
			{
				if(distance.gps2m(location.getLatitude(), location.getLongitude(), mCurrentLantitude, mCurrentLongitude)>5)
				{
					points.add(new LatLng(location.getLatitude(),
						location.getLongitude()));
					mCurrentLantitude=location.getLatitude();
					mCurrentLongitude=location.getLongitude();
				}
			}
			start=new LatLng(mCurrentLantitude,mCurrentLongitude);
		}
		public void onReceivePoi(BDLocation poiLocation) {
			}
		}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.setClass(MapActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

    }
