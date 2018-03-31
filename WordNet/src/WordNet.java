import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // Build the HashMap for synsets lookup. Key(String) is word, value(ArrayList<Integer>) is wordId in synsets
        int numRecords = 0;
        In nounSynsetsIn = new In(synsets);
        HashMap<String, ArrayList<Integer>> nounSynsetsMap = new HashMap<>();
        while (nounSynsetsIn.hasNextLine()) {
            numRecords++;
            String[] synsetRecord = nounSynsetsIn.readLine().split(",");
            int wordId = Integer.parseInt(synsetRecord[0]);
            for (String word: synsetRecord[1].split(" ")) {
                if (nounSynsetsMap.containsKey(word)) {
                    nounSynsetsMap.get(word).add(wordId);
                } else {
                    nounSynsetsMap.put(word, new ArrayList<>(Collections.singletonList(wordId)));
                }
            }
        }

//        StdOut.println(numRecords);

        // build the adjacency list
        int numVertices;
        int numEdges = 0;
        HashMap<Integer, int[]> graphAdjList = new HashMap<>();
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

//        StdOut.println(numVertices);
//        StdOut.println(numEdges);
//
//        int cnt = 0;
//        for(HashMap.Entry<Integer, int[]> entry: graphAdjList.entrySet()) {
//            System.out.println(entry.getKey() + " : "  + Arrays.toString(entry.getValue()));
//            cnt++;
//            if (cnt > 10) {
//                break;
//            }
//        }

    }

//    // returns all WordNet nouns
//    public Iterable<String> nouns() {
//
//    }
//
//    // is the word a WordNet noun?
//    public boolean isNoun(String word) {
//
//    }
//
//    // distance between nounA and nounB (defined below)
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
