package com.tongxin.map;
import java.util.Stack;
public class DijkstraSP {
  private Edge[] edgeTo;
  private double[] disTo;
  private IndexMinPQ<Double> pq;
  private int end;

  public DijkstraSP(EdgeWeightedGraph G,int s,int e)
  {
	  end=e;
	  edgeTo=new Edge[G.V()];
	  disTo=new double[G.V()];
	  pq=new IndexMinPQ<Double>(G.V());
	  for(int v=0;v<G.V();v++)
		  disTo[v]=Double.POSITIVE_INFINITY;
	  disTo[s]=0.0;
	  pq.insert(s,0.0);
	  while(!pq.isEmpty())
	  {
		  int v=pq.delMin();
		  if(v==end)
		  break;
		  else 
		  relax(G,v);
	  }
  }
  private void relax(EdgeWeightedGraph G,int v)
  {
	 for(Edge e:G.adj(v)) 
	 {
		 int w=e.other(v);
		 if(disTo[w]>disTo[v]+e.distance())
		 {
			 disTo[w]=disTo[v]+e.distance();
			 edgeTo[w]=e;
			 if(pq.contains(w)) pq.change(w, disTo[w]);
			 else pq.insert(w,disTo[w]);
		 }
	 }
  }
  public Stack<Integer> pathTo()
  {
	 Stack<Integer> path=new Stack<Integer>();
	 int v=end;
	 path.push(v);
	 for(Edge e=edgeTo[end];e!=null;e=edgeTo[v])
	 {
		 v=e.other(v);
		 path.push(v);
	 }
	return path;  
  }
}
