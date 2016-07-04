package com.example.sortlistview;

/**
 * Created by Administrator on 2016/3/23.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.tongxin.e_guide.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weiso extends Activity {
    private GridView gridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private int[] icon = { R.drawable.quancunji, R.drawable.bdormitory,
            R.drawable.gdormitory, R.drawable.canteen, R.drawable.gym_,
            R.drawable.atm, R.drawable.park, R.drawable.bar,
            R.drawable.bicycle, R.drawable.concession_stand, R.drawable.academy,
            R.drawable.enrolling };
    private String[] iconName = { "圈存机", "男生宿舍", "女生宿舍", "食堂", "运动场所", "atm机", "停车场",
            "休闲吧", "借单车点", "校园超市", "教学楼", "医院" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weiso);
        gridview = (GridView) findViewById(R.id.gridview);
        data_list = new ArrayList<Map<String, Object>>();
        getData();
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.weiso_item, from, to);
        gridview.setAdapter(sim_adapter);
        // 添加列表项被单击的监听器

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //  @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String[] p=new String[19];
                int cupos0[]=new int[19];
                for(int i=0;i<19;i++)
                    cupos0[i]=-1;
                switch(position)
                {
                    case 0:cupos0[0]=17;cupos0[1]=5;cupos0[2]=6;cupos0[3]=29;cupos0[4]=23;cupos0[5]=24;cupos0[6]=4;cupos0[7]=1;cupos0[8]=2;cupos0[9]=3;cupos0[10]=7;
                        cupos0[11]=9;cupos0[12]=10;cupos0[13]=12;cupos0[14]=13;cupos0[15]=20;cupos0[16]=34;cupos0[17]=44;cupos0[18]=8;break;
                    case 1:cupos0[0]=2;cupos0[1]=20;cupos0[2]=7;cupos0[3]=8;cupos0[4]=12;cupos0[5]=13;break;
                    case 2:cupos0[0]=1;cupos0[1]=6;cupos0[2]=10;cupos0[3]=9;cupos0[4]=34;break;
                    case 3:cupos0[0]=28;cupos0[1]=5;cupos0[2]=29;break;
                    case 4:cupos0[0]=45;cupos0[1]=32;cupos0[2]=33;cupos0[3]=39;cupos0[4]=40;break;
                    case 5:cupos0[0]=14;break;
                    case 6:cupos0[0]=39;cupos0[1]=31;break;
                    case 7:cupos0[0]=25;break;
                    case 8:cupos0[0]=5;cupos0[1]=23;break;
                    case 9:cupos0[0]=11;cupos0[1]=27;cupos0[2]=26;break;
                    case 10:cupos0[0]=15;cupos0[1]=17;cupos0[2]=38;cupos0[3]=43;cupos0[4]=41;break;
                    case 11:cupos0[0]=18;break;
                    default:break;

                }
                for(int i=0;i<19;i++) {
                    p[i] = String.valueOf(cupos0[i]);
                }
                Intent intent=new Intent();
                intent.putExtra("position0", p[0]);
                intent.putExtra("position1",p[1]);
                intent.putExtra("position2",p[2]);
                intent.putExtra("position3",p[3]);
                intent.putExtra("position4",p[4]);
                intent.putExtra("position5",p[5]);
                intent.putExtra("position6",p[6]);
                intent.putExtra("position7",p[7]);
                intent.putExtra("position8",p[8]);
                intent.putExtra("position9",p[9]);
                intent.putExtra("position10",p[10]);
                intent.putExtra("position11",p[11]);
                intent.putExtra("position12",p[12]);
                intent.putExtra("position13",p[13]);
                intent.putExtra("position14",p[14]);
                intent.putExtra("position15",p[15]);
                intent.putExtra("position16",p[16]);
                intent.putExtra("position17",p[17]);
                intent.putExtra("position18",p[18]);
                setResult(2, intent);
                finish();
                //Toast toast=Toast.makeText(WeiSo.this,(position+1)+"有点困了",Toast.LENGTH_SHORT);
                //  toast.show();
            }
        });
    }
    public List<Map<String, Object>> getData(){
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }



}

