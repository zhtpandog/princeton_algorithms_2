import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class Outcast {

    private final WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new java.lang.IllegalArgumentException();
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) throw new java.lang.IllegalArgumentException();

        int[] distSumArr = new int[nouns.length];

        int numWords = nouns.length;
        for (int i = 0; i < numWords; i++) {
            for (int j = i + 1; j < numWords; j++) {
                int distance = wn.distance(nouns[i], nouns[j]);
                distSumArr[i] += distance;
                distSumArr[j] += distance;
            }
        }

        int maxDist = 0;
        String result = null;
        for (int i = 0; i < nouns.length; i++) {
            if (distSumArr[i] > maxDist) {
                maxDist = distSumArr[i];
                result = nouns[i];
            }
        }
        return result;
    }


    public static void main(String[] args) {

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }


        /*
        // for test
        WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String[] source = {"wordnet/outcast5.txt", "wordnet/outcast8.txt", "wordnet/outcast11.txt"};
        for (int t = 0; t < source.length; t++) {
            In in = new In(source[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(source[t] + ": " + outcast.outcast(nouns));
        }
        */

    }
}