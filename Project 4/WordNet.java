import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Ben Loisch on 4/7/2017.
 */
public final class WordNet {
    public SAP sap;
    public ArrayList<String> strings;
    public Map<String, ArrayList<Integer>> map;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inputSynsets = new In(synsets);
        In inputHypernyms = new In(hypernyms);

        if (!inputSynsets.exists()) {
            //System.out.println(synsets + " file not found!");
            return;
        }

        if (!inputHypernyms.exists()) {
            //System.out.println(hypernyms + " file not found!!");
            return;
        }

        //get amount of verticies
        int verticies = 0;
        while (inputHypernyms.hasNextLine()) {
            inputHypernyms.readLine();
            verticies++;
        }

        Digraph digraph = new Digraph(verticies);
        inputHypernyms.close();
        inputHypernyms = new In(hypernyms);

        //create edges between verticies on each line
        for (int i = 1; i < verticies; i++) {
            try {
                String line = inputHypernyms.readLine();
                List<String> verticiesList = Arrays.asList(line.split(","));
                for (int n = 1; n < verticiesList.size(); n++) {
                    digraph.addEdge(Integer.parseInt(verticiesList.get(0)), Integer.parseInt(verticiesList.get(n)));
                }

            } catch (Exception e) {
                //System.out.println("exception in loading " + hypernyms);
                inputSynsets.close();
                inputHypernyms.close();
                return;
            }
        }

        //load synsets
        this.strings = new ArrayList<String>();
        this.map = new HashMap<String, ArrayList<Integer>>();
        for (int i = 1; i < verticies; i++) {
            try {
                String line = inputSynsets.readLine();
                List<String> wordPair = Arrays.asList(line.split(","));
                int w = Integer.parseInt(wordPair.get(0));
                String s = wordPair.get(1);
                List<String> s_list = Arrays.asList(s.split(" "));
                strings.add(s_list.get(0));
                for (int m = 0; m < s_list.size(); m++) {
                    //insert pair(w,s) into map
                    //check if it is already there
                    if (map.containsKey(s_list.get(m))) {
                        map.get(s_list.get(m)).add(w);
                    }
                    else {
                        ArrayList<Integer> a = new ArrayList<Integer>();
                        a.add(w);
                        map.put(s_list.get(m), a);
                    }
                }

            } catch (Exception e) {
                //System.out.println("exception in loading " + synsets);
                inputSynsets.close();
                inputHypernyms.close();
                return;
            }
        }

        this.sap = new SAP(digraph);

        inputSynsets.close();
        inputHypernyms.close();
    }

    // is the word a WordNet noun? This can be used to search for existing
    // nouns at the beginning of the printSap method
    public boolean isNoun(String word) {
        return this.map.containsKey((word));
    }

    // print the synset (second field of synsets.txt) that is the common ancestor
    // of nounA and nounB in a shortest ancestral path as well as the length of the path,
    // following this format: (see handout)
    // If no such path exists the sap should contain -1 and ancestor should say "null"
    // This method should use th epreviously defined SAP datatype
    public void printSap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            System.out.println("sap = -1, ancestor = null");
            return;
        }

        ArrayList<Integer> nounA_numbers = map.get(nounA);
        ArrayList<Integer> nounB_numbers = map.get(nounB);

        int ancestor = -1;
        int length = Integer.MAX_VALUE;
        for (int a = 0; a < nounA_numbers.size(); a++) {
            for (int b = 0; b < nounB_numbers.size(); b++) {
                int retAncestor = this.sap.ancestor(nounA_numbers.get(a), nounB_numbers.get(b));
                int retLength = this.sap.tempLength;

                if (retLength == -1) {
                    //do nothing
                }
                else if (retLength < length) {
                    ancestor = retAncestor;
                    length = retLength;
                }
            }
        }

        System.out.println("sap = " + length + ", ancestor = " + strings.get(ancestor-1));
    }

    public static void main(String args[]) {
        In inputTest = new In(args[2]);
        WordNet wordnet = new WordNet(args[0], args[1]);

        if (!inputTest.exists()) {
            //System.out.println("test input file not found!");
            return;
        }

        int inputTestLines = 0;
        while (inputTest.hasNextLine()) {
            inputTest.readLine();
            inputTestLines++;
        }
        inputTest.close();

        inputTest = new In(args[2]);
        for (int i = 0; i < inputTestLines; i++) {
            try {
                String v = inputTest.readString();
                String w = inputTest.readString();
                wordnet.printSap(v, w);
            } catch (Exception e) {
                //System.out.println("Exception occured when trying to readString() for " + args[0]);
                inputTest.close();
                return;
            }
        }

        inputTest.close();
    }
}
