package flux.zoom.client;

import flux.zoom.ItemBinoculars;
import flux.zoom.FluxZoom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class EventHandler {
    
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    private static final float MIN_ZOOM = 1 / 1.5F;
    private static final float MAX_ZOOM = 1 / 10.0F;
    private static final float DEFAULT_ZOOM = 1 / 6.0F;
    private static float currentZoom = DEFAULT_ZOOM;

    /**
     * Tracks transitions so we can reset zoom every time zoom starts.
     */
    private static boolean wasZooming = false;
    
    private static boolean renderPlayerAPILoaded = false;
    
    public static void init() {
        renderPlayerAPILoaded = Loader.isModLoaded("RenderPlayerAPI");
        
        EventHandler handler = new EventHandler();
        FMLCommonHandler.instance().bus().register(handler);
        MinecraftForge.EVENT_BUS.register(handler);
    }
    
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent evt) {
        if (isUsingBinoculars() && mc.gameSettings.thirdPersonView == 0) {
            evt.newfov = currentZoom;
        }
    }
    
    @SubscribeEvent
    public void onMouseScroll(MouseEvent evt) {
        if (isUsingBinoculars() && evt.dwheel != 0 && mc.gameSettings.thirdPersonView == 0) {
            currentZoom = 1 / Math.min(Math.max(1 / currentZoom + evt.dwheel / 180F, 1 / MIN_ZOOM), 1 / MAX_ZOOM);
            evt.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent evt) {
        if (evt.phase != Phase.END) {
            return;
        }

        boolean zoomingNow = isUsingBinoculars();
        if (zoomingNow && !wasZooming) {
            // Reset zoom every time we begin zooming (do not remember between sessions)
            currentZoom = DEFAULT_ZOOM;
        }
        wasZooming = zoomingNow;
    }
    
    @SubscribeEvent
    public void onRenderTick(RenderTickEvent evt) {
        // Intentionally left blank.
        // The original binocular overlay texture has been removed.
    }
    
    @SubscribeEvent
    public void onRenderHand(RenderHandEvent evt) {
        if (isUsingBinoculars()) {
            evt.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRenderHeldItem(RenderPlayerEvent.Specials.Pre evt) {
        if (renderPlayerAPILoaded && isUsingBinoculars(evt.entityPlayer, false)) {
            evt.renderItem = false;
        }
    }
    
    private static boolean isUsingBinoculars(EntityPlayer player, boolean keybind) {
        // Right-click zoom: binoculars must be held AND the use-item key must be held.
        ItemStack held = player.getHeldItem();
        if (held != null && held.getItem() instanceof ItemBinoculars) {
            if (mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
                return true;
            }
        }

        // Keybind zoom: binoculars must exist somewhere in inventory.
        if (keybind && KeyHandler.keyZoom.getIsKeyPressed()) {
            for (ItemStack invStack : player.inventory.mainInventory) {
                if (invStack != null && invStack.getItem() instanceof ItemBinoculars) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean isUsingBinoculars(boolean keybind) {
        EntityPlayer player = mc.thePlayer;
        if (player == null) {
            return false;
        }
        return isUsingBinoculars(player, keybind);
    }
    
    private static boolean isUsingBinoculars() {
        return isUsingBinoculars(true);
    }
    
}
