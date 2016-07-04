package com.tongxin.map;
import java.util.Vector;
public class EdgeWeightedGraph {
    private final int V;//顶点的个数
    private int E;//边数
    private Vector<Edge>[] adj;//用于保存每一个端点对应的边
    public EdgeWeightedGraph(int V)//构造函数
    {
        this.V=V;
        this.E=0;
        adj=(Vector<Edge>[])new Vector[V];
        for(int i=0;i<V;i++)
            adj[i]=new Vector<Edge>();
    }
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }
    public int V()
    {
        return V;
    }

    public int E()
    {
        return E;
    }
    public void addEdge(Edge e)
    {
        int v=e.either();
        int w=e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }
    public Iterable<Edge> adj(int v)
    {
        return adj[v];
    }
}
