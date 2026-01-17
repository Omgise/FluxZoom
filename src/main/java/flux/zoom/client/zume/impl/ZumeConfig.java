package flux.zoom.client.zume.impl;

/**
 * Minimal config mirror of Zume defaults (archaic).
 *
 * In-memory defaults only (no file IO) to keep dependencies minimal.
 */
public class ZumeConfig {

    public boolean enableCinematicZoom = true;
    public double mouseSensitivityFloor = 0.4D;
    public short zoomSpeed = 20;
    public boolean enableZoomScrolling = true;
    public short zoomSmoothnessMs = 150;
    public double animationEasingExponent = 4D;
    public double zoomEasingExponent = 2D;
    public double defaultZoom = 0.5D;
    public boolean toggleMode = false;
    public boolean thirdPersonToggleMode = true;
    public double minFOV = 1D;
    public double maxThirdPersonZoomDistance = 15D;
    public double minThirdPersonZoomDistance = 0.5D;
    public boolean disable = false;
}
