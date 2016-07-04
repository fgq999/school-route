package com.tongxin.map;
import java.io.File;
import java.util.Stack;
import java.util.Vector;
public class Find {
	public static void main(String[] args) {
		Vector<Place> v=find(39.9710,116.3671,39.966461,116.36220);
		for(Place e:v)
			System.out.println(e.id+" "+e.latitude+" "+e.longitude);
	}//该main函数为测试用
	public static Vector<Place> find(double sla,double slo,double ela,double elo)//sla,slo表示起点的纬度和经度，ela和elo为终点的纬度和经度
	{
		EdgeWeightedGraph bupt;
		String str1=android.os.Environment.getExternalStorageDirectory()+"/map/bupt.txt";
		bupt=new EdgeWeightedGraph(new In(str1));//导入图bupt
		Place[] place;//place是一个储存图中所有点的数组，且每个place中有经度和纬度，还有一个专门用于调试的id，这是每个顶点的编号
		String str2=android.os.Environment.getExternalStorageDirectory()+"/map/node.txt";
		place=initial(new In(str2));//初始化
		int s=jude(place,sla,slo);
		int e=jude(place,ela,elo);//jude函数用于判断起点和终点分别靠经哪个十字路口
		DijkstraSP sp=new DijkstraSP(bupt,s,e);
		int sum=sp.pathTo().size();//pathTo()函数返回该路径经过的点的数目
		Stack<Integer> pathnum=sp.pathTo();//pathnum为一个Stack变量，里面储存该路径的上的所有点的编号，根据编号可以再place数组中找到一一对应的Place变量
		Vector<Place> path=new Stack<Place>();
		path.add(new Place(-1,sla,slo));
		for(int i=0;i<sum;i++)
		{
			path.add(place[pathnum.pop()]);
		}
		path.add(new Place(45,ela,elo));
		if(sum>1)
		{
			if(Math.abs(path.elementAt(1).id-path.elementAt(2).id)==1)
			{
				double longitude=path.elementAt(1).longitude;
				int    index=path.elementAt(1).id;
				path.remove(1);
				path.add(1, new Place(index,sla,longitude));
			}
			else
			{
				double latitude=path.elementAt(1).latitude;
				int    index=path.elementAt(1).id;
				path.remove(1);
				path.add(1,new Place(index,latitude,slo));
			}
			int end=path.size()-1;
			if(Math.abs(path.elementAt(end-1).id-path.elementAt(end-2).id)==1)
			{
				double longitude=path.elementAt(end-1).longitude;
				int    index=path.elementAt(end-1).id;
				path.remove(end-1);
				path.add(end-1, new Place(index,ela,longitude));
			}
			else
			{
				double latitude=path.elementAt(end-1).latitude;
				int    index=path.elementAt(end-1).id;
				path.remove(end-1);
				path.add(end-1,new Place(index,latitude,elo));
			}
		}
		return path;
	}
	public static Place[] initial(In in)
	{
		int count=in.readInt();
		Place[] node=new Place[count];
		for(int i=0;i<count;i++)
		{
			node[i]=new Place(in.readInt(),in.readDouble(),in.readDouble());
		}
		return node;
	}
	public static int jude(Place[] place,double la,double lo)
	{
		double distance=Double.POSITIVE_INFINITY;
		int near = 0;
		double temd;
		for(int i=0;i<place.length;i++)
		{
			temd=Math.sqrt((la-place[i].latitude)*(la-place[i].latitude)+(lo-place[i].longitude)*(lo-place[i].longitude));
			if(temd<distance)
			{
				near=i;
				distance=temd;
			}
		}
		return near;
	}
}