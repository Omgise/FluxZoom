package flux.zoom.client;

import flux.zoom.FluxZoom;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import flux.zoom.ItemBinoculars;
import flux.zoom.ItemSpyglass;
import flux.zoom.client.model.ModelBinoculars;
import flux.zoom.client.model.ModelSpyGlass;

public class ItemRenderer implements IItemRenderer {
    
    private static final Minecraft mc = Minecraft.getMinecraft();
    private final ModelBinoculars binoculars = new ModelBinoculars();
    private final ModelSpyGlass spyglass = new ModelSpyGlass();
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }
    
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
        
        final boolean renderSpyglass = item != null && item.getItem() instanceof ItemSpyglass;
        final boolean renderBinoculars = item != null && item.getItem() instanceof ItemBinoculars;

        if (renderSpyglass) {
            mc.renderEngine.bindTexture(new ResourceLocation(FluxZoom.MODID, "textures/models/spyglass.png"));
        } else if (renderBinoculars) {
            mc.renderEngine.bindTexture(new ResourceLocation(FluxZoom.MODID, "textures/models/binoculars.png"));
        } else {
            // Fallback (should never happen because this renderer is only registered for our items).
            mc.renderEngine.bindTexture(new ResourceLocation(FluxZoom.MODID, "textures/models/binoculars.png"));
        }
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.09F, 0.09F, 0.09F);
        // The spyglass model's geometry is positioned around Y=12..21, so translate it down into view.
        if (renderSpyglass) {
            GL11.glScalef(1.15F, 1.15F, 1.15F);
            GL11.glTranslatef(2.25F, -16F, -1.8F);
            GL11.glRotatef(-115.0F, 0.0F, 1.0F, 0.0F);
        } else {
            GL11.glTranslatef(0.0F, 0.0F, -1.8F);
        }
        
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(3.0F, -7.0F, -0.0F);
            if (renderSpyglass) {
                GL11.glRotatef(30F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(16.5F, 0.0F, 0.0F, 1.0F);
               // GL11.glRotatef(2.5F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(3.13F, 0.0F, -2F);
            }
        }
        
        if (renderSpyglass) {
            this.spyglass.render(1.0F);
        } else {
            this.binoculars.render(1.0F);
        }
    }
    
}
