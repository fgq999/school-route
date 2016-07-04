package com.tongxin.e_guide;


import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im_bmob.CustomApplcation;
import com.im_bmob.MyMessageReceiver;
import com.im_bmob.ui.NewFriendActivity;
import com.im_bmob.ui.fragment.ChatMainFragment;
import com.im_bmob.ui.fragment.ContactFragment;
import com.im_bmob.ui.fragment.RecentFragment;
import com.im_bmob.ui.fragment.SettingsFragment;
import com.lostfound.Biaobai;
import com.lostfound.Lost_Found_Activity;
import com.tongxin.map.MapActivity;
import com.tongxin.speech.SpeechActivity;
import com.community.fragments.FragmentTabAdapter;
import com.tongxin.e_guide.R;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.listeners.Listeners.FetchListener;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.login.Loginable;
import com.umeng.comm.core.nets.responses.AlbumResponse;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.core.nets.responses.LikesResponse;
import com.umeng.comm.core.nets.responses.TopicResponse;
import com.umeng.comm.core.nets.responses.UsersResponse;
import com.umeng.comm.core.sdkmanager.ImageLoaderManager;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.comm.core.sdkmanager.LoginSDKManager;


import com.umeng.comm.ui.fragments.CommunityMainFragment;
import com.community.custom.SimpleLoginImpl;
import com.community.custom.UILImageLoader;
import com.umeng.comm.ui.widgets.CommunityViewPager;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.community.login.UMAuthService;
import com.umeng.community.login.UMLoginServiceFactory;
import com.umeng.community.share.UMShareServiceFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;

public class MainActivity extends FragmentActivity {
	CommunitySDK mCommSDK = null;
	String topicId = "";
	public static MainActivity instance = null;

