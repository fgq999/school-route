package com.lostfound;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lostfound.bean.Tell;
import com.tongxin.e_guide.R;

import cn.bmob.v3.listener.SaveListener;

/**
 * 添加失物/招领信息界面
 *
 * @ClassName: AddActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-21 上午11:41:06
 */
public class AddActivity1 extends BaseActivity implements OnClickListener {

    EditText edit_title, edit_name, edit_describe;
    Button btn_back, btn_true;
    TextView tv_add;
    String from = "";
    String old_title = "";
    String old_describe = "";

    String old_name = "";
    @Override
    public void setContentView() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_add1);
    }

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        tv_add = (TextView) findViewById(R.id.tv_add);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_true = (Button) findViewById(R.id.btn_true);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_describe = (EditText) findViewById(R.id.edit_describe);
        edit_title = (EditText) findViewById(R.id.edit_title);
    }

    @Override
    public void initListeners() {
        // TODO Auto-generated method stub
        btn_back.setOnClickListener(this);
        btn_true.setOnClickListener(this);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        from = getIntent().getStringExtra("from");
        old_title = getIntent().getStringExtra("title");
        old_name= getIntent().getStringExtra("name");
        old_describe = getIntent().getStringExtra("describe");

        edit_title.setText(old_title);
        edit_describe.setText(old_describe);
        edit_name.setText(old_name);
        tv_add.setText("邮苑情话");
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btn_true) {
            addByType();
        } else if (v == btn_back) {
            finish();
        }
    }
    String title = "";
    String describe = "";
    String name="";

    /**根据类型添加失物/招领
     * addByType
     * @Title: addByType
     * @throws
     */
    private void addByType(){
        title = edit_title.getText().toString();
        name = edit_name.getText().toString();
        describe = edit_describe.getText().toString();
        if(TextUtils.isEmpty(title)){
            ShowToast("请填写标题");
            return;
        }
        if(TextUtils.isEmpty(name)){
            ShowToast("请填写昵称");
            return;
        }
        if(TextUtils.isEmpty(describe)){
            ShowToast("请填写想说的话");
            return;
        }
        addTell();
    }

    private void addTell(){
        Tell tell = new Tell();
        tell.setDescribe(describe);
        tell.setName(name);
        tell.setTitle(title);
        tell.save(this, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ShowToast("表白信息添加成功!");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub
                ShowToast("添加失败:"+arg0);
            }
        });
    }


}
