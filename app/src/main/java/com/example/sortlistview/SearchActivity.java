package com.example.sortlistview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.tongxin.e_guide.AgentApp;
import com.tongxin.e_guide.R;

public class SearchActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initViews();
		AgentApp.getInstance().addActivity(this);
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
				String p=null;
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				//Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				int cupos=0;
				String PINYIN=characterParser.getSelling(((SortModel) adapter.getItem(position)).getName());
				switch (PINYIN){
					case "beiyoukejidaxia":cupos=0;break;
					case "baoweichu":cupos=30;break;
					case "beiyouyoueryuan":cupos=46;break;
					case "beiyoudongmen":cupos=47;break;
					case "beiyouximen":cupos=48;break;
					case "beiyounanmen":cupos=49;break;
					case "beiyoubeimen":cupos=50;break;
					case "beiyouzhongmen":cupos=51;break;
					case "chaoshi":cupos=11;break;
					case "caiwuchu":cupos=35;break;
					case "fengyucaochang":cupos=45;break;
					case "hongtonglou":cupos=14;break;
					case "houqinlou":cupos=36;break;
					case "jiaosilou":cupos=15;break;
					case "jiaosanlou":cupos=17;break;
					case "jiaogongcanting":cupos=28;break;
					case "jiaoyilou":cupos=38;break;
					case "jiaoerlou":cupos=43;break;
					//case "kuanguangdianxin":cupos=28;break;
					case "kexuehuitang":cupos=42;break;
					case "liuxueshenggongyu":cupos=4;break;
					case "lanqiuchang":cupos=32;break;
					case "mankafei":cupos=25;break;
					case "paiqiuchang":cupos=33;break;
					case "shudian":cupos=19;break;
					case "shuzimeitixueyuan":cupos=22;break;
					case "shuiguodian":cupos=27;break;
					case "tushuguan":cupos=31;break;
					case "tiyuguan":cupos=39;break;
					case "wangluozhongxin":cupos=44;break;
					case "xueshiyigongyu":cupos=1;break;
					case "xueshigongyu":cupos=2;break;
					case "xuejiugongyu":cupos=3;break;
					case "xinxueshengcanting":cupos=5;break;
					case "xueshisangongyu":cupos=6;break;
					case "xuewugongyu":cupos=7;break;
					case "xuebagongyu":cupos=8;break;
					case "xuesangongyu":cupos=9;break;
					case "xuesigongyu":cupos=10;break;
					case "xueyigongyu":cupos=12;break;
					case "xueergongyu":cupos=13;break;
					case "xiaoyiyuan":cupos=18;break;
					case "xueliugongyu":cupos=20;break;
					case "xueshenghuodongzhongxin":cupos=21;break;
					case "xinkeyanlou":cupos=24;break;
					case "xueyuanchaoshi":cupos=26;break;
					case "xueshengshitang":cupos=29;break;
					case "xueershijiugongyu":cupos=34;break;
					case "xiaobailou":cupos=37;break;
					case "yidongyingyeting":cupos=16;break;
					case "youyongguan":cupos=40;break;
					case "zonghefuwulou":cupos=23;break;
					case "zhulou":cupos=41;break;
					case "jingguanlou":cupos=52;break;
					case "xiaoqingshuhuazhan":cupos=53;break;
					case "yepeidatongxiang":cupos=54;break;
					case "caichangniantongxiang":cupos=55;break;
					case "xunixiaoshiguan":cupos=57;break;
					default:cupos=56;break;
				}
				p=String.valueOf(cupos);
				//Toast.makeText(getApplication(),po,Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				intent.putExtra("position", p);
				setResult(1, intent);
				finish();
				//position是当前点击的那个item的int类型，是传递给MapActivity类的
			}
		});

		SourceDateList = filledData(getResources().getStringArray(R.array.date));

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
