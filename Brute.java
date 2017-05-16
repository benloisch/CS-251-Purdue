import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by bloisch on 2/18/2017.
 */
public class Brute {

    public ArrayList<Point> arrP = new ArrayList<Point>();

    public void loadData() {
        Scanner s;
        try {
            s = new Scanner(new File("input6.txt"));
        } catch (FileNotFoundException e) {
            return;
        }

        if(s.hasNext()) {
            int n = s.nextInt();

            for (int i = 0; i < n; i++)
                this.arrP.add(new Point(s.nextInt(), s.nextInt()));

        }

        /*
        if(!StdIn.isEmpty()) {
            int n = StdIn.readInt();

            for (int i = 0; i < n; i++)
                arrP.add(new Point(StdIn.readInt(), StdIn.readInt()));

        }
        */
    }

    public void printData() {

    }

    public static void main(String[] args) {
        Brute b = new Brute();

        b.loadData();



    }
}
