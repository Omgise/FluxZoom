package flux.zoom.client.zume.impl;


public final class MathUtil {
	
	private MathUtil() {}
	
	public static int sign(final int input) {
		return input >> (Integer.SIZE - 1) | 1;
	}
	
	public static long sign(final long input) {
		return input >> (Long.SIZE - 1) | 1;
	}
	
	public static float sign(final float input) {
		return Math.signum(input);
	}
	
	public static double sign(final double input) {
		return Math.signum(input);
	}
	
	public static double clamp(final double value, final double min, final double max) {
		return Math.max(Math.min(value, max), min);
	}
	
	public static int mod(final int numerator, final int denominator) {
		return (numerator % denominator + denominator) % denominator;
	}
	
	public static double mod(final double numerator, final double denominator) {
		return (numerator % denominator + denominator) % denominator;
	}
	
	public static long pack(int left, int right) {
		return (((long) left) << Integer.SIZE) | (right & ~(~0L << Integer.SIZE));
	}
	
	public static int unpackLeft(long packed) {
		return (int) (packed >> Integer.SIZE);
	}
	
	public static int unpackRight(long packed) {
		return (int) packed;
	}
	
}
