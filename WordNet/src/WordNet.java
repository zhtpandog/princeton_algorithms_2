import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class WordNet {

    private HashMap<String, ArrayList<Integer>> nounSynsetsMap;
    private HashMap<Integer, int[]> graphAdjList;
    private int numVertices;
    private int numEdges;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // Build the nounSynsetsMap(HashMap) for synsets lookup. Key(String) is word, value(ArrayList<Integer>) is wordId in synsets
        In nounSynsetsIn = new In(synsets);
        nounSynsetsMap = new HashMap<>();
        while (nounSynsetsIn.hasNextLine()) {
            String[] synsetRecord = nounSynsetsIn.readLine().split(",");
            int wordId = Integer.parseInt(synsetRecord[0]);
            for (String word : synsetRecord[1].split(" ")) {
                if (nounSynsetsMap.containsKey(word)) {
                    nounSynsetsMap.get(word).add(wordId);
                } else {
                    nounSynsetsMap.put(word, new ArrayList<>(Collections.singletonList(wordId)));
                }
            }
        }

        // build the adjacency list graphAdjList
        numEdges = 0;
        graphAdjList = new HashMap<>();
        In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine()) {
            String[] hypernymRelation = hypernymsIn.readLine().split(",");
            int node = Integer.parseInt(hypernymRelation[0]);
            int[] neighbors = new int[hypernymRelation.length - 1];
            for (int i = 0; i < neighbors.length; i++) {
                neighbors[i] = Integer.parseInt(hypernymRelation[i + 1]);
                numEdges++;
            }
            graphAdjList.put(node, neighbors);
        }

        numVertices = graphAdjList.size();

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounSynsetsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounSynsetsMap.containsKey(word);
    }

//    // distance between nounA and nounB (defined below)
//    //  minimum length of any ancestral path between any synset v of A and any synset w of B
//    public int distance(String nounA, String nounB) {
//
//    }
//
//    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
//    // in a shortest ancestral path (defined below)
//    public String sap(String nounA, String nounB) {
//
//    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
    }
}
