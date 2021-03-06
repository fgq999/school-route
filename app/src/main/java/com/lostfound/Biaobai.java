package com.lostfound;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lostfound.adapter.BaseAdapterHelper;
import com.lostfound.adapter.QuickAdapter;
import com.lostfound.bean.Tell;
import com.lostfound.config.Constants;
import com.tongxin.e_guide.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import static com.tongxin.e_guide.R.id.tv_describe;
import static com.tongxin.e_guide.R.id.tv_people;
import static com.tongxin.e_guide.R.id.tv_title;

/**
 * Lost/Found
 *
 * @ClassName: MainActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-21 上午11:12:36
 */
public class Biaobai extends BaseActivity implements OnClickListener
{

    RelativeLayout layout_action;//
    LinearLayout layout_all;
    TextView tv_lost;
    ListView listview;
    Button btn_add;
    public static final int REQUEST_CODE1 =1;
    protected QuickAdapter<Tell> TellAdapter;// 表白
    private Button layout_lost;
    PopupWindow morePop;
    RelativeLayout progress;
    LinearLayout layout_no;
    TextView tv_no;
    @Override
    public void setContentView() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_biaobai);
    }

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        progress = (RelativeLayout) findViewById(R.id.progress);
        layout_no = (LinearLayout) findViewById(R.id.layout_no);
        tv_no = (TextView) findViewById(R.id.tv_no);
        layout_action = (RelativeLayout) findViewById(R.id.layout_action);
        layout_all = (LinearLayout) findViewById(R.id.layout_all);
        // 默认是失物界面
        tv_lost = (TextView) findViewById(R.id.tv_lost);
        tv_lost.setTag("Tell");
        listview = (ListView) findViewById(R.id.list_lost);
        btn_add = (Button) findViewById(R.id.btn_add);
        // 初始化长按弹窗
        //initEditPop();
    }

    @Override
    public void initListeners() {
        // TODO Auto-generated method stub
        // listview.setOnItemLongClickListener(this);
        btn_add.setOnClickListener(this);
        layout_all.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == layout_all) {
            //  showListPop();
        } else if (v == btn_add) {
            Intent intent = new Intent(this, AddActivity1.class);
            intent.putExtra("from", tv_lost.getTag().toString());
            startActivityForResult(intent, Constants.REQUESTCODE_ADD);
        }/* else if (v == layout_lost) {
            changeTextView(v);
            morePop.dismiss();
            queryLosts();
        }*/
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        if (TellAdapter == null) {
            TellAdapter = new QuickAdapter<Tell>(this, R.layout.item_list1) {
                @Override
                protected void convert(BaseAdapterHelper helper, Tell tell) {
                    helper.setText(tv_title, tell.getTitle())
                            .setText(tv_describe, tell.getDescribe())
                            .setText(tv_people,tell.getName());
                }
            };
            listview.setAdapter(TellAdapter);
            // 默认加载失物界面
            queryTells();
        }
    }
    /*  private void changeTextView(View v) {
          if (v == layout_found) {
              tv_lost.setTag("Found");
              tv_lost.setText("Found");
          } else {
              tv_lost.setTag("Lost");
              tv_lost.setText("Lost");
          }
      }

      @SuppressWarnings("deprecation")
      private void showListPop() {
          View view = LayoutInflater.from(this).inflate(R.layout.pop_lost, null);
          // 注入
        //  layout_found = (Button) view.findViewById(R.id.layout_found);
          layout_lost = (Button) view.findViewById(R.id.layout_lost);
        //  layout_found.setOnClickListener(this);
          layout_lost.setOnClickListener(this);
          morePop = new PopupWindow(view, mScreenWidth, 600);

          morePop.setTouchInterceptor(new OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                      morePop.dismiss();
                      return true;
                  }
                  return false;
              }
          });

          morePop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
          morePop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
          morePop.setTouchable(true);
          morePop.setFocusable(true);
          morePop.setOutsideTouchable(true);
          morePop.setBackgroundDrawable(new BitmapDrawable());
          // 动画效果 从顶部弹下
          morePop.setAnimationStyle(R.style.MenuPop);
          morePop.showAsDropDown(layout_action, 0, -dip2px(this, 2.0F));
      }

      private void initEditPop() {
          mPopupWindow = new EditPopupWindow(this, 200, 48);
          mPopupWindow.setOnPopupItemClickListner(this);
      }

      EditPopupWindow mPopupWindow;

      @Override
      public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                     long arg3) {
          // TODO Auto-generated method stub
          position = arg2;
          int[] location = new int[2];
          arg1.getLocationOnScreen(location);
          mPopupWindow.showAtLocation(arg1, Gravity.RIGHT | Gravity.TOP,
                  location[0], getStateBar() + location[1]);
          return false;
      }   */
    int position;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.REQUESTCODE_ADD:// ��ӳɹ�֮��Ļص�
                    queryTells();
                break;
        }
    }


    /**
     * 查询全部失物信息 queryLosts
     *
     * @return void
     * @throws
     */
    private void queryTells() {
        showView();
        BmobQuery<Tell> query = new BmobQuery<Tell>();
        query.order("-createdAt");// ����ʱ�併��
        query.findObjects(this, new FindListener<Tell>() {

            @Override
            public void onSuccess(List<Tell> tells) {
                // TODO Auto-generated method stub
                TellAdapter.clear();
                //  FoundAdapter.clear();
                if (tells == null || tells.size() == 0) {
                    showErrorView(0);
                    TellAdapter.notifyDataSetChanged();
                    return;
                }
                progress.setVisibility(View.GONE);
                TellAdapter.addAll(tells);
                listview.setAdapter(TellAdapter);
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                showErrorView(0);
            }
        });
    }
    /**
     * 请求出错或者无数据时候显示的界面 showErrorView
     *
     * @return void
     * @throws
     */
    private void showErrorView(int tag) {
        progress.setVisibility(View.GONE);
        listview.setVisibility(View.GONE);
        layout_no.setVisibility(View.VISIBLE);
        if (tag == 0) {
            tv_no.setText("暂时还未有表白信息");
        }
    }

    private void showView() {
        listview.setVisibility(View.VISIBLE);
        layout_no.setVisibility(View.GONE);
    }

    public void onEdit(View v) {
        // TODO Auto-generated method stub
        String tag = tv_lost.getTag().toString();
        Intent intent = new Intent(this, AddActivity1.class);
        String title = "";
        String describe = "";
        String name = "";
        title = TellAdapter.getItem(position).getTitle();
        describe = TellAdapter.getItem(position).getDescribe();
        name = TellAdapter.getItem(position).getName();
        intent.putExtra("describe", describe);
        intent.putExtra("name", name);
        intent.putExtra("title", title);
        intent.putExtra("from", tag);
        startActivityForResult(intent, Constants.REQUESTCODE_ADD);
    }
    public void onDelete(View v) {
        // TODO Auto-generated method stub
        String tag = tv_lost.getTag().toString();
        if (tag.equals("Tell")) {
            deleteTell();
        }
    }
    private void deleteTell() {
        Tell tell = new Tell();
        tell.setObjectId(TellAdapter.getItem(position).getObjectId());
        tell.delete(this, new DeleteListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                TellAdapter.remove(position);
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


}

