package com.tongxin.speak;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.tongxin.e_guide.R;

public class synther {
	private Context context;
	private SpeechSynthesizer mTts;
	private int pause_symbol=-1;

	private int des_id[]={
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

	private int num=0;

	public synther(Context c)
	{
		context=c;
	   /* readFile reading=new readFile(getApplicationContext());
	    n=reading.getFileNum("des.txt");
	    String[] des=new String[n];
	    des=reading.readSDFile("des.txt",n);*/
	}
	public void speaker(int num)
	{
		SpeechUtility.createUtility(context, SpeechConstant.APPID +"=53ce7373");
		mTts= SpeechSynthesizer.createSynthesizer(context, null);
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		//设置发音人 
		mTts.setParameter(SpeechConstant.SPEED, "50");
		//设置语速 
		mTts.setParameter(SpeechConstant.VOLUME, "80");
		//设置音量，范围0~100
		//如果不需要保存合成音频，注释该行代码 mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
		mTts.startSpeaking(context.getString(des_id[num]), mSynListener);
		//mTts.startSpeaking(ss, mSynListener);
		//SpeechActivity调用Speaker函数传递参数num
		pause_symbol=0;
	}
	public int pauseOrResume(){
		if(pause_symbol == 0)
		{
			mTts.pauseSpeaking();
			pause_symbol=1;
		}
		else if(pause_symbol == 1)
		{
			mTts.resumeSpeaking();
			pause_symbol=0;
		}
		return pause_symbol;
	}
	public void end_speak()
	{
		if(mTts.isSpeaking())
			mTts.destroy();
	}
	private SynthesizerListener mSynListener = new SynthesizerListener()
	{
		@Override
		public void onSpeakBegin() {

		}

		@Override
		public void onBufferProgress(int i, int i1, int i2, String s) {

		}

		@Override
		public void onSpeakPaused() {

		}

		@Override
		public void onSpeakResumed() {

		}

		@Override
		public void onSpeakProgress(int i, int i1, int i2) {

		}

		@Override
		public void onCompleted(SpeechError speechError) {

		}

		@Override
		public void onEvent(int i, int i1, int i2, Bundle bundle) {

		}
	};
}
