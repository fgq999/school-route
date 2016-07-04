package com.tongxin.speech;

import com.tongxin.e_guide.AgentApp;
import com.tongxin.e_guide.MainActivity;
import com.tongxin.e_guide.R;
import com.tongxin.speak.synther;



import java.io.File;

import com.example.update.client;





//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View.OnClickListener;

public class SpeechActivity extends Activity implements OnTouchListener,OnGestureListener{

	private synther voice = new synther(SpeechActivity.this);
	public Button buttonPR,buttonStart,button_menu;
	private TextView textviewDescription;
	private ImageView imageView;
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;

	private int des_id[]={//speech
			R.string.desBuptHotel,		//"北邮科技大厦",	0
			R.string.desDorm11,		//"学十一公寓",		1
			R.string.desStudentsDorm10,		//"学十公寓",		2
			R.string.desDorm9,		//"学九公寓",		3
			R.string.desDormForeigner,		//"留学生公寓",		4
			R.string.desNewStudentsDiningRoom,		//"新食堂",			5
			R.string.desDorm13,		//"学十三公寓",	6
			R.string.desDorm5,		//"学五公寓",	7
			R.string.desDorm8,		//"学八公寓",	8
			R.string.desDorm3,		//"学三公寓",	9
			R.string.desDorm4,		//"学四公寓",	10
			R.string.desMarket,		//"超市",	11
			R.string.desDorm1,		//"学一公寓",	12	
			R.string.desDorm2,		//"学二公寓",	13
			R.string.desHongtong_building,		//"鸿通楼",	14
			R.string.des_jiao4,		//"教四楼",	15
			R.string.desChina_mobile,		//"移动营业厅",16
			R.string.des_jiao3,		//"教三楼",17
			R.string.des_hospital,		//"校医院",18
			R.string.des_bookstore,		//"书店",19
			R.string.desDorm6,		//"学六公寓",20
			R.string.desStudents_act,		//"学生活动中心",21
			R.string.des_shumei,		//"数字媒体学院",22
			R.string.des_servicebuilding,		//"综合服务楼",23
			R.string.des_newsciencebuilding,		//"新科研楼",24
			R.string.desCoffeebar,		//"漫咖啡",25
			R.string.des_xueyuan,		//"学苑超市",26
			R.string.des_fruit,		//"水果店",27
			//R.string.des_dianxin,		//"宽广电信",28
			R.string.des_stuffDiningroom,		//"教工餐厅",29
			R.string.des_oldDiningroom,		//"学生食堂",30
			R.string.des_securityOffice,		//"保卫处",31
			R.string.desLibarary,		//"图书馆",32
			R.string.des_basketball,		//"篮球场",33
			R.string.des_volleyball,		//"排球场",34
			R.string.desDorm29,		//"学二十九公寓",35
			R.string.des_financeOffice,		//"财务处",36
			R.string.des_houqin,		//"后勤楼",37
			R.string.desXiaoBaiLou,		//"小白楼",38
			R.string.des_jiao1,		//"教一楼",39
			R.string.desGym,		//"体育馆",40
			R.string.des_natatorium,		//"游泳馆",41
			R.string.DesMainBuilding,		//"主楼",42
			R.string.des_hall,		//"科学会堂",43
			R.string.des_jiao2,		//"教二楼",44
			R.string.des_netcenter,		//"网络中心",45
			R.string.des_sportsGround,		//"风雨操场",46
			R.string.des_kindergarten,		//"北邮幼儿园"47
		    R.string.eest_door,
			R.string.west_door,
			R.string.south_door,
			R.string.north_door,
			R.string.mid_door,
			R.string.jingguan,
			R.string.xiaoqinghuazhan,
			R.string.yepeida,
			R.string.caichangnian,
			R.string.zhoujiongpan,
			R.string.xunixiaoshiguan
	};
	private int pic_id[]={            //picture id /name
			R.drawable.bupthotel,		//"北邮科技大厦",
			R.drawable.dorm11,		//"学十一公寓",
			R.drawable.dorm10,		//"学十公寓",
			R.drawable.dorm9,		//"学九公寓",
			R.drawable.dormforeigner,		//"留学生公寓",
			R.drawable.newdiningroom,		//"新食堂",
			R.drawable.dorm13,		//"学十三公寓",
			R.drawable.dorm5,		//"学五公寓",
			R.drawable.dorm8,		//"学八公寓",
			R.drawable.dorm3,		//"学三公寓",
			R.drawable.dorm4,		//"学四公寓",
			R.drawable.market,		//"超市",
			R.drawable.dorm1,		//"学一公寓",
			R.drawable.dorm2,		//"学二公寓",
			R.drawable.hongtong_building,		//"鸿通楼",
			R.drawable.jiao4,		//"教四楼",
			R.drawable.china_mobile,		//"移动营业厅",
			R.drawable.jiao3,		//"教三楼",
			R.drawable.hospital,		//"校医院",
			R.drawable.bookstore,		//"书店",
			R.drawable.dorm6,		//"学六公寓",
			R.drawable.students_act,		//"学生活动中心",
			R.drawable.shumei,		//"数字媒体学院",
			R.drawable.servicebuilding,		//"综合服务楼",
			R.drawable.newsciencebuilding,		//"新科研楼",
			R.drawable.coffeebar,		//"漫咖啡",
			R.drawable.xueyuan,		//"学苑超市",
			R.drawable.fruitstore,		//"水果店",
			//R.drawable.dianxin,		//"宽广电信",
			R.drawable.stuffdiningroom,		//"教工餐厅",
			R.drawable.olddiningroom,		//"学生食堂",
			R.drawable.security_office,		//"保卫处",
			R.drawable.library,		//"图书馆",
			R.drawable.basketball,		//"篮球场",
			R.drawable.volleyball,		//"排球场",
			R.drawable.dorm29,		//"学二十九公寓",
			R.drawable.finance_office,		//"财务处",?????????????????
			R.drawable.houqin,		//"后勤楼",
			R.drawable.xiaobailou,		//"小白楼",
			R.drawable.jiao1,		//"教一楼",
			R.drawable.gym,		//"体育馆",
			R.drawable.natatorium,		//"游泳馆",
			R.drawable.mainbuilding,		//"主楼",
			R.drawable.hall,		//"科学会堂",
			R.drawable.jiao2,		//"教二楼",
			R.drawable.netcenter,		//"网络中心",
			R.drawable.sports_ground,		//"风雨操场",
			R.drawable.kindergarten,		//"北邮幼儿园"
			R.drawable.east_door,
			R.drawable.west_door,
			R.drawable.south_door,
			R.drawable.north_door,
			R.drawable.mid_door,
			R.drawable.jingguanlou,
			R.drawable.xiaoqinghuazhan,
			R.drawable.yepeida,
			R.drawable.caichangnian,
			R.drawable.zhoujiongpan,
			R.drawable.xunixiaoshiguan
	};


