import java.util.ArrayList;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        //BWT bwt = new BWT("abra.txt");
        //bwt.transform();
        /*
        ArrayList<Character> listy = new ArrayList<Character>();
        while (!BinaryStdIn.isEmpty()) {
            listy.add((char)BinaryStdIn.readByte());
        }
        Object[] arr = listy.toArray();
        char[] chars = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            chars[i] = (char)arr[i];
        }

        String original = new String(chars);
        */
        String original = BinaryStdIn.readString();

        Wrapper[] wArray = new Wrapper[original.length()];

        for (int i = 0; i < original.length(); i++) {
            wArray[i] = new Wrapper(original, i, original.length());
        }

        Quick3string.sort(wArray);

        Wrapper.findZeroIndex(wArray);
        Wrapper.getLastChars(wArray, original.toCharArray());

    }
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        int i = BinaryStdIn.readInt();
        String input = BinaryStdIn.readString();
        char[] cInput = input.toCharArray();

        char[] cInputSorted = cInput.clone();
        Arrays.sort(cInputSorted);

        int[] next = new int[cInput.length];

        boolean[] bInput = new boolean[cInput.length];
        Arrays.fill(bInput, true);

        for (int sorted = 0; sorted < cInput.length; sorted++) {
            char find = cInputSorted[sorted];
            for (int encoded = 0; encoded < cInput.length; encoded++) {
                if (cInput[encoded] == find && bInput[encoded] == true) {
                    next[sorted] = encoded;
                    bInput[encoded] = false;
                    break;
                }
            }
        }

        for (int count = 0; count < cInput.length; count++) {
            i = next[i];
            BinaryStdOut.write(input.charAt(i));
        }

        BinaryStdOut.flush();
    }
    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args)
    {
        if (args[0].equals("-"))
            encode();

        if (args[0].equals("+"))
            decode();
    }
}