package com.tongxin.db;

import android.content.Context;
import android.database.Cursor;

public class place_search {
	   private String place_name[]={"北邮科技大厦",
			   "学十一公寓",
			   "学十公寓",
			   "学九公寓",
			   "留学生公寓",
			   "新食堂",
			   "学十三公寓",
			   "学五公寓",
			   "学八公寓",
			   "学三公寓",
			   "学四公寓",
			   "超市",
			   "学一公寓",
			   "学二公寓",
			   "鸿通楼",
			   "教四楼",
			   "移动营业厅",
			   "教三楼",
			   "校医院",
			   "书店",
			   "学六公寓",
			   "学生活动中心",
			   "数字媒体学院",
			   "综合服务楼",
			   "新科研楼",
			   "漫咖啡",
			   "学苑超市",
			   "水果店",
			   //"宽广电信",
			   "教工餐厅",
			   "学生食堂",
			   "保卫处",
			   "图书馆",
			   "篮球场",//"网球场"
			   "排球场",
			   "学二十九公寓",
			   "财务处",
			   "后勤楼",
			   "小白楼",
			   "教一楼",
			   "体育馆",
			   "游泳馆",
			   "主楼",
			   "科学会堂",
			   "教二楼",
			   "网络中心",
			   "风雨操场",
			   "北邮幼儿园",
			   "北邮东门",
			   "北邮西门",
			   "北邮南门",
			   "北邮北门",
			   "北邮中门",
			   "经管楼",
			   "校庆画展",
			   "叶培大铜像",
			   "蔡长年铜像",
			   "周炯槃铜像",
			   "虚拟校史馆"
	   };
	   private String platitude[]={"39.965330",
			    		"39.970493",
			    		"39.970132",
			    		"39.970079",
			    		"39.96979",
			    		"39.969678",
			    		"39.96894",
			    		"39.969332",
			    		"39.969261",
			    		"39.968765",
			    		"39.968767",
			    		"39.968254",
			    		"39.968318",
			    		"39.968351",
			    		"39.967955",
			    		"39.967663",
			    		"39.967195",
			    		"39.966312",
			    		"39.965737",
			    		"39.96541",
			    		"39.970421",
			    		"39.970022",
			    		"39.969768",
			    		"39.969835",
			    		"39.96993",
			    		"39.969465",
			    		"39.969465",
			    		"39.9696",
			    		//"39.969291",
			    		"39.969428",
			    		"39.969119",
			    		"39.969148",
			    		"39.968614",
			    		"39.968419",
			    		"39.968514",
			    		"39.968651",
			    		"39.967955",
			    		"39.96787",
			    		"39.967972",
			    		"39.967732",
			    		"39.967972",
			    		"39.968039",
			    		"39.967235",
			    		"39.967216",
			    		"39.966409",
			    		"39.96624",
			    		"39.966383",
			    		"39.965341",
					   "39.968239",//东门
					   "39.966905",//西门
					   "39.964000",//南
					   "39.970690",//北
					   "39.965570",//中
			           "39.970257",
			   "39.968614",
			   "39.967324",
			   "39.966682",
			   "39.966609",
			   "39.968614"
	   };
	   private String plongitude[]={"116.365665",
			    		"116.362398",
			    		"116.363195",
			    		"116.362303",
			    		"116.361811",
			    		"116.363035",
			    		"116.361852",
			    		"116.362429",
			    		"116.363258",
			    		"116.36242",
			    		"116.363339",
			    		"116.361859",
			    		"116.362436",
			    		"116.363305",
			    		"116.361883",
			    		"116.362905",
			    		"116.361906",
			    		"116.363108",
			    		"116.363636",
			    		"116.363292",
			    		"116.364738",
			    		"116.363909",
			    		"116.363952",
			    		"116.364251",
			    		"116.36547",
			    		"116.364102",
			    		"116.364102",
			    		"116.364798",
			    		//"116.364194",
			    		"116.36509",
			    		"116.365537",
			    		"116.366635",
			    		"116.364418",
			    		"116.365516",
			    		"116.366538",
			    		"116.367223",
			    		"116.363987",
			    		"116.36398",
			    		"116.364836",
			    		"116.364497",
			    		"116.366139",
			    		"116.367219",
			    		"116.364913",
			    		"116.365625",
			    		"116.364635",
			    		"116.365547",
			    		"116.366722",
			    		"116.365677",
					   "116.367519",//东门
					   "116.361979",//西
					   "116.364677",//南
					   "116.363638",//北
					   "116.364000",//中
			           "116.363909",
			   "116.364418",
			   "116.364387",
			   "116.363168",
			   "116.363985",
			   "116.364418"
	   };
	   private Context context;
	   private DbTool place_db = null;
	   public place_search(Context c) 
	   { 
			context =  c;	
	   }
	   /*Added by SPH*/
	   public place_search()
	   {
		   
	   }
	   
	   /*Added by SPH*/
	   public String[] getPlaceName()
	   {
		   return this.place_name;
	   }
	   /*Added by SPH*/
	   public String[] getPlatitude()
	   {
		   return this.platitude;
	   }
	   /*Added by SPH*/
	   public String[] getPlongitude()
	   {
		   return this.plongitude;
	   }
	   /*Added by SPH*/
	   public void Db_initial()
	   {
		   place_db = new DbTool(context);
			if(place_db.select("北邮科技大厦").getCount()<1)
			{
				  for(int i=0;i<=57;i++)
				  {
					  place_db.insert(place_name[i], platitude[i], plongitude[i]);
				  }
			}
	   }
	   public Cursor get_search(String placename)
	   {
		   return place_db.select(placename);
	   }
	   
}