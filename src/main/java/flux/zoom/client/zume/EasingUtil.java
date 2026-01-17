package flux.zoom.client.zume;

/**
 * Small easing helpers matching the functions used by Zume.
 *
 * All inputs are expected in the range [0,1] unless otherwise noted.
 */
final class EasingUtil {

    private EasingUtil() {}

    static double linear(double from, double to, double t) {
        return from + (to - from) * t;
    }

    /**
     * Ease-in using exponent (equivalent to Zume's EasingUtil.in).
     */
    static double in(double from, double to, double t, double exponent) {
        if (t <= 0D) return from;
        if (t >= 1D) return to;
        final double eased = Math.pow(t, exponent);
        return from + (to - from) * eased;
    }

    /**
     * Ease-out using exponent (equivalent to Zume's EasingUtil.out).
     */
    static double out(double from, double to, double t, double exponent) {
        if (t <= 0D) return from;
        if (t >= 1D) return to;
        final double eased = 1D - Math.pow(1D - t, exponent);
        return from + (to - from) * eased;
    }

    /**
     * Inverse of {@link #out(double, double, double, double)} for a value inside [from,to].
     */
    static double inverseOut(double from, double to, double value, double exponent) {
        if (to == from) return 0D;
        final double norm = (value - from) / (to - from);
        final double clamped = MathUtil.clamp(norm, 0D, 1D);
        // out(t) = 1 - (1 - t)^e => (1 - t)^e = 1 - out => 1 - t = (1 - out)^(1/e)
        return 1D - Math.pow(1D - clamped, 1D / exponent);
    }
}
