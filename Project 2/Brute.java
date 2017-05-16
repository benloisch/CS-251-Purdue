import java.io.*;
import java.util.*;

/**
 * Created by bloisch on 2/18/2017.
 */
public class Brute {

    public Point [] arrP;
    BufferedWriter bw;

    public void loadData() {
        /*
        Scanner s;
        try {
            s = new Scanner(new File("input6.txt"));
        } catch (FileNotFoundException e) {
            return;
        }

        if(s.hasNext()) {
            int n = s.nextInt();

            arrP = new Point[n];

            for (int i = 0; i < n; i++)
                this.arrP[i] = new Point(s.nextInt(), s.nextInt());

        }
        */

        if(!StdIn.isEmpty()) {
            int n = StdIn.readInt();
            arrP = new Point[n];
            int c = 0;
            while (c < n) {
                this.arrP[c] = new Point(StdIn.readInt(), StdIn.readInt());
                c ++;
            }
        }

    }

    public void findColinearPoints () {
        Arrays.sort(arrP);
        //Arrays.sort(arrP, arrP[0].BY_SLOPE_ORDER);

        for (int i = 0; i < arrP.length - 3; i++)
            for (int j = i + 1; j < arrP.length - 2; j++)
                for (int k = j + 1; k < arrP.length - 1; k++) {
                    for (int m = k + 1; m < arrP.length; m++) {
                        if (Point.areCollinear(arrP[i], arrP[j], arrP[k], arrP[m])) {
                            //System.out.println(arrP[i].getX() + "," + arrP[i].getY() + " " + arrP[j].getX() + "," + arrP[j].getY() + " " + arrP[k].getX() + "," + arrP[k].getY()+" "+arrP[m].getX() + "," + arrP[m].getY())
                            String line = new String();
                            try {

                                line += "4:("+arrP[i].getX()+", "+arrP[i].getY() + ") -> ";
                                line += "("+arrP[j].getX()+", "+arrP[j].getY() + ") -> ";
                                line += "("+arrP[k].getX()+", "+arrP[k].getY() + ") -> ";
                                line += "("+arrP[m].getX()+", "+arrP[m].getY() + ")\n";
                                StdOut.print(line);
                                this.bw.write("4:("+arrP[i].getX()+", "+arrP[i].getY() + ") -> ");
                                this.bw.write("("+arrP[j].getX()+", "+arrP[j].getY() + ") -> ");
                                this.bw.write("("+arrP[k].getX()+", "+arrP[k].getY() + ") -> ");
                                this.bw.write("("+arrP[m].getX()+", "+arrP[m].getY() + ")");
                                this.bw.newLine();
                            } catch (IOException e) {}
                        }
                    }
                }
    }

    public void printData() {

    }

    public static void main(String[] args) {
        Brute b = new Brute();

        b.loadData();

        b.bw = null;
        try {
            b.bw = new BufferedWriter(new FileWriter("visualPoints.txt"));
        } catch (IOException e) {}

        b.findColinearPoints();

        try {
            b.bw.close();
        } catch (IOException e) {}

        StdOut.print("\n");
    }
}