	private GestureDetector mGestureDetector;

	private int n=58;
	private int num=0;
	String b=client.banben;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_speech);
		button_menu=(Button)findViewById(R.id.button_menu);
		buttonStart=(Button) findViewById(R.id.buttonPlay);
		buttonPR=(Button) findViewById(R.id.buttonPause);
		textviewDescription=(TextView)findViewById(R.id.textViewDescription);
		imageView= (ImageView) findViewById(R.id.imageViewPlace);
		button_menu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent=new Intent(SpeechActivity.this,SpeechMenu.class);
				startActivity(intent);
				finish();
			}
		});

		ViewParent vp=imageView.getParent();
		((View) vp).setOnTouchListener(this);
		((View) vp).setLongClickable(true);
		mGestureDetector = new GestureDetector(this);
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		if(bundle!=null){
			num =bundle.getInt("name");
			imageView.setImageResource(pic_id[num]);
			textviewDescription.setText(des_id[num]);
		}
		AgentApp.getInstance().addActivity(this);
	}

	protected void onDestroy() {
		// 退出时销毁定位
		//voice.end_speak();
		super.onDestroy();
        /*Added by SPH.*/ //此处修复退出时仍然继续解说和滑动时或返回键返回时异常退出BUG
		if(voice.pauseOrResume() == 0)
		{
			voice.end_speak();
		}
        /*Added by SPH.*/
	}
	public void startSpeech(View view){
		//设置合成文本.
		voice.speaker(num);
		buttonPR.setBackgroundResource(R.drawable.stop);
	}

	public void pauseOrResume(View view){
		int symbol=voice.pauseOrResume();
		if(symbol == 1)
			buttonPR.setBackgroundResource(R.drawable.begin);
		else if(symbol == 0)
			buttonPR.setBackgroundResource(R.drawable.stop);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(arg1);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {

		// 参数解释：  
		// e1：第1个ACTION_DOWN MotionEvent   
		// e2：最后一个ACTION_MOVE MotionEvent   
		// velocityX：X轴上的移动速度，像素/秒   
		// velocityY：Y轴上的移动速度，像素/秒   
		// 触发条件 ：   
		// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒   
		Intent intent=new Intent();
		intent.setClass(SpeechActivity.this, SpeechActivity.class);
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY)
		{
			// Fling left
			//<Marked by SPH>表示手指从右向左滑动←
			if(num == n-1)
			{
				num = 0;
			}
			else
			{
				num++;
			}
			/*Modified by SPH*/
			intent.putExtra("name", num);
			System.out.println(num);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//Modified by SPH
			startActivity(intent);
			overridePendingTransition(R.anim.from_out, R.anim.from_in);//<Added by SPH>右边进入,左边退出
			/*Modified by SPH*/
		}
		else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY)
		{

			// Fling right
			//<Marked by SPH>表示手指从左向右滑动→
			if(num == 0)
			{
				num = n-1;
			}
			else
			{
				num--;
			}
			/*Modified by SPH*/
			intent.putExtra("name", num);
			System.out.println(num);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//Modified by SPH
			startActivity(intent);
			overridePendingTransition(R.anim.from_out, R.anim.from_in);//<Added by SPH>左边进入,右边退出
			/*Modified by SPH*/
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
							float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent=new Intent();
			intent.setClass(SpeechActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
