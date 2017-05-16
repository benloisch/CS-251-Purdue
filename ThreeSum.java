/**
 * Created by bloisch on 1/28/2017.
 */

//tilde approximation of if statment: (t1/6) * N^3
//order of growth: N^3

//commented out code makes this method faster
//order of growth: N^2 * log(N)

public class ThreeSum {
    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) { //N
            for (int j = i + 1; j < N; j++) { //N^2/2 - N/2
                //for (int k = j + 1; k < N; k++) {//N^3/6 - N^2/2 + N/3
                //    if (a[i] + a[j] + a[k] == 0)
                //        cnt++;
                //}

                if (BinarySearch.rank(-a[i] - a[j], a) > j)
                    cnt++;
            }
        }

        return cnt;
    }
}
