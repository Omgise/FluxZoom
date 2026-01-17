package flux.zoom.client.zume;

import flux.zoom.client.KeyHandler;
import flux.zoom.client.mixin.EntityRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MouseFilter;

/**
 * Archaic (1.7.10) platform glue that mirrors Zume's ArchaicZume implementation.
 *
 * Zoom is gated by {@link ZoomGate} so the feature still requires binoculars
 * (inventory for keybind, held+use-item for right click).
 */
public final class FluxZumeImplementation implements IZumeImplementation {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public boolean isZoomPressed() {
        // Match Zume: don't zoom while a GUI is open.
        if (mc.currentScreen != null) return false;
        return ZoomGate.isZoomActive();
    }

    @Override
    public boolean isZoomInPressed() {
        return KeyHandler.keyZoomIn != null && KeyHandler.keyZoomIn.getIsKeyPressed();
    }

    @Override
    public boolean isZoomOutPressed() {
        return KeyHandler.keyZoomOut != null && KeyHandler.keyZoomOut.getIsKeyPressed();
    }

    @Override
    public CameraPerspective getCameraPerspective() {
        final int view = mc.gameSettings.thirdPersonView;
        if (view == 0) return CameraPerspective.FIRST_PERSON;
        if (view == 2) return CameraPerspective.THIRD_PERSON_FRONT;
        return CameraPerspective.THIRD_PERSON;
    }

    @Override
    public void onZoomActivate() {
        // Mirror Zume's cinematic init: if cinematic zoom is enabled and the user
        // does NOT have smoothCamera enabled, we reset the smooth cam filters so
        // the transition feels the same.
        if (ZumePort.enableCinematicZoom && !mc.gameSettings.smoothCamera) {
            final EntityRendererAccessor er = (EntityRendererAccessor) mc.entityRenderer;
            // Use the accessor method names that exist in our mixin accessor.
            er.fluxzoom$setMouseFilterXAxis(new MouseFilter());
            er.fluxzoom$setMouseFilterYAxis(new MouseFilter());
            er.fluxzoom$setSmoothCamYaw(0F);
            er.fluxzoom$setSmoothCamPitch(0F);
            er.fluxzoom$setSmoothCamFilterX(0F);
            er.fluxzoom$setSmoothCamFilterY(0F);
            er.fluxzoom$setSmoothCamPartialTicks(0F);
        }
    }
}
