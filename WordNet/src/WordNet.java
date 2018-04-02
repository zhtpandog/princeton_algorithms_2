import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WordNet {

    private final HashMap<String, ArrayList<Integer>> nounSynsetsMap;
    private final HashMap<Integer, String> nounSynsetsIdMap;
    private final Digraph graph;
    private BreadthFirstDirectedPaths bfsVIter, bfsWIter;
    private Iterable<Integer> currVIter = null, currWIter = null;
    private int minLengthIter, minAncestorIter;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new java.lang.IllegalArgumentException();

        // Build the nounSynsetsMap(HashMap) for synsets lookup. Key(String) is word, value(ArrayList<Integer>) is wordId in synsets
        In nounSynsetsIn = new In(synsets);
        nounSynsetsMap = new HashMap<>();
        nounSynsetsIdMap = new HashMap<>();
        while (nounSynsetsIn.hasNextLine()) {
            String[] synsetRecord = nounSynsetsIn.readLine().split(",");
            int synsetId = Integer.parseInt(synsetRecord[0]);

            String synsetStr = synsetRecord[1];
            nounSynsetsIdMap.put(synsetId, synsetStr);

            for (String word: synsetRecord[1].split(" ")) {
                if (nounSynsetsMap.containsKey(word)) nounSynsetsMap.get(word).add(synsetId);
                else nounSynsetsMap.put(word, new ArrayList<>(Collections.singletonList(synsetId)));
            }
        }

        int numVertices = nounSynsetsIdMap.size();

        // build the adjacency list graphAdjList
//        ArrayList<ArrayList<Integer>> graphAdjList = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> graphAdjList= new HashMap<>();

        In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine()) {
            String[] hypernymRelation = hypernymsIn.readLine().split(",");
            int from = Integer.parseInt(hypernymRelation[0]);
            ArrayList<Integer> neighbors = new ArrayList<>(); // nodes that "pointed to", aka hypernym
            for (int i = 1; i < hypernymRelation.length; i++) {
                neighbors.add(Integer.parseInt(hypernymRelation[i]));
            }
            graphAdjList.put(from, neighbors);
        }

//        // validate graphAdjList
//        for (Map.Entry<Integer, ArrayList<Integer>> entry: graphAdjList.entrySet()) {
//            System.out.print(entry.getKey() + ": ");
//            for (int j: entry.getValue()) {
//                System.out.print(j + " ");
//            }
//            System.out.println();
//        }

        // create directed graph called graph
        graph = new Digraph(numVertices);
        for (Map.Entry<Integer, ArrayList<Integer>> entry: graphAdjList.entrySet()) {
            int origin = entry.getKey();
            for (int dest: entry.getValue()) {
                graph.addEdge(origin, dest);
            }
        }

        // validate this is a rooted graph
        DirectedCycle dc = new DirectedCycle(graph);
//        System.out.println(dc.hasCycle());
//        for (int i: dc.cycle()) System.out.println(i);
        if (dc.hasCycle()) throw new java.lang.IllegalArgumentException();

//        boolean rooted = false;
//        for (int ver = 0; ver < graph.V(); ver++) {
//            if (graph.indegree(ver) == 0) {
//                rooted = true;
//                break;
//            }
//        }
//        if (!rooted) throw new java.lang.IllegalArgumentException();

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounSynsetsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new java.lang.IllegalArgumentException();
        return nounSynsetsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    // minimum length of any ancestral path between any synset v of A and any synset w of B
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new java.lang.IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();

        // retrieve id list associated with nounA, nounB
        ArrayList<Integer> idListA = nounSynsetsMap.get(nounA);
        ArrayList<Integer> idListB = nounSynsetsMap.get(nounB);

        boolean flag = false;

        if (idListA != currVIter) {
            bfsVIter = new BreadthFirstDirectedPaths(graph, idListA);
            currVIter = idListA;
            flag = true;
        }
        if (idListB != currWIter) {
            bfsWIter = new BreadthFirstDirectedPaths(graph, idListB);
            currWIter = idListB;
            flag = true;
        }

        // find all vertices that v can reach and associated distance
        if (flag) {
            int minDist = Integer.MAX_VALUE;
            int lowestAncestor = -1;
            for (int ver = 0; ver < graph.V(); ver++) {
                if (bfsVIter.hasPathTo(ver) && bfsWIter.hasPathTo(ver)) {
                    int dist = bfsVIter.distTo(ver) + bfsWIter.distTo(ver);
                    if (dist < minDist) {
                        minDist = dist;
                        lowestAncestor = ver;
                    }
                }
            }

            minAncestorIter = lowestAncestor;
            if (lowestAncestor == -1) minLengthIter = -1;
            else minLengthIter = minDist;
        }

        return minLengthIter;

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new java.lang.IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();

        // retrieve id list associated with nounA, nounB
        ArrayList<Integer> idListA = nounSynsetsMap.get(nounA);
        ArrayList<Integer> idListB = nounSynsetsMap.get(nounB);

        boolean flag = false;

        if (idListA != currVIter) {
            bfsVIter = new BreadthFirstDirectedPaths(graph, idListA);
            currVIter = idListA;
            flag = true;
        }
        if (idListB != currWIter) {
            bfsWIter = new BreadthFirstDirectedPaths(graph, idListB);
            currWIter = idListB;
            flag = true;
        }

        // find all vertices that v can reach and associated distance
        if (flag) {
            int minDist = Integer.MAX_VALUE;
            int lowestAncestor = -1;
            for (int ver = 0; ver < graph.V(); ver++) {
                if (bfsVIter.hasPathTo(ver) && bfsWIter.hasPathTo(ver)) {
                    int dist = bfsVIter.distTo(ver) + bfsWIter.distTo(ver);
                    if (dist < minDist) {
                        minDist = dist;
                        lowestAncestor = ver;
                    }
                }
            }

            minAncestorIter = lowestAncestor;
            if (lowestAncestor == -1) minLengthIter = -1;
            else minLengthIter = minDist;
        }

        return nounSynsetsIdMap.get(minAncestorIter);

    }

    // do unit testing of this class
    public static void main(String[] args) {
//        WordNet wn = new WordNet("wordnet/synsets15.txt", "wordnet/hypernyms15Tree.txt");
//        StdOut.println(wn.distance("b", "f"));
//        StdOut.println(wn.sap("b", "f"));
    }
}
