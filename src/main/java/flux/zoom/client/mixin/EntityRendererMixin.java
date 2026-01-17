package flux.zoom.client.mixin;

import flux.zoom.client.zume.ZumeEngine;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin hooks matching Zume's 1.7.10 ("archaic") hook points.
 */
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Inject(method = "updateCameraAndRender", at = @At("HEAD"))
    private void fluxzoom$updateCameraAndRender$HEAD(CallbackInfo ci) {
        ZumeEngine.renderHook();
    }

    @Inject(method = "getFOVModifier", at = @At("RETURN"), cancellable = true)
    private void fluxzoom$getFOVModifier$RETURN(float partialTicks, boolean useFOVSetting, CallbackInfoReturnable<Float> cir) {
        if (ZumeEngine.isFOVHookActive()) {
            final float original = cir.getReturnValue();
            cir.setReturnValue((float) ZumeEngine.fovHook(original));
        }
    }

    // Cinematic camera enable hook (smoothCamera)
    @Redirect(
        method = {"updateCameraAndRender", "updateRenderer"},
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;smoothCamera:Z")
    )
    private boolean fluxzoom$smoothCameraHook(GameSettings settings) {
        return ZumeEngine.cinematicCameraEnabledHook(settings.smoothCamera);
    }

    // Mouse sensitivity hook
    @Redirect(
        method = {"updateCameraAndRender", "updateRenderer"},
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;mouseSensitivity:F")
    )
    private float fluxzoom$mouseSensitivityHook(GameSettings settings) {
        return (float) ZumeEngine.mouseSensitivityHook(settings.mouseSensitivity);
    }

    // Third-person camera distance hooks
    @Redirect(
        method = "orientCamera",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistance:F")
    )
    private float fluxzoom$thirdPersonDistanceHook(EntityRenderer self) {
        final EntityRendererAccessor acc = (EntityRendererAccessor) self;
        return (float) ZumeEngine.thirdPersonCameraHook(acc.fluxzoom$getThirdPersonDistance());
    }

    @Redirect(
        method = "orientCamera",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistanceTemp:F")
    )
    private float fluxzoom$thirdPersonDistanceTempHook(EntityRenderer self) {
        final EntityRendererAccessor acc = (EntityRendererAccessor) self;
        return (float) ZumeEngine.thirdPersonCameraHook(acc.fluxzoom$getThirdPersonDistanceTemp());
    }
}
