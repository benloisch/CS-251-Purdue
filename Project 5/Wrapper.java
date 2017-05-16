/**
 * Created by Ben Loisch on 4/27/2017.
 */
public class Wrapper {
    public String s;
    public int index;
    public int length;

    static void getLastChars(Wrapper[] ws, char[] ch) {
        for (int i = 0; i < ws.length; i++) {
            Wrapper w = ws[i];
            char character;
            if (w.index == 0) {
                character = ch[w.length - 1];
            }
            else {
                character = ch[w.index -1];
            }
            BinaryStdOut.write(character);
        }

        BinaryStdOut.flush();
    }

    static void findZeroIndex(Wrapper[] ws) {
        for (int i = 0; i < ws.length; i++) {
            if (ws[i].index == 0) {
                BinaryStdOut.write(i);
            }
        }

        BinaryStdOut.flush();
    }

    public int compareTo(Wrapper other) {
        for (int i = 0; i < this.length && i < other.length; i++) {
            if (this.charAt(i) > other.charAt(i))
                return 1;
            else if (this.charAt(i) < other.charAt(i))
                return -1;
        }

        return 0;
    }

    Wrapper(String s, int index, int length) {
        this.s = s;
        this.index = index;
        this.length = length;
    }

    int length() {
        return length;
    }

    char charAt(int c) {
        char retChar = s.charAt((c + index) % length);
        return retChar;
    }

    Wrapper substring(int s1) {
        return this.substring(s1, length);
    }

    Wrapper substring(int s1, int s2) {

        if (s2 > length || s1 > s2 || s1 < 0)
            return null;

        int start = s1 + index;

        Wrapper w = new Wrapper(this.s, start, s2 - s1);

        return w;
    }
}