	private ViewPager mTabPager;
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
	private LinearLayout mCloseBtn;
	private View layout;
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	private  RelativeLayout rl;
	//private Button mRightBtn;
	boolean flag=false;
	private Fragment[] fragments;
	private int index=0;
	private int currentTabIndex=0;
	private RelativeLayout[] mTabs;
	private ContactFragment fragment2;
	private SettingsFragment fragment3;
	private RecentFragment fragment1;
	private TextView tv_talk, tv_contact, tv_settings;
	private ImageView img1,img2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		rl=(RelativeLayout)findViewById(R.id.main);
		//启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		mCommSDK = CommunityFactory.getCommSDK(this);
		initPlatforms(this);
		View view=LayoutInflater.from(this).inflate(R.layout.main_tab_chat, null);
		mTabPager = (ViewPager)findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.img_weixin);
		mTab2 = (ImageView) findViewById(R.id.img_address);
		mTab3 = (ImageView) findViewById(R.id.img_friends);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));

		mTabs = new RelativeLayout[3];
		mTabs[0] = (RelativeLayout)view.findViewById(R.id.f1);
		mTabs[1] = (RelativeLayout)view.findViewById(R.id.f2);
		mTabs[2] = (RelativeLayout)view.findViewById(R.id.f3);
		tv_talk=(TextView)view.findViewById(R.id.tv_talk);
		tv_contact=(TextView)view.findViewById(R.id.tv_contact);
		tv_settings=(TextView)view.findViewById(R.id.tv_settings);

		Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth/4; //设置水平动画平移大小
		two = one*2;
		three = one*3;
		//Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);

		//InitImageView();//使用动画
		//将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.main_tab_lbs, null);
		View view2 = mLi.inflate(R.layout.main_tab_chat, null);
		View view3 = mLi.inflate(R.layout.main_tab_community, null);
		View view4 = mLi.inflate(R.layout.main_tab_helper, null);
		WindowManager wm=this.getWindowManager();
		int width=wm.getDefaultDisplay().getWidth();
		int height=wm.getDefaultDisplay().getHeight();
		img1=(ImageView)view1.findViewById(R.id.search);
		img2=(ImageView)view1.findViewById(R.id.speak);
		moveHintsImage(img1,18*width/62-getWidth(img1)/2,3*height/7-getHeight(img1)/2);
		moveHintsImage(img2,44*width/62-getWidth(img2)/2,3*height/7-getHeight(img2)/2);
		//每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		//填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}

			//@Override
			//public CharSequence getPageTitle(int position) {
			//return titles.get(position);
			//}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
	}
	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/* 页卡切换监听(原作者:D.Winter)
     */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
				case 0:
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
					if (currIndex == 1) {
						animation = new TranslateAnimation(one, 0, 0, 0);
						mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, 0, 0, 0);
						mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
					}
					else if (currIndex == 3) {
						animation = new TranslateAnimation(three, 0, 0, 0);
						mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
					}
					break;
				case 1:
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_pressed));
					fragment2=new ContactFragment();
					fragment3=new SettingsFragment();
					fragment1=new RecentFragment();
					fragments = new Fragment[]{fragment1,fragment2,fragment3};
					//ChatMainFragment fragment1=new ChatMainFragment();
					getSupportFragmentManager().beginTransaction().add(R.id.container1,fragment1).
							add(R.id.container1,fragment2).
							add(R.id.container1,fragment3).
							hide(fragment2).hide(fragment3).show(fragment1).commit();
					initNewMessageBroadCast();
					initTagMessageBroadCast();
					if (currIndex == 0) {
						animation = new TranslateAnimation(zero, one, 0, 0);
						mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, one, 0, 0);
						mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
					}
					else if (currIndex == 3) {
						animation = new TranslateAnimation(three, one, 0, 0);
						mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
					}
					break;
				case 2:
					if(!flag){
						CommunityMainFragment fragment = new CommunityMainFragment();
						fragment.setBackButtonVisibility(View.GONE);
						// 3、将友盟微社区的首页Fragment添加到Activity中
						getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

						// =================== 自定义设置部分 =================
						// 在初始化CommunitySDK之前配置推送和登录等组件
						useSocialLogin();

						// 使用自定义的ImageLoader
						// useMyImageLoader();

						// 使用自定义的登录系统
						//useCustomLogin();


						// 设置地理位置SDK
						LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
						flag=true;
					}
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_pressed));
					if (currIndex == 0) {
						animation = new TranslateAnimation(zero, two, 0, 0);
						mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 1) {
						animation = new TranslateAnimation(one, two, 0, 0);
						mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
					}
					else if (currIndex == 3) {
						animation = new TranslateAnimation(three, two, 0, 0);
						mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
					}
					break;
				case 3:
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_pressed));
					if (currIndex == 0) {
						animation = new TranslateAnimation(zero, three, 0, 0);
						mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 1) {
						animation = new TranslateAnimation(one, three, 0, 0);
						mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
					}
					else if (currIndex == 2) {
						animation = new TranslateAnimation(two, three, 0, 0);
						mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
					}
					break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	//设置标题栏右侧按钮的作用


	public void btn_locate1(View v) {                                   //自动定位
		Intent intent = new Intent (MainActivity.this,MapActivity.class);
		startActivity(intent);
	}
	public void btn_yuyin(View v) {                                   //语音解说
		Intent intent = new Intent (MainActivity.this,SpeechActivity.class);
		startActivity(intent);
	}
	public void btn_lostfound(View v) {                                  //失物招领
		Intent intent = new Intent (MainActivity.this,Lost_Found_Activity.class);
		startActivity(intent);
	}
	public void btn_biaobai(View v) {                                   //表白墙
		Intent intent = new Intent (MainActivity.this,Biaobai.class);
		startActivity(intent);
	}
	public void btn_freshmen(View v) {                                   //新生入学手册
		String url = "http://wenku.baidu.com/link?url=VFvE60lXttbrgHRLsij_FZEKG7H6t6pePjICr-45lTD19MOApZDoPEz3ZRjPQN4NlirG4N3KZSSwyFQa-c0q3_xBDfVKWr7bTFNqZmB8A17"; // web address
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}
	public void btn_guanwang(View v) {                                   //北邮官网
		String url = "http://www.bupt.edu.cn/";
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}
	private void initPlatforms(Activity activity) {
		// 添加QQ
		UMQQSsoHandler qqHandler = new UMQQSsoHandler(activity, "1104606393",
				"X4BAsJAVKtkDQ1zQ");
		qqHandler.addToSocialSDK();
		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, "1104606393",
				"X4BAsJAVKtkDQ1zQ");
		qZoneSsoHandler.addToSocialSDK();
		// 添加微信平台
		UMWXHandler wechatHandler = new UMWXHandler(activity, "wx96110a1e3af63a39",
				"c60e3d3ff109a5d17013df272df99199");
		wechatHandler.addToSocialSDK();
		// 添加微信朋友圈平台
		UMWXHandler circleHandler = new UMWXHandler(activity, "wx96110a1e3af63a39",
				"c60e3d3ff109a5d17013df272df99199");
		circleHandler.setToCircle(true);
		circleHandler.addToSocialSDK();

		UMShareServiceFactory.getSocialService().getConfig()
				.setPlatforms(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,
						SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA);
		UMShareServiceFactory.getSocialService().getConfig()
				.setPlatformOrder(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,
						SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA);
	}

	/**
	 * 自定义自己的登录系统
	 */
	protected void useSocialLogin() {

		// 用户自定义的登录
		UMAuthService mLogin = UMLoginServiceFactory.getLoginService("umeng_login_impl");
		String appId = "1105168073";
		String appKey = "sVwpQK8uYWqsDK5v";
		// SSO 设置
		// mLogin.getConfig().setSsoHandler(new SinaSsoHandler());
		new UMQQSsoHandler(this, appId, appKey).addToSocialSDK();

		String wxappId = "wx96110a1e3af63a39";
		String wxappSecret = "c60e3d3ff109a5d17013df272df99199";
		new UMWXHandler(getApplicationContext(), wxappId,
				wxappSecret).addToSocialSDK();

		// 将登录实现注入到sdk中,key为umeng_login
		LoginSDKManager.getInstance().addAndUse(mLogin);

	}

	protected void useCustomLogin() {
		// 管理器
		LoginSDKManager.getInstance().addAndUse(new SimpleLoginImpl());
	}

	/**
	 * 自定义自己的ImageLoader
	 */
	protected void useMyImageLoader() {
		//
		final String imageLoadKey = UILImageLoader.class.getSimpleName();
		// 使用第三方ImageLoader库,添加到sdk manager中, 并且使用useThis来使用该加载器.
		ImageLoaderManager manager = ImageLoaderManager.getInstance();
		manager.addImpl(imageLoadKey, new UILImageLoader(this));
		manager.useThis(imageLoadKey);
	}

	/**
	 * 一些常用的接口以及获取推荐的数据接口
	 */
	void someMethodsDemo() {
		// 主动登录
		mCommSDK.login(getApplicationContext(), new LoginListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(int stCode, CommUser userInfo) {

			}
		});

		// 获取登录SDK Manager
		LoginSDKManager manager = LoginSDKManager.getInstance();
		Loginable currentLoginable = manager.getCurrentSDK();
		// 是否登录
		//currentLoginable.isLogined(getApplicationContext());

		// 未登录下获取话题
		mCommSDK.fetchTopics(new FetchListener<TopicResponse>() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(TopicResponse response) {
				for (Topic item : response.result) {
					Log.e("", "### topic id : " + item.id + ", name = " + item.name);
					topicId = item.id;
				}

			}
		});

		// 未登录情况下获取某个话题下的feed
		mCommSDK.fetchTopicFeed(topicId, new
				FetchListener<FeedsResponse>() {

					@Override
					public void onComplete(FeedsResponse response) {
						Log.e("", "### 未登录下获取到某个topic下的feed : " + response.result.size());
						for (FeedItem item : response.result) {
							Log.e("", "### topic feed id : " + item.id + ", name = " +
									item.text);
						}

					}

					@Override
					public void onStart() {
					}
				});

		// 推荐的feed
		mCommSDK.fetchRecommendedFeeds(new FetchListener<FeedsResponse>() {

			@Override
			public void onComplete(FeedsResponse response) {
				Log.e("", "### 推荐feed  code : " + response.errCode + ", msg = " + response.errMsg);
				for (FeedItem item : response.result) {
					Log.e("", "### 推荐feed id : " + item.id + ", name = " + item.text);
				}
			}

			@Override
			public void onStart() {

			}
		});

		// 获取推荐的话题
		mCommSDK.fetchRecommendedTopics(new FetchListener<TopicResponse>() {

			@Override
			public void onComplete(TopicResponse response) {
				Log.e("", "### 推荐的话题 : ");
				for (Topic item : response.result) {
					Log.e("", "### 话题 : " + item.name);
				}
			}

			@Override
			public void onStart() {

			}
		});

		// 获取某个话题活跃的用户
		mCommSDK.fetchActiveUsers("541fe6f40bbbaf4f41f7aa3f", new FetchListener<UsersResponse>() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(UsersResponse response) {
				Log.e("", "### 某个话题的活跃用户 : ");
				for (CommUser user : response.result) {
					Log.e("", "### 活跃用户 : " + user.name);
				}
			}
		});

		// 获取某用户的相册,也就是发布feed上传的所有图片
		mCommSDK.fetchAlbums(CommConfig.getConfig().loginedUser.id,
				new FetchListener<AlbumResponse>() {

					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(AlbumResponse response) {
						Log.e("", "### response size : " + response.result.size());
					}
				});

		// 搜索周边的feed
		mCommSDK.searchFeedNearby(116.3758540000f, 39.9856970000f,
				new FetchListener<FeedsResponse>() {

					@Override
					public void onComplete(FeedsResponse response) {
						Log.e("", "### 周边的feed : " + response.result.size());
					}

					@Override
					public void onStart() {

					}

				});
	}
	/**
	 * 点击事件
	 * @param view
	 */
	public void onTabSelect(View view) {
		switch (view.getId()) {
			case R.id.f1:
				index = 0;
				break;
			case R.id.f2:
				index = 1;
				break;
			case R.id.f3:
				index = 2;
				break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		currentTabIndex = index;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// 取消监听推送的消息
	}

	public void onMessage(BmobMsg message) {
		// TODO Auto-generated method stub
		refreshNewMsg(message);
	}
	/** 刷新界面
	 * @Title: refreshNewMsg
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshNewMsg(BmobMsg message){
		// 声音提示
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
		//也要存储起来
		if(message!=null){
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true,message);
		}
		if(currentTabIndex==0){
			//当前页面如果为会话页面，刷新此页面
			if(fragment1!= null){
				fragment1.refresh();
			}
		}
	}

	NewBroadcastReceiver  newReceiver;

	private void initNewMessageBroadCast(){
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}

	/**
	 * 新消息广播接收者
	 *
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//刷新界面
			refreshNewMsg(null);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}

	TagBroadcastReceiver  userReceiver;

	private void initTagMessageBroadCast(){
		// 注册接收消息广播
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}

	/**
	 * 标签消息广播接收者
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent.getSerializableExtra("invite");
			refreshInvite(message);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}

	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if(isNetConnected){
			Toast.makeText(MainActivity.this, R.string.network_tips,Toast.LENGTH_LONG).show();
		}
	}


	public void onAddUser(BmobInvitation message) {
		// TODO Auto-generated method stub
		refreshInvite(message);
	}

	/** 刷新好友请求
	 * @Title: notifyAddUser
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshInvite(BmobInvitation message){
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
		if(currentTabIndex==1){
			if(fragment2 != null){
				fragment2.refresh();
			}
		}else{
			//同时提醒通知
			String tickerText = message.getFromname()+"请求添加好友";
			boolean isAllowVibrate = CustomApplcation.getInstance().getSpUtil().isAllowVibrate();
			BmobNotifyManager.getInstance(this).showNotify(isAllow,isAllowVibrate, R.drawable.ic_launcher, tickerText, message.getFromname(), tickerText.toString(),NewFriendActivity.class);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			unregisterReceiver(newReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(userReceiver);
		} catch (Exception e) {
		}
		//取消定时检测服务
//		BmobChat.getInstance(this).stopPollService();
	}
	/**public void resetColor(){
		tv_talk.setTextColor(Color.rgb(56, 56, 56));
		tv_contact.setTextColor(Color.rgb(56,56,56));
		tv_settings.setTextColor(Color.rgb(56,56,56));
	}*/
	/*
	*获取控件宽
	 */
	public static int getWidth(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return (view.getMeasuredWidth());
	}

	/*
    * 获取控件高
    */
	public static int getHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return (view.getMeasuredHeight());
	}
	void moveHintsImage(View v,int x, int y) {

		ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(
				ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
		mp.setMargins(x, y, rl.getWidth()-(x+v.getWidth()), rl.getHeight()-(y+v.getHeight()));
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mp);
		v.setLayoutParams(params);

	}
}



