import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Ben Loisch on 2/6/2017.
 */
public class PercolationQuick {
    private int[] grid;
    private int rowSize;
    QuickUnionUF wquuf;

    public PercolationQuick(int n) {
        rowSize = n;
        grid = new int[n * n];
        for (int i = 0; i < n * n; i++)
            grid[i] = 0;

        wquuf = new QuickUnionUF((n * n) + 2);
        for (int i = 1; i < rowSize; i++)
            wquuf.union(0, i);
        for (int i = rowSize * (rowSize - 1); i < rowSize * rowSize; i++)
            wquuf.union(rowSize * rowSize, i);
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getElement(int x, int y) {
        if (x < 0 || x >= rowSize && y < 0 || y >= rowSize)
            return 0;

        return grid[x + (rowSize * y)];
    }

    public boolean nextTo(int x1, int y1, int x2, int y2) {
        if (x1 >= 0 && x1 < rowSize && y1 >= 0 && y1 < rowSize) //validate in range
            if (x2 >= 0 && x2 < rowSize && y2 >= 0 && y2 < rowSize) { //validate in range
                if ((grid[x1+(rowSize*y1)] == 1 || grid[x1+(rowSize*y1)] == 2) && (grid[x2+(rowSize*y2)] == 1 || grid[x2+(rowSize*y2)] == 2)) //if open next to open
                    return true;
            }
        return false;
    }

    public void open(int x, int y) {
        if (x < 0 || x >= rowSize && y < 0 || y >= rowSize)
            return;

        if (grid[x + (rowSize * y)] == 0)
            grid[x + (rowSize * y)] = 1;

        //check if where we opened is next to another open spot and connect the two
        if (nextTo(x, y, x-1, y))
            wquuf.union(x + (rowSize * y), (x-1) + (rowSize * y));
        if (nextTo(x, y, x+1, y))
            wquuf.union(x + (rowSize * y), (x+1) + (rowSize * y));
        if (nextTo(x, y, x, y-1))
            wquuf.union(x + (rowSize * y), (x) + (rowSize * (y-1)));
        if (nextTo(x, y, x, y+1))
            wquuf.union(x + (rowSize * y), (x) + (rowSize * (y+1)));

        //if this node is connected to the top, make sure all nodes are full
        /*
        if (isFull(x, y)) {
            grid[x+(rowSize*y)] = 2;
            for (int xGrid = 0; xGrid < rowSize; xGrid++)
                for (int yGrid = 0; yGrid < rowSize; yGrid++) {
                    if (wquuf.connected(x + (rowSize * y), xGrid + (rowSize * yGrid))) {
                        grid[xGrid + (rowSize * yGrid)] = 2;
                    }
                }
        }
        */
    }

    public boolean isOpen(int x, int y) {
        return grid[x + (rowSize * y)] == 1;
    }

    public boolean isFilled(int x, int y) {
        return grid[x + (rowSize * y)] == 2;
    }

    public boolean isFull(int x, int y) {
        //check if node is connected to any node on the top layer
        //for (int xGrid = 0; xGrid < rowSize; xGrid++)
        //    if (wquuf.connected(x + (rowSize * y), xGrid + (rowSize * (rowSize-1))))
        //        return true;

        if (wquuf.connected(x + (rowSize * y), rowSize * rowSize))
            return true;

        return false;
    }

    public boolean percolates() {
        //check if any node on bottom is connected to any node on the top layer
        //for (int x = 0; x < rowSize; x++)
        //    for (int xGrid = 0; xGrid < rowSize; xGrid++)
        //        if (wquuf.connected(x, xGrid + (rowSize * (rowSize-1))))
        //            return true;

        if (wquuf.connected(0, rowSize * rowSize))
            return true;

        return false;
    }

    public static void main(String[] args) {

        //Scanner s;
        //try {
        //    s = new Scanner(new File(args[0]));
        //} catch (FileNotFoundException e) {
        //    return;
        //}

        Percolation p = new Percolation(StdIn.readInt());
        //System.out.println(StdIn.readInt());
        /*
        long start = System.currentTimeMillis();

        Percolation p = new Percolation(100);
        int rowSize = p.getRowSize();


        double threshold = 0;
        while (!p.percolates()) {
            p.open(StdRandom.uniform(0, rowSize), StdRandom.uniform(0, rowSize));
            //p.open(0 + (int)(Math.random() * ((rowSize - 1) + 1)), 0 + (int)(Math.random() * ((rowSize - 1) + 1)));
        }

        for (int x = 0; x < rowSize; x++)
            for (int y = 0; y < rowSize; y++)
                if (p.isOpen(x, y) || p.isFilled(x, y))
                    threshold++;

        threshold /= (double)rowSize * (double)rowSize;

        double now = System.currentTimeMillis();
        System.out.println((now - start) / 1000.0);
        */

        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            p.open(y, x);
        }

        if (p.percolates()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}

