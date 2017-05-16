
/**
 * Created by bloisch on 2/5/2017.
 */
import java.util.ArrayList;
import java.util.List;

public class PercolationStats {

    public int N;
    public double T;
    public String type;
    public double threshold;

    public double runQUUF() {
        long start = System.currentTimeMillis();

        Percolation p = new Percolation(N);
        int rowSize = p.getRowSize();


        threshold = 0;
        while (!p.percolates()) {
            p.open((int)StdRandom.uniform(0, rowSize), (int)StdRandom.uniform(0, rowSize));
        }

        for (int x = 0; x < rowSize; x++)
            for (int y = 0; y < rowSize; y++)
                if (p.isOpen(x, y) || p.isFilled(x, y))
                    threshold++;

        threshold /= (double)rowSize * (double)rowSize;

        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public double runWQUUF() {
        long start = System.currentTimeMillis();

        PercolationQuick p = new PercolationQuick(N);
        int rowSize = p.getRowSize();

        threshold = 0;
        while (!p.percolates()) {
            p.open((int)StdRandom.uniform(0, rowSize), (int)StdRandom.uniform(0, rowSize));
            // p.open(0 + (int)(Math.random() * ((rowSize - 1) + 1)), 0 + (int)(Math.random() * ((rowSize - 1) + 1)));
        }

        for (int x = 0; x < rowSize; x++)
            for (int y = 0; y < rowSize; y++)
                if (p.isOpen(x, y) || p.isFilled(x, y))
                    threshold++;

        threshold /= (double)rowSize * (double)rowSize;

        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public void gatherStats() {
        if (type.equals("slow")) {

            List<Double> times = new ArrayList<Double>();
            List<Double> thresholds = new ArrayList<Double>();
            for (int i = 0; i < T; i++) {
                times.add(runWQUUF());
                thresholds.add(threshold);
            }

            double meanTime = 0;
            double totalTime = 0;
            double meanThreshold = 0;
            for (int i = 0; i < T; i++) {
                totalTime += times.get(i);
                meanThreshold += thresholds.get(i);
            }
            meanTime = totalTime / T;
            meanThreshold = meanThreshold / T;

            double stdDevTime = 0;
            for (int i = 0; i < T; i++) {
                stdDevTime += (times.get(i) - meanTime) * (times.get(i) - meanTime);
            }
            stdDevTime /= T;
            stdDevTime = Math.sqrt(stdDevTime);

            double stdDevThreshold = 0;
            for (int i = 0; i < T; i++) {
                stdDevThreshold += (thresholds.get(i) - meanThreshold) * (thresholds.get(i) - meanThreshold);
            }
            stdDevThreshold /= T;
            stdDevThreshold = Math.sqrt(stdDevThreshold);

            System.out.println("mean threshold=" + meanThreshold);
            System.out.println("std dev=" + stdDevThreshold);
            System.out.println("time=" + totalTime);
            System.out.println("mean time=" + meanTime);
            System.out.println("stddev time=" + stdDevTime);

        } else if (type.equals("fast")) {

            List<Double> times = new ArrayList<Double>();
            List<Double> thresholds = new ArrayList<Double>();
            for (int i = 0; i < T; i++) {
                times.add(runQUUF());
                thresholds.add(threshold);
            }

            double meanTime = 0;
            double totalTime = 0;
            double meanThreshold = 0;
            for (int i = 0; i < T; i++) {
                totalTime += times.get(i);
                meanThreshold += thresholds.get(i);
            }
            meanTime = totalTime / T;
            meanThreshold = meanThreshold / T;

            double stdDevTime = 0;
            for (int i = 0; i < T; i++) {
                stdDevTime += (times.get(i) - meanTime) * (times.get(i) - meanTime);
            }
            stdDevTime /= T;
            stdDevTime = Math.sqrt(stdDevTime);

            double stdDevThreshold = 0;
            for (int i = 0; i < T; i++) {
                stdDevThreshold += (thresholds.get(i) - meanThreshold) * (thresholds.get(i) - meanThreshold);
            }
            stdDevThreshold /= T;
            stdDevThreshold = Math.sqrt(stdDevThreshold);

            System.out.println("mean threshold=" + meanThreshold);
            System.out.println("std dev=" + stdDevThreshold);
            System.out.println("time=" + totalTime);
            System.out.println("mean time=" + meanTime);
            System.out.println("stddev time=" + stdDevTime);

        } else {
            System.out.println("mean threshold=0\nstd dev=0\ntime=0\nmean time=0\nstddev time=0\n");
        }
    }

    public static void main(String[] args) {

        PercolationStats ps = new PercolationStats();

        ps.N = Integer.parseInt(args[0]);
        ps.T = Integer.parseInt(args[1]);
        ps.type = args[2];

        //ps.N = 100;
        //ps.T = 30;
        //ps.type = "fast";

        ps.gatherStats();

    }
}
