package krm.encryption;

public class Util {

    public static final int BITS_IN_TYPE_CHART = 16;

    public static final int EVENT_BITS_IN_CHART = 21845;
    public static final int NOT_EVENT_BITS_IN_CHART = 43690;

    /**
     * циклический сдвиг влево
     */
    public static char cyclicShiftLeftChar(char initial, int shift) {
        shift = shift % BITS_IN_TYPE_CHART;
        return (char) ((initial << shift) | (initial >> (BITS_IN_TYPE_CHART - shift)));
    }

    /**
     * циклический сдвиг вправо
     */
    public static char cyclicShiftRightChar(char initial, int shift) {
        shift = shift % BITS_IN_TYPE_CHART;
        return (char) ((initial >> shift) | (initial << (BITS_IN_TYPE_CHART - shift)));
    }

    /**
     * перестановка
     */
    public static char permutation(char initial) {
        char shiftL = (char) (initial & NOT_EVENT_BITS_IN_CHART);
        char shiftR = (char) (initial & EVENT_BITS_IN_CHART);

        return (char) ((shiftL >>> 1) | (shiftR << 1));
    }

    public static char  encrypt(char plainText, char key, char vector) {
        char keyI = (char) (key | vector);

        char cipherЕext = (char) (plainText ^ keyI);

        return cyclicShiftLeftChar(cipherЕext, keyI);
    }

    public static char  decrypt(char plainText, char key, char vector) {
        char keyI = (char) (key | vector);

        char cipherЕext = cyclicShiftRightChar(plainText, keyI);

        return (char) (cipherЕext ^ keyI);
    }

    public static char  crypt(char plainText, char key, char vector) {
        char keyI = (char) (key ^ vector);

        char cipherText = (char) (plainText ^ keyI);
        cipherText = permutation(cipherText);
        cipherText = (char) (cipherText ^ keyI);

        return  cipherText;
    }

    public static void main(String[] args) {
        System.out.println(crypt('u', 'd', 'l'));
        System.out.println(crypt('¶', 'D', 'L'));
    }
}
