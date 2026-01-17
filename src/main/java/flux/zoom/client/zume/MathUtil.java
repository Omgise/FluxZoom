package flux.zoom.client.zume;

/**
 * Tiny math helpers used by the Zume zoom logic.
 */
final class MathUtil {

    private MathUtil() {}

    static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static int sign(int value) {
        return value < 0 ? -1 : (value > 0 ? 1 : 0);
    }

    static int sign(double value) {
        return value < 0D ? -1 : (value > 0D ? 1 : 0);
    }
}
