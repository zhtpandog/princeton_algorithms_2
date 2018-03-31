import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class SAP {

    private Digraph graph;
    private int minLength;
    private int minAncestor;
    private int currV = -1, currW = -1;
    private BreadthFirstDirectedPaths bfsV, bfsW;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        boolean flag = false;

        if (v != currV) {
            bfsV = new BreadthFirstDirectedPaths(graph, v);
            currV = v;
            flag = true;
        }
        if (w != currW) {
            bfsW = new BreadthFirstDirectedPaths(graph, w);
            currW = w;
            flag = true;
        }

        // find all vertices that v can reach and associated distance
        if (flag) {
            int minDist = Integer.MAX_VALUE;
            int lowestAncestor = -1;
            for (int ver = 0; ver < graph.V(); ver++) {
                if (bfsV.hasPathTo(ver) && bfsW.hasPathTo(ver)) {
                    int dist = bfsV.distTo(ver) + bfsW.distTo(ver);
                    if (dist < minDist) {
                        minDist = dist;
                        lowestAncestor = ver;
                    }
                }
            }

            minAncestor = lowestAncestor;
            if (lowestAncestor == -1) minLength = -1;
            else minLength = minDist;
        }

        return minLength;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        boolean flag = false;

        if (v != currV) {
            bfsV = new BreadthFirstDirectedPaths(graph, v);
            currV = v;
            flag = true;
        }
        if (w != currW) {
            bfsW = new BreadthFirstDirectedPaths(graph, w);
            currW = w;
            flag = true;
        }

        if (flag) {
            int minDist = Integer.MAX_VALUE;
            int lowestAncestor = -1;
            for (int ver = 0; ver < graph.V(); ver++) {
                if (bfsV.hasPathTo(ver) && bfsW.hasPathTo(ver)) {
                    int dist = bfsV.distTo(ver) + bfsW.distTo(ver);
                    if (dist < minDist) {
                        minDist = dist;
                        lowestAncestor = ver;
                    }
                }
            }

            minAncestor = lowestAncestor;
            if (lowestAncestor == -1) minLength = -1;
            else minLength = minDist;
        }

        return minAncestor;
    }

//    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
//    public int length(Iterable<Integer> v, Iterable<Integer> w) {
//
//    }
//
//    // a common ancestor that participates in shortest ancestral path; -1 if no such path
//    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
//
//    }

    // do unit testing of this class
    public static void main(String[] args) {

        In in = new In("wordnet/digraph1.txt");

//        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int[] v = new int[]{3, 9, 7, 1};
        int[] w = new int[]{11, 12, 2, 6};

        for (int i = 0; i < 4; i++) {
            int length   = sap.length(v[i], w[i]);
            int ancestor = sap.ancestor(v[i], w[i]);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
    }
}
