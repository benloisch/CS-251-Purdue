import java.util.Arrays;

public class MoveToFront {
	// apply move-to-front encoding, reading from standard input and writing to standard output
	public static void encode()
	{
		String bwt = BinaryStdIn.readString();
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++)
            ascii[i] = (char)i;

        for (int mtf = 0; mtf < bwt.length(); mtf++) {
            char c = bwt.charAt(mtf);

            char outIndex = 0;
            for (int findIndex = 0; findIndex < 256; findIndex++) {
                if (c == ascii[findIndex]) {
                    outIndex = (char)findIndex;
                    break; }}
            BinaryStdOut.write(outIndex);

            //if letter isn't already at beginning,
            //then put it at beginning and shift letters to right
            if (outIndex != 0) {
                for (int shift = outIndex; shift > 0; shift--) {
                    ascii[shift] = ascii[shift - 1];
                }
                ascii[0] = c;
            }

        }

        BinaryStdOut.flush();
	}
	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode()
	{
        String input = BinaryStdIn.readString();

        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++)
            ascii[i] = (char)i;

        for (int init = 0; init < input.length(); init++) {
            int index = input.charAt(init);

            char temp = ascii[index];
            BinaryStdOut.write(ascii[index]);

            //if letter isn't already at beginning,
            //then put it at beginning and shift letters to right
            if (index != 0) {
                for (int shift = index; shift > 0; shift--) {
                    ascii[shift] = ascii[shift - 1];
                }
                ascii[0] = temp;
            }
        }

        BinaryStdOut.flush();
	}
	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args)
	{
		if (args[0].equals("-"))
			encode();

		if (args[0].equals("+"))
			decode();
	}
}