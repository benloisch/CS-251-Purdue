/**
 * Created by Benjamin Loisch on 1/27/17.
 * CS 250 Purdue Spring 2017
 */


import java.util.*;
import java.awt.*;
public class Source {

    public static void main (String[] args) {
        for (int N = 250; true; N += N) {
            double time = DoublingTest.timeTrial(N);
            StdOut.printf("%7d %5.1f\n", N, time);
        }
    }

}