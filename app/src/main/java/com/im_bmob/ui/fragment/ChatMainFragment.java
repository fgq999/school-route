package com.im_bmob.ui.fragment;

/**
 * Created by Administrator on 2016/3/27.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tongxin.e_guide.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMainFragment extends Fragment implements OnClickListener{
    private View view;
    private ViewPager mPaper;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private TextView tv_talk,tv_contact,tv_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatmain, container,false);

        initLayout();

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mPaper.setAdapter(mAdapter);
        mPaper.setOnPageChangeListener(new OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetColor();
                switch (position) {
                    case 0:
                        tv_talk.setTextColor(Color.rgb(87,153,8));
                        break;
                    case 1:
                        tv_contact.setTextColor(Color.rgb(87,153,8));
                        break;
                    case 2:
                        tv_settings.setTextColor(Color.rgb(87,153,8));

                        break;
                    default:
                        tv_talk.setTextColor(Color.rgb(87,153,8));
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        return view;
    }

    /**
     * 初始化控件
     */
    public void initLayout(){
        tv_talk = (TextView)view.findViewById(R.id.tv_talk);
        tv_contact = (TextView)view.findViewById(R.id.tv_contact);
        tv_settings = (TextView)view.findViewById(R.id.tv_settings);

        mPaper = (ViewPager)view.findViewById(R.id.view_pager1);

        tv_talk.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        tv_settings.setOnClickListener(this);

        RecentFragment f1 = new RecentFragment();
        ContactFragment f2 = new ContactFragment();
        SettingsFragment f3 = new SettingsFragment();

        mFragments.add(f1);
        mFragments.add(f2);
        mFragments.add(f3);
    }

    public void resetColor(){
        tv_talk.setTextColor(Color.rgb(56,56,56));
        tv_contact.setTextColor(Color.rgb(56,56,56));
        tv_settings.setTextColor(Color.rgb(56,56,56));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_talk:
                resetColor();
                tv_talk.setTextColor(Color.rgb(87,153,8));
                mPaper.setCurrentItem(0);
                break;
            case R.id.tv_contact:
                resetColor();
                tv_contact.setTextColor(Color.rgb(87,153,8));
                mPaper.setCurrentItem(1);
                break;
            case R.id.tv_settings:
                resetColor();
                tv_settings.setTextColor(Color.rgb(87,153,8));
                mPaper.setCurrentItem(2);
                break;

            default:
                break;
        }
    }
    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        public List<Activity> mListViews;

        public MyPagerAdapter(List<Activity> mListViews) {
            this.mListViews = mListViews;
        }


        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }



        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
}

