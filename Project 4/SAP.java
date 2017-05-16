import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ben Loisch on 4/7/2017.
 */
public class SAP {
    private Digraph G;
    public int tempLength;

    public SAP(Digraph G) {
        this.G = G;
        tempLength = -1;
    }

    // return length of shortest ancestral path of v and w; -1 if no such path
    public int length(int v, int w) {
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < this.G.V(); i++) {
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
            if (bfsV.hasPathTo(i)) {
                BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
                if (bfsW.hasPathTo(i))
                    if (bfsV.distTo(i) + bfsW.distTo(i) < minLength)
                        minLength = bfsV.distTo(i) + bfsW.distTo(i);
            }
        }

        if (minLength == Integer.MAX_VALUE)
            return -1;
        return minLength;
    }

    // return a common ancestor of v and w that participates in a shortest
    // ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int commonVertex = -1;
        int minLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
        for (int i = 0; i < this.G.V(); i++) {
            if (bfsV.hasPathTo(i)) {
                if (bfsW.hasPathTo(i))
                    if (bfsV.distTo(i) + bfsW.distTo(i) < minLength) {
                        minLength = bfsV.distTo(i) + bfsW.distTo(i);
                        commonVertex = i;
                    }
            }
        }
        this.tempLength = minLength;
        return commonVertex;
    }

    static public void main(String args[]) {
        //get input
        //args[0] is name of digraph input file
        //args[1] is name of digraph test input file
        In inputFile = new In(args[0]);
        In inputTest = new In(args[1]);

        if (!inputFile.exists()) {
            //System.out.println("arg[0] doesn't exist!");
            return;
        }
        if (!inputTest.exists()) {
            //System.out.println("arg[1] doesn't exist!");
            return;
        }
        if (inputFile.isEmpty()) {
            //System.out.println("arg[0] is an empty file!");
            return;
        }
        if (inputTest.isEmpty()) {
            //System.out.println("arg[0] is an empty file!");
            return;
        }

        SAP sap = new SAP(new Digraph(inputFile));

        while (inputTest.hasNextLine()) {
            try {
                int v = inputTest.readInt();
                int w = inputTest.readInt();
                System.out.println("sap = " + Integer.toString(sap.length(v,w)) + ", ancestor = " + Integer.toString(sap.ancestor(v,w)));
            } catch (Exception e) {
                //System.out.println("Exception occured when trying to readInt()");
                inputFile.close();
                inputTest.close();
                return;
            }
        }

        inputFile.close();
        inputTest.close();
    }
}
