package com.tongxin.map;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
//import com.baidu.nplatform.comapi.basestruct.GeoPoint;
//import com.baidu.mapapi.map.PopupOverlay; //�޷��ҵ������,����ʹ��PopupOverlay�����
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.utils.CoordinateConverter;
//import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
//import com.baidu.mapapi.utils.DistanceUtil;
import com.tongxin.db.place_search;
import com.tongxin.e_guide.MainActivity;
import com.tongxin.e_guide.R;
import com.tongxin.speech.SpeechActivity;

/*Added by SPH.实现地理围栏相关功能类*/
public class GeoFence extends Activity
{
	private place_search place;
	private int count = -1;
	private	BaiduMap nBaiduMap=null;
	private Marker marker = null;
	private MapActivity mapActivity = null;
	//private NotifyLister mNotifyer;
	private LocationClient mLocationClient;
	private final double EARTH_RADIUS = 6378137;
	private int flag = -1;
	private AlertDialog dialog = null;
	private TextView textview = null;

	public GeoFence()
	{
		System.out.println("生成GeoFence对象.");
		place = new place_search();
		//dialog = new AlertDialog.Builder(this).create();
	}
	public GeoFence(BaiduMap BM, MapActivity mapActivity)
	{
		nBaiduMap = BM;
		this.mapActivity = mapActivity;
		System.out.println("生成GeoFence对象.");
		place = new place_search();
		dialog = new AlertDialog.Builder(this.mapActivity).create();
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	}

	public place_search getPlaceSearch()
	{
		return this.place;
	}

	@SuppressLint("NewApi") public String detectGeoFence(String latitude, String longitude)
	{
		System.out.println("进入detectGeoFence.");
		double currentLatitude = Double.valueOf(latitude);
		double currentLongitude = Double.valueOf(longitude);
		double detectLatitude = 0.0;
		double detectLongitude = 0.0;
		double distance = 0.0;
		double minDistance = -1;
		System.out.println("生成LatLng坐标.");
		//LatLng currentPoint = new LatLng(latitudeCurrent,longitudeCurrent);
		//CoordinateConverter converter  = new CoordinateConverter();
		//converter.from(CoordType.GPS);
		//converter.coord(currentPoint);
		//LatLng currentLatLng = converter.convert();
		System.out.println("循环查找.");
		for(int i = 0; i <= 57 ; ++i)
		{
			detectLatitude = Double.valueOf(place.getPlatitude()[i]);
			detectLongitude = Double.valueOf(place.getPlongitude()[i]);
			distance = getDistance(currentLatitude,currentLongitude,detectLatitude,detectLongitude);
			if(minDistance == -1)
			{
				minDistance = distance;
			}
			else
			{
				if((distance < minDistance))
				{
					minDistance = distance;
					flag = i;
				}
			}
			System.out.println("与当前最近坐标点距离为"+minDistance+"米");
		}
		if((minDistance < 50)&&(count != flag))
		{
			//flag = i;
			//dialog.setMessage("您正在游览"+place.getPlaceName()[flag]);
			//tip.setAlpha(255);
			//textview.setText("您正在游览"+place.getPlaceName()[flag]);
			ShowAlertDialopg("您正在游览"+place.getPlaceName()[flag]);
			//new AlertDialog.Builder(mapActivity).setTitle("提示")
			//.setMessage("您现在正在游览"+place.getPlaceName()[flag]+"区域")
			//.setPositiveButton("确定", null)
			//.show();
			Vibrator vibrator = (Vibrator)mapActivity.getSystemService(Context.VIBRATOR_SERVICE);
			//long[] pattern = {2000,2000};   // 停止 开启 停止 开启
			//vibrator.vibrate(pattern,1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
			vibrator.vibrate(2000); //两秒长震动提醒进入地理围栏
			System.out.println("已经进入"+place.getPlaceName()[flag]+"区域");
			Sign(flag);
			//Sign(0);
			//位置提醒相关代码
			//mNotifyer = new NotifyLister();
			//mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,50,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
			//mLocationClient.registerNotify(mNotifyer);
			//注册位置提醒监听事件后，可以通过SetNotifyLocation 来修改位置提醒设置，修改后立刻生效。
			count = flag;
			return "true";
		}
		//Cancel();
		System.out.println("没有找到.");
		return "false";
	}
	public int getCount() //返回进入的是哪个区域的数字代号
	{
		return this.count;
	}

	public double getDistance(double currentLatitude,double currentLongitude,double detectLatitude,double detectLongitude)
	{
		double distance = 0.0;
		double radLat1 = angleToRadian(currentLatitude);
		double radLat2 = angleToRadian(detectLatitude);
		double a = radLat1 - radLat2;
		double b = angleToRadian(currentLongitude) - angleToRadian(detectLongitude);
		distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		distance = distance * EARTH_RADIUS;
		//distance = Math.round(distance * 10000) / 10000;
		return distance;//计算结果单位为米
	}


	private double angleToRadian(double angle) //角度转弧度
	{
		return angle * Math.PI / 180.0;
	}

	public void Sign(int num)//添加覆盖物
	{
		LatLng point = new LatLng(Double.valueOf(place.getPlatitude()[num]),Double.valueOf(place.getPlongitude()[num]));
		//构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.speechsign);
		/*
		    //构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);

		    //在地图上添加Marker，并显示
		marker = (Marker) (nBaiduMap.addOverlay(option));
		//nBaiduMap.addOverlay(option);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
		nBaiduMap.setMapStatus(u);

		*/
		//Button button = new Button(getApplicationContext());
		//button.setBackgroundResource(R.drawable.sign);
		//创建InfoWindow的点击事件监听者
		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
			public void onInfoWindowClick()
			{
				//添加点击后的事件响应代码
				System.out.println("被点击了");

				Intent intent=new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("name", flag);
				intent.putExtras(bundle);
				intent.setClass(mapActivity, SpeechActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//mapActivity.finish();
				mapActivity.startActivity(intent);
			}
		};
		//创建InfoWindow
		InfoWindow mInfoWindow = new InfoWindow(bitmap, point,0, listener);
		//显示InfoWindow
		nBaiduMap.showInfoWindow(mInfoWindow);

	}
	public void Cancel()                              //删除标志物
	{
		//nBaiduMap.clear();//全部删除nBaiduMap.clearOverlays();
		nBaiduMap.hideInfoWindow();
		//OverlayOptions opt=Sign();
		//Marker marker=(Marker)nBaiduMap.addOverlay(opt);   //删除刚固定的点，且要先调用Sign（）；map.removaOverlays(poliyline)一段折线
		//marker.remove();
	}

	private void ShowAlertDialopg(String tips)
	{
		//final AlertDialog dialog = new AlertDialog.Builder(this).create();
		//dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alert_dialog);
		textview = (TextView)window.findViewById(R.id.textviewdialog);
		textview.setTextColor(Color.rgb(255, 255, 255));
		textview.setTextSize(20);
		//textview.setTextAlignment(textAlignment)
		textview.setText(tips);
		ImageButton confirm = (ImageButton)window.findViewById(R.id.confirm);
		confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();

			}
		});
	}
}
