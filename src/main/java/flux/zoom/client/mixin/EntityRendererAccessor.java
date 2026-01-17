package flux.zoom.client.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.MouseFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
public interface EntityRendererAccessor {

    @Accessor("thirdPersonDistance")
    float fluxzoom$getThirdPersonDistance();

    @Accessor("thirdPersonDistanceTemp")
    float fluxzoom$getThirdPersonDistanceTemp();

    @Accessor("smoothCamFilterX")
    void setSmoothCamFilterX(float value);

    // Some classes call prefixed names (historical reason). Provide aliases.
    @Accessor("smoothCamFilterX")
    void fluxzoom$setSmoothCamFilterX(float value);

    @Accessor("smoothCamFilterY")
    void setSmoothCamFilterY(float value);

    @Accessor("smoothCamFilterY")
    void fluxzoom$setSmoothCamFilterY(float value);

    @Accessor("smoothCamYaw")
    void setSmoothCamYaw(float value);

    @Accessor("smoothCamYaw")
    void fluxzoom$setSmoothCamYaw(float value);

    @Accessor("smoothCamPitch")
    void setSmoothCamPitch(float value);

    @Accessor("smoothCamPitch")
    void fluxzoom$setSmoothCamPitch(float value);

    @Accessor("smoothCamPartialTicks")
    void setSmoothCamPartialTicks(float value);

    @Accessor("smoothCamPartialTicks")
    void fluxzoom$setSmoothCamPartialTicks(float value);

    @Accessor("mouseFilterXAxis")
    void setMouseFilterXAxis(MouseFilter value);

    @Accessor("mouseFilterXAxis")
    void fluxzoom$setMouseFilterXAxis(MouseFilter value);

    @Accessor("mouseFilterYAxis")
    void setMouseFilterYAxis(MouseFilter value);

    @Accessor("mouseFilterYAxis")
    void fluxzoom$setMouseFilterYAxis(MouseFilter value);
}
