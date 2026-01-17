package flux.zoom.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Spyglass model replicated from the provided model code.
 */
public class ModelSpyGlass extends ModelBase {

    private final ModelRenderer eyelid;
    private final ModelRenderer handle;
    private final ModelRenderer magnifier;

    public ModelSpyGlass() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.eyelid = new ModelRenderer(this, 0, 14);
        this.eyelid.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.eyelid.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.eyelid.setTextureSize(64, 32);
        this.setRotation(this.eyelid, 0.0F, 0.0F, 0.0F);

        this.handle = new ModelRenderer(this, 0, 8);
        this.handle.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
        this.handle.setRotationPoint(-0.5F, 17.0F, -0.5F);
        this.handle.setTextureSize(64, 32);
        this.setRotation(this.handle, 0.0F, 0.0F, 0.0F);

        this.magnifier = new ModelRenderer(this, 0, 0);
        this.magnifier.addBox(0.0F, 0.0F, 0.0F, 3, 5, 3);
        this.magnifier.setRotationPoint(-1.0F, 12.0F, -1.0F);
        this.magnifier.setTextureSize(64, 32);
        this.magnifier.mirror = true;
        this.setRotation(this.magnifier, 0.0F, 0.0F, 0.0F);
    }

    public void render(float size) {
        this.eyelid.render(size);
        this.handle.render(size);
        this.magnifier.render(size);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
