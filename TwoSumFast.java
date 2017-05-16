/**
 * Created by bloisch on 1/31/17.
 */

import java.util.Arrays;

public class TwoSumFast {
    public static int count(int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            //for (int j = i + 1; j < N; j++) {
            //    if (a[i] + a[j] == 0)
            //        cnt++; }

            if (BinarySearch.rank(-a[i], a) > i)
                cnt++;
        }

        return cnt;
    }
}
