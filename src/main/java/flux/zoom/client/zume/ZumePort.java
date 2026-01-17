package flux.zoom.client.zume;

/**
 * Self-contained port of Zume's core zoom logic (defaults only).
 *
 * This class is intentionally structured like Zume.impl.Zume, but without config IO
 * or platform-specific integrations.
 */
public final class ZumePort {

    // Default ZumeConfig values
    public static boolean enableCinematicZoom = true;
    public static double mouseSensitivityFloor = 0.4D;
    public static short zoomSpeed = 20;
    public static boolean enableZoomScrolling = true;
    public static short zoomSmoothnessMs = 150;
    public static double animationEasingExponent = 4D;
    public static double zoomEasingExponent = 2D;
    public static double defaultZoom = 0.5D;
    public static boolean toggleMode = false;
    public static boolean thirdPersonToggleMode = true;
    public static double minFOV = 1D;
    public static double maxThirdPersonZoomDistance = 15D;
    public static double minThirdPersonZoomDistance = 0.5D;

    private static IZumeImplementation implementation;
    private static boolean disabled = false;

    private static final EasedDouble zoom = new EasedDouble(1D);
    private static int scrollDelta = 0;
    private static boolean wasHeld = false;
    private static boolean zooming = false;
    private static boolean wasZooming = false;
    private static long prevRenderTimestamp;

    private ZumePort() {}

    public static void init(final IZumeImplementation impl) {
        implementation = impl;
        zoom.update(zoomSmoothnessMs, animationEasingExponent);
        prevRenderTimestamp = System.currentTimeMillis();
    }

    // ---- API methods (mirroring Zume) ----

    public static double fovHook(final double original) {
        return EasingUtil.out(minFOV, original, getZoom(), zoomEasingExponent);
    }

    public static double thirdPersonCameraHook(final double original) {
        if (shouldUseFirstPersonZoom() || !shouldHook())
            return original;

        return original * 0.25D * EasingUtil.out(
            minThirdPersonZoomDistance,
            maxThirdPersonZoomDistance,
            getZoom(),
            zoomEasingExponent
        );
    }

    public static boolean cinematicCameraEnabledHook(final boolean original) {
        if (enableCinematicZoom && isActive() && shouldUseFirstPersonZoom())
            return true;
        return original;
    }

    public static double mouseSensitivityHook(final double original) {
        if (!isActive() || !shouldUseFirstPersonZoom())
            return original;
        return original * EasingUtil.linear(mouseSensitivityFloor, 1D, getZoom());
    }

    public static boolean isMouseScrollHookActive() {
        return enableZoomScrolling && isActive();
    }

    public static boolean mouseScrollHook(final int delta) {
        if (!isMouseScrollHookActive() || delta == 0)
            return false;
        scrollDelta += MathUtil.sign(delta);
        return true;
    }

    public static boolean isActive() {
        if (disabled || implementation == null)
            return false;
        return zooming;
    }

    public static boolean isFOVHookActive() {
        return shouldHook() && shouldUseFirstPersonZoom();
    }

    public static void renderHook() {
        if (disabled || implementation == null)
            return;

        final long timestamp = System.currentTimeMillis();
        final boolean held = implementation.isZoomPressed();
        final boolean toggle = getToggleMode();

        if (toggle && held && !wasHeld) {
            zooming = !zooming;
        } else if (!toggle) {
            zooming = held;
        }

        if (zooming) {
            if (!wasZooming) {
                onZoomActivate();
            }

            final long timeDelta = timestamp - prevRenderTimestamp;

            if (enableZoomScrolling && scrollDelta != 0) {
                setZoom(zoom.getTarget() - (scrollDelta * zoomSpeed * 4E-3D));
            } else if (implementation.isZoomInPressed() ^ implementation.isZoomOutPressed()) {
                final double interpolatedIncrement = zoomSpeed * 1E-4D * timeDelta;
                if (implementation.isZoomInPressed()) {
                    setZoom(zoom.getTarget() - interpolatedIncrement);
                } else if (implementation.isZoomOutPressed()) {
                    setZoom(zoom.getTarget() + interpolatedIncrement);
                }
            }
        } else if (wasZooming) {
            onZoomDeactivate();
        }

        scrollDelta = 0;
        prevRenderTimestamp = timestamp;
        wasHeld = held;
        wasZooming = zooming;
    }

    // ---- internals ----

    private static double getZoom() {
        return zoom.getEased();
    }

    private static void setZoom(final double targetZoom) {
        zoom.set(MathUtil.clamp(targetZoom, 0D, 1D));
    }

    private static void setZoom(final double fromZoom, final double targetZoom) {
        zoom.set(MathUtil.clamp(fromZoom, 0D, 1D), MathUtil.clamp(targetZoom, 0D, 1D));
    }

    private static double getThirdPersonStartZoom() {
        return EasingUtil.inverseOut(
            minThirdPersonZoomDistance,
            maxThirdPersonZoomDistance,
            4D,
            zoomEasingExponent
        );
    }

    private static void onZoomActivate() {
        implementation.onZoomActivate();

        if (shouldUseFirstPersonZoom()) {
            setZoom(1D, 1D - defaultZoom);
        } else {
            final double from = getThirdPersonStartZoom();
            final double target;

            if (implementation.getCameraPerspective() == CameraPerspective.THIRD_PERSON) {
                target = EasingUtil.linear(1D, from, defaultZoom);
            } else {
                target = EasingUtil.linear(from, 0D, defaultZoom);
            }

            setZoom(from, target);
        }
    }

    private static void onZoomDeactivate() {
        if (shouldUseFirstPersonZoom()) {
            setZoom(1D);
        } else {
            setZoom(getThirdPersonStartZoom());
        }
    }

    private static boolean getToggleMode() {
        return shouldUseFirstPersonZoom() ? toggleMode : thirdPersonToggleMode;
    }

    private static boolean shouldHook() {
        if (disabled || implementation == null)
            return false;
        return isActive() || zoom.isEasing();
    }

    private static boolean shouldUseFirstPersonZoom() {
        return maxThirdPersonZoomDistance == 0D ||
            implementation.getCameraPerspective() == CameraPerspective.FIRST_PERSON;
    }
}
