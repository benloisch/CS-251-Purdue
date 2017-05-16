import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bloisch on 2/18/17.
 */
public class Fast {
    public Point [] arrP;
    BufferedWriter bw;
    ArrayList<String> entries;

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

        /*
     	Random rand = new Random();
        arrP = new Point[10000];
        for (int i = 0; i < 10000; i++) {
            this.arrP[i] = new Point(rand.nextInt(32768), rand.nextInt(32768));
        }
        */
    }

    public int amountOfSameSlopes(int start, Point ref, Point [] arr) {
        int amount = 1;

        double refSlope = 0.0;
        if (ref.getX() != arr[start].getX() && ref.getY() != arr[start].getY())
            refSlope = ref.slope(arr[start]);
        else if (start < arr.length - 1)
            refSlope = ref.slope(arr[start + 1]);
        for (int i = start; i < arr.length-1; i++) {
            double compareSlope = ref.slope(arr[i]);
            if (compareSlope != refSlope)
                break;
            else
                amount++;
        }
        return amount;
    }

    public Point[] arrayCopy(Point[] a) {
        Point[] b = new Point[a.length];
        for (int c = 0; c < a.length; c++) {
            b[c] = a[c];
        }
        return b;
    }

    public Point[] arrayCopyArrRemOneElement(Point[] a, Point rem) {
        Point[] b = new Point[a.length-1];
        boolean skipped = false;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i].getX() == rem.getX() && a[i].getY() == rem.getY())
                skipped = true;

            if (skipped) {
                b[i] = a[i+1];
            } else {
                b[i] = a[i];
            }
        }

        return b;
    }

    public void writeArr(Point[] arr) {
        Arrays.sort(arr);
        int n = 0;
        String line = new String("");
        line += (arr.length + ":(" + arr[n].getX() + ", " + arr[n].getY() + ") -> ");
        n++;

        for (; n < arr.length-1; n++) {
            line += ("(" + arr[n].getX() + ", " + arr[n].getY() + ") -> ");
        }

        line += "(" + arr[n].getX() + ", " + arr[n].getY() + ")\n";

        for (int p = 0; p < this.entries.size(); p++) {
            if (entries.get(p).contains(line.substring(2, line.length())) && line.length() < entries.get(p).length())
                return;
        }

        try {
            this.bw.write(line);
        } catch (IOException e) {
        }

        entries.add(line);
        StdOut.print(line);
    }

    public void findColinearPoints () {

        Arrays.sort(arrP);

        Point [] arrPc = arrayCopy(arrP);

        for (int a = 0; a < arrP.length; a++) { //find groups

            if (arrPc.length <= 3)
                break;

            Point referencePoint = arrP[a];

            //find index of referencePoint in arrPc
            int index = 0;
            int x = arrP[a].getX();
            int y = arrP[a].getY();
            for (int q = 0; q < arrP.length; q++) {
                if (x == arrPc[q].getX() && y == arrPc[q].getY()) {
                    index = q;
                    break;
                }
            }

            Arrays.sort(arrPc, arrPc[index].BY_SLOPE_ORDER);

            //traverse sorted array and count same-slope elements
            for (int i = 0; i < arrPc.length - 3; i++) {
                int sameSlopes = amountOfSameSlopes(i, referencePoint, arrPc);

                if (sameSlopes >= 4) {

                    Point[] outArr = new Point[sameSlopes];

                    int counter = 0;
                    int n = i;

                    outArr[0] = new Point(arrPc[n].getX(), arrPc[n].getY());
                    n++;
                    counter++;

                    for (; n < i + sameSlopes - 1; n++) {
                        outArr[counter] = new Point(arrPc[n].getX(), arrPc[n].getY());
                        counter++;
                    }

                    boolean foundRefInSlopes = false;
                    for (int t = i; t < i + sameSlopes - 1; t++) {
                        if (referencePoint.getX() == arrPc[t].getX() && referencePoint.getY() == arrPc[t].getY()) {
                            foundRefInSlopes = true;
                            break;
                        }
                    }

                    if (!foundRefInSlopes) {
                        outArr[counter] = new Point(referencePoint.getX(), referencePoint.getY());
                        //outArr[counter] = new Point(arrPc[n].getX(), arrPc[n].getY());
                    } else {
                        outArr[counter] = new Point(arrPc[n].getX(), arrPc[n].getY());
                    }

                    i += sameSlopes;
                    Arrays.sort(outArr);
                    writeArr(outArr);

                }
            }

            arrPc = arrayCopyArrRemOneElement(arrPc, referencePoint);

        }
    }

    public static void main(String[] args) {
        Fast b = new Fast();
        b.entries = new ArrayList<String>();

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
