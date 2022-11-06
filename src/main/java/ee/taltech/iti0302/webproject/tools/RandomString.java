package ee.taltech.iti0302.webproject.tools;

import java.util.Random;

public class RandomString {
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String LOWER = UPPER.toLowerCase();

    public static final String DIGITS = "0123456789";

    public static final String ALPHANUMERIC = UPPER + LOWER + DIGITS;

    private final Random random;

    private final char[] symbols;

    private final char[] chars;

    public RandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();
        this.random = new Random();
        this.symbols = ALPHANUMERIC.toCharArray();
        this.chars = new char[length];
    }

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < chars.length; ++idx)
            chars[idx] = symbols[random.nextInt(symbols.length)];
        return new String(chars);
    }

}
