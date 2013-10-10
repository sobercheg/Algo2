package util;

/**
 * Created by Sobercheg on 10/9/13.
 */
public class Assert {

    public static void equals(Object o1, Object o2, String message) {
        if (!o1.equals(o2)) throw new IllegalStateException(message);
    }

    public static void assertFalse(boolean expression, String message) {
        if (expression) throw new IllegalStateException(message);
    }

    public static void assertTrue(boolean expression, String message) {
        if (!expression) throw new IllegalStateException(message);
    }
}
