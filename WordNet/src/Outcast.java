import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class Outcast {

    WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        HashMap<String, Integer> distSum = new HashMap<>();
        int numWords = nouns.length;
        for (int i = 0; i < numWords; i++) {
            for (int j = i + 1; j < numWords; j++) {
//                StdOut.println(nouns[i]);
//                StdOut.println(nouns[j]);
//                StdOut.println("-----");
                int distance = wn.distance(nouns[i], nouns[j]);

                if (!distSum.containsKey(nouns[i])) distSum.put(nouns[i], distance);
                else distSum.replace(nouns[i], distSum.get(nouns[i]) + distance);

                if (!distSum.containsKey(nouns[j])) distSum.put(nouns[j], distance);
                else distSum.replace(nouns[j], distSum.get(nouns[j]) + distance);
            }
        }

        int maxDist = 0;
        String result = null;
        for (HashMap.Entry<String, Integer> entry: distSum.entrySet()) {
            if (entry.getValue() > maxDist) {
                maxDist = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }


    public static void main(String[] args) {
//        WordNet wordnet = new WordNet(args[0], args[1]);
        WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String[] source = {"wordnet/outcast5.txt", "wordnet/outcast8.txt", "wordnet/outcast11.txt"};
        for (int t = 0; t < source.length; t++) {
            In in = new In(source[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(source[t] + ": " + outcast.outcast(nouns));
        }
    }
}