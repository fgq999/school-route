package com.example.update;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.DataInputStream;

import android.util.Log;
import android.os.Environment;
import android.os.Handler;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sortlistview.*;
import com.example.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.tongxin.e_guide.MainActivity;
import com.tongxin.e_guide.R;

import android.os.Message;



public class update extends Activity {
	private ProgressDialog mSaveDialog = null;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private ProgressDialog dialog1;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private String SDPATH = Environment.getExternalStorageDirectory() + "/";
	private boolean f=false;
	public static String  banben;
	private String searchMap[] =
			{
					"Peking","beishida","beijiao","beiying","beili","beike","beihang","beigong","beihua","beiyou",
					"duiwaijingmao","huabeidianli","Qinghua","shoudushifan","zhongguochuanmei","renda","yangcai","nongda",
					"mingzudaxue","zhengfa"
			};

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
				{
					// mSaveDialog.dismiss();
					dialog1.dismiss();
					// Toast.makeText(update.this, "下载成功,快去使用吧！",Toast.LENGTH_LONG ).show();
					Toast toast = Toast.makeText(getApplicationContext(),
							"下载成功，快去使用吧！", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					Intent intent=new Intent();
					intent.setClass(update.this, MainActivity.class);
					startActivity(intent);
					update.this.finish();
					break;
				}
				case 2:
				{
					dialog1.dismiss();
					Toast toast = Toast.makeText(getApplicationContext(),
							"更新服务器未开启！请稍后再试", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					break;
				}




			}
			super.handleMessage(msg);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initViews();
	}

	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}

			}
		});
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			//@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				String b=null;
				int p=0;
				if (characterParser.getSelling(((SortModel) adapter.getItem(position)).getName()).equals("beijingyoudiandaxue")) p=9;
					b=searchMap[p];

				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				//Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				//	client cl=new client(b);
				//	cl.run();
				if(b.equals("beiyou"))

				{
					String localFile = SDPATH + "beiyou";
					File file = new File(localFile);
					if (file.exists()) {
						Toast toast = Toast.makeText(getApplicationContext(),
								"您已下载过北邮版本,快去使用吧！", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						//Toast.makeText(update.this, "您已下载过北邮版本",Toast.LENGTH_LONG ).show();

					}
					else{

						//Toast.makeText(update.this, "你选择下载北邮版本",Toast.LENGTH_LONG ).show();


						//	mSaveDialog = ProgressDialog.show(update.this, "下载更新版本", "正在下载，请稍等...", true);



						banben=b;
						//      tt.start();
						//   new Thread(saveFileRunnable).start();
						client cl=new client(b,myHandler);
						cl.start();
						dialog1 = new ProgressDialog(update.this);
						dialog1.setTitle("北邮版本");
						dialog1.setMessage("正在下载，请稍后...");
						dialog1.setCancelable(false);
						dialog1.show();

					}
				}
				else

				{
					// Toast.makeText(update.this,"该版本目前还不能使用！非常抱歉!您可以先下载北邮版本",Toast.LENGTH_LONG ).show();
					Toast toast = Toast.makeText(getApplicationContext(),
							"该版本目前还不能使用！非常抱歉！您可以先下载北邮版本", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				}


			}



		});

		SourceDateList = filledData(getResources().getStringArray(R.array.gaoxiao));
		//SourceDateList = filledData(getResources().getStringArray(R.array.wuyu));

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);


		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		//根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}

	
	












