
import java.io.*;

/**
 * Created by bloisch on 2/5/2017.
 */
public class PercolationVisualizer {
    public static void main(String[] args) {


        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("visualMatrix.txt"));
        } catch (IOException e) {

        }

        Percolation p = new Percolation(StdIn.readInt());

        try {
            bw.write(p.getRowSize() + "");
            bw.newLine();
            bw.newLine();
        } catch (IOException e) {}

        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            p.open(y, x);

            for (int yGrid = p.getRowSize()-1; yGrid >= 0; yGrid--) {
                for (int xGrid = 0; xGrid < p.getRowSize(); xGrid++) {
                    try {
                        if (p.isFull(xGrid, yGrid) && p.getElement(xGrid, yGrid) == 1)
                            bw.write("2 ");
                        else
                            bw.write(p.getElement(xGrid, yGrid) + " ");
                    } catch (IOException e) {}
                }
                try {
                    bw.newLine();
                } catch (IOException e) {}
            }

            try {
                bw.newLine();
            } catch (IOException e) {}
        }

        try {
            bw.close();
        } catch (IOException e) {}
    }
}
