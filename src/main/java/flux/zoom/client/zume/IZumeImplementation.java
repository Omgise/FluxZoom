package flux.zoom.client.zume;

public interface IZumeImplementation {

    boolean isZoomPressed();

    boolean isZoomInPressed();

    boolean isZoomOutPressed();

    CameraPerspective getCameraPerspective();

    void onZoomActivate();
}
