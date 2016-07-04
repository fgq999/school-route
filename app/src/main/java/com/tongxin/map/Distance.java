package com.tongxin.map;

import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.tongxin.e_guide.R;


public class Distance
{
	private	BaiduMap nBaiduMap=null;
	private double last_Longitude;
	private double last_Latitude;
    private final double EARTH_RADIUS = 6378137.0;  

    public double platitude[]={39.970253,
			39.970493,
			39.970294,
			39.970079,
			39.96979,
			39.969678,
			39.96894,
			39.969332,
			39.969261,
			39.968765,
			39.968767,
			39.968254,
			39.968318,
			39.968351,
			39.967955,
			39.967663,
			39.967195,
			39.966312,
			39.965737,
			39.96541,
			39.970421,
			39.970022,
			39.969768,
			39.969835,
			39.96993,
			39.969465,
			39.969465,
			39.9696,
			//39.969291,
			39.969428,
			39.969119,
			39.969148,
			39.968614,
			39.968419,
			39.968514,
			39.968651,
			39.967955,
			39.96787,
			39.967972,
			39.967732,
			39.967972,
			39.968039,
			39.967235,
			39.967216,
			39.966409,
			39.96624,
			39.966383,
			39.965341,
			39.968239,//东门
			39.966905,//西门
			39.964000,//南
			39.970690,//北
			39.965570,//中
			39.970257,
			39.968614,
			39.967324,
			39.966682,
			39.966609,
			39.968614
			 };
    public double plongitude[]={116.362052,
			116.362398,
			116.363494,
			116.362303,
			116.361811,
			116.363035,
			116.361852,
			116.362429,
			116.363258,
			116.36242,
			116.363339,
			116.361859,
			116.362436,
			116.363305,
			116.361883,
			116.362905,
			116.361906,
			116.363108,
			116.363636,
			116.363292,
			116.364738,
			116.363909,
			116.363952,
			116.364251,
			116.36547,
			116.364102,
			116.364102,
			116.364798,
			//116.364194,
			116.36509,
			116.365537,
			116.366635,
			116.364418,
			116.365516,
			116.366538,
			116.367223,
			116.363987,
			116.36398,
			116.364836,
			116.364497,
			116.366139,
			116.367219,
			116.364913,
			116.365625,
			116.364635,
			116.365547,
			116.366722,
			116.365677,
			116.367519,//东门
			116.361979,//西
			116.364677,//南
			116.363638,//北
			116.364000,//中
			116.363909,
			116.364418,
			116.364387,
			116.363168,
			116.363985,
			116.364418
			 };

	public Distance(BaiduMap BM)
	{
		nBaiduMap = BM;
	}
	public double gps2m(double lat_a, double lng_a, double lat_b, double lng_b)   //两坐标求距离，单位是m。
	{
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	public void Warm()
	{
		for(int i=0;i<57;i++)                //与47个定点比较
		{
			if(gps2m(last_Latitude,last_Longitude,platitude[i],plongitude[i])<15)
			{
				;//显示出文字提醒
			}
		}
	}
	public void write_line(List<LatLng> line)
	{
		if(line.size()>1)
		{
			OverlayOptions ooPolyline = new PolylineOptions().width(10)
					.color(0xAAFF0000).points(line);
			nBaiduMap.addOverlay(ooPolyline);
		}
	}
	public void Sign(int num)//添加覆盖物
	{
		LatLng point = new LatLng( platitude[num],plongitude[num]);
		//构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.sign);
		//构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);

		//在地图上添加Marker，并显示
		nBaiduMap.addOverlay(option);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
		nBaiduMap.setMapStatus(u);
	}
	public void Cancel()                              //删除标志物
	{
		nBaiduMap.clear();//全部删除nBaiduMap.clearOverlays();
		//OverlayOptions opt=Sign();
		//Marker marker=(Marker)nBaiduMap.addOverlay(opt);   //删除刚固定的点，且要先调用Sign（）；map.removaOverlays(poliyline)一段折线
		//marker.remove();
	}
}
