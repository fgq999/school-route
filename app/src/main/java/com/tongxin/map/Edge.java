package com.tongxin.map;
public class Edge implements Comparable<Edge> {
	private final int v;
	private final int w;
	private final double distance;
	
	public  Edge(int v,int w,double distance)
	{
		this.v=v;
		this.w=w;
		this.distance=distance;
	}
	
	public double distance()
	{
		return distance;
	}
	
	public int either()
	{
		return v;
	}
	
	public int other(int vertex)
	{
		if(vertex==v) return w;
		else if(vertex==w) return v;
		else throw new RuntimeException("Inconsistent edge");
	}
	

	public int compareTo(Edge that) {
		if(that.distance()>this.distance()) return -1;
		else if(that.distance()<this.distance()) return 1;
		else return 0;
	}
	
	public String toString() {
		return String.format("%d-%d %.2f", v,w,distance);
	}
  
}
