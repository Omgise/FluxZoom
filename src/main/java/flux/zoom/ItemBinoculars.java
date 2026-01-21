package flux.zoom;

import java.util.List;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import flux.zoom.client.KeyHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBinoculars extends Item {
    
    public ItemBinoculars() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName(FluxZoom.PREFIX + "binoculars");
        this.setTextureName(FluxZoom.RESOURCE_PREFIX + "binoculars");
        this.setCreativeTab(CreativeTabs.tabTools);
        
        GameRegistry.registerItem(this, "binoculars");
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        // Intentionally do NOT call setItemInUse().
        // Using an item triggers vanilla "item use" behaviour which slows player movement.
        // Zoom is handled client-side (key state + held item) in the EventHandler.
        return itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack itemStack) {
        return Integer.MAX_VALUE;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
        list.add(StatCollector.translateToLocal("item.fluxzoom.binoculars.desc.1"));
        if (KeyHandler.keyZoom.getKeyCode() != 0) {
            list.add(StatCollector.translateToLocalFormatted("item.fluxzoom.binoculars.desc.2", EnumChatFormatting.AQUA + GameSettings.getKeyDisplayString(KeyHandler.keyZoom.getKeyCode()) + EnumChatFormatting.GRAY));
        }
    }
}
