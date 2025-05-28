package co.edu.uptc.utils;

import java.util.Random;

public class RandomUtil {

    private RandomUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    private static final Random RANDOM = new Random();

    public static int getRandomNumber(int n) {
        return RANDOM.nextInt(n + 1);
    }
}
