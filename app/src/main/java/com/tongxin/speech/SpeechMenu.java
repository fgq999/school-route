package com.tongxin.speech;

import com.tongxin.e_guide.AgentApp;
import com.tongxin.e_guide.R;
import com.tongxin.speak.synther;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.update.client;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View.OnClickListener;

public class SpeechMenu extends Activity {

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.speech_menu);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(SpeechMenu.this, R.layout.fruit_item, fruitList);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(
        // SpeechActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("name", position);
                intent.putExtras(bundle);
                intent.setClass(SpeechMenu.this, SpeechActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AgentApp.getInstance().addActivity(this);
    }

    private void initFruits() {

        Fruit bupthotel = new Fruit(R.drawable.bupthotel, "北邮科技大厦");
        fruitList.add(bupthotel);
        Fruit dorm11 = new Fruit(R.drawable.dorm11, "学十一公寓");
        fruitList.add(dorm11);
        Fruit dorm10 = new Fruit(R.drawable.dorm10, "学十公寓");
        fruitList.add(dorm10);
        Fruit dorm9 = new Fruit(R.drawable.dorm9, "学九公寓");
        fruitList.add(dorm9);
        Fruit dormforeigner = new Fruit(R.drawable.dormforeigner, "留学生公寓");
        fruitList.add(dormforeigner);
        Fruit newdininghall = new Fruit(R.drawable.newdiningroom, "新食堂");
        fruitList.add(newdininghall);
        Fruit dorm13 = new Fruit(R.drawable.dorm13, "学十三公寓");
        fruitList.add(dorm13);
        Fruit dorm5 = new Fruit(R.drawable.dorm5, "学五公寓");
        fruitList.add(dorm5);
        Fruit dorm8 = new Fruit(R.drawable.dorm8, "学八公寓");
        fruitList.add(dorm8);
        Fruit dorm3 = new Fruit(R.drawable.dorm3, "学三公寓");
        fruitList.add(dorm3);
        Fruit dorm4 = new Fruit(R.drawable.dorm4, "学四公寓");
        fruitList.add(dorm4);
        Fruit market = new Fruit(R.drawable.market, "小超市");
        fruitList.add(market);
        Fruit dorm1 = new Fruit(R.drawable.dorm1, "学一公寓");
        fruitList.add(dorm1);
        Fruit dorm2 = new Fruit(R.drawable.dorm2, "学二公寓");
        fruitList.add(dorm2);
        Fruit hongtonglou = new Fruit(R.drawable.hongtong_building, "鸿通楼");
        fruitList.add(hongtonglou);
        Fruit jiao4 = new Fruit(R.drawable.jiao4, "教四楼");
        fruitList.add(jiao4);
        Fruit yidong = new Fruit(R.drawable.china_mobile, "移动营业厅");
        fruitList.add(yidong);
        Fruit jiao3 = new Fruit(R.drawable.jiao3, "教三楼");
        fruitList.add(jiao3);
        Fruit hospital = new Fruit(R.drawable.hospital, "校医院");
        fruitList.add(hospital);
        Fruit bookstore = new Fruit(R.drawable.bookstore, "二手书店");
        fruitList.add(bookstore);
        Fruit dom6 = new Fruit(R.drawable.dorm6, "学六公寓");
        fruitList.add(dom6);
        Fruit xuehuo = new Fruit(R.drawable.students_act, "学活");
        fruitList.add(xuehuo);
        Fruit shumei = new Fruit(R.drawable.shumei, "数媒学院");
        fruitList.add(shumei);
        Fruit zonghelou = new Fruit(R.drawable.servicebuilding, "综合服务楼");
        fruitList.add(zonghelou);
        Fruit xinkeyanlou = new Fruit(R.drawable.newsciencebuilding, "新科研楼");
        fruitList.add(xinkeyanlou);
        Fruit manka = new Fruit(R.drawable.coffeebar, "漫咖啡");
        fruitList.add(manka);
        Fruit xueyuan = new Fruit(R.drawable.xueyuan, "学苑超市");
        fruitList.add(xueyuan);
        Fruit fruitstore = new Fruit(R.drawable.fruitstore, "水果店");
        fruitList.add(fruitstore);
       // Fruit kuanguang = new Fruit(R.drawable.kuanguang1, "宽广电信");
        //fruitList.add(kuanguang);
        Fruit staffdininghall = new Fruit(R.drawable.stuffdiningroom, "教工餐厅");
        fruitList.add(staffdininghall);
        Fruit studininghall = new Fruit(R.drawable.olddiningroom, "学生食堂");
        fruitList.add(studininghall);
        Fruit baoweichu = new Fruit(R.drawable.security_office, "保卫处");
        fruitList.add(baoweichu);
        Fruit library = new Fruit(R.drawable.library, "图书馆");
        fruitList.add(library);
        Fruit basketball = new Fruit(R.drawable.basketball, "篮球场");
        fruitList.add(basketball);
        Fruit volleyball = new Fruit(R.drawable.volleyball, "网球场");
        fruitList.add(volleyball);
        Fruit dorm29 = new Fruit(R.drawable.dorm29, "学二十九公寓");
        fruitList.add(dorm29);
        Fruit caiwuchu = new Fruit(R.drawable.finance_office, "财务处");
        fruitList.add(caiwuchu);
        Fruit houqin = new Fruit(R.drawable.houqin, "后勤楼");
        fruitList.add(houqin);
        Fruit xiaobailou = new Fruit(R.drawable.xiaobailou, "小白楼");
        fruitList.add(xiaobailou);
        Fruit jiao1 = new Fruit(R.drawable.jiao1, "教一楼");
        fruitList.add(jiao1);
        Fruit gym = new Fruit(R.drawable.gym, "体育馆");
        fruitList.add(gym);
        Fruit youyongguan = new Fruit(R.drawable.natatorium, "游泳馆");
        fruitList.add(youyongguan);
        Fruit mainbuilding = new Fruit(R.drawable.mainbuilding, "主楼");
        fruitList.add(mainbuilding);
        Fruit kehui = new Fruit(R.drawable.hall, "科学会堂");
        fruitList.add(kehui);
        Fruit jiao2 = new Fruit(R.drawable.jiao2, "教二楼");
        fruitList.add(jiao2);
        Fruit netcenter = new Fruit(R.drawable.netcenter, "网络中心");
        fruitList.add(netcenter);
        Fruit sportsground = new Fruit(R.drawable.sports_ground, "风雨操场");
        fruitList.add(sportsground);
        Fruit kindergarten = new Fruit(R.drawable.kindergarten, "幼儿园");
        fruitList.add(kindergarten);
        Fruit east_door=new Fruit(R.drawable.east_door,"北京邮电大学东门");
        fruitList.add(east_door);
        Fruit west_door=new Fruit(R.drawable.west_door,"北京邮电大学西门");
        fruitList.add(west_door);
        Fruit south_door=new Fruit(R.drawable.south_door,"北京邮电大学南门");
        fruitList.add(south_door);
        Fruit north_door=new Fruit(R.drawable.north_door,"北京邮电大学北门");
        fruitList.add(north_door);
        Fruit mid_door=new Fruit(R.drawable.mid_door,"北京邮电大学中门");
        fruitList.add(mid_door);
        Fruit jingguanlou=new Fruit(R.drawable.jingguanlou,"经管楼");
        fruitList.add(jingguanlou);
        Fruit xiaoqinghuazhan=new Fruit(R.drawable.xiaoqinghuazhan,"校庆书画展");
        fruitList.add(xiaoqinghuazhan);
        Fruit yepeida=new Fruit(R.drawable.yepeida,"叶培大铜像");
        fruitList.add(yepeida);
        Fruit caichangnian=new Fruit(R.drawable.caichangnian,"蔡长年铜像");
        fruitList.add(caichangnian);
        Fruit zhoujiongpan=new Fruit(R.drawable.zhoujiongpan,"周炯槃铜像");
        fruitList.add(zhoujiongpan);
        Fruit xunixiaoshiguan=new Fruit(R.drawable.xunixiaoshiguan,"虚拟校史馆");
        fruitList.add(xunixiaoshiguan);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.setClass(SpeechMenu.this, SpeechActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
