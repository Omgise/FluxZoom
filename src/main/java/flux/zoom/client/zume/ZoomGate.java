package flux.zoom.client.zume;

import flux.zoom.ItemBinoculars;
import flux.zoom.client.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

/**
 * Single source of truth for when zoom is allowed/active.
 *
 * User rules:
 * - Right-click zoom works only while HOLDING binoculars and HOLDING use-item.
 * - Keybind zoom works only if binoculars exist somewhere in inventory.
 * - Third person zooming is allowed, but still requires binoculars for the keybind.
 */
public final class ZoomGate {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private ZoomGate() {}

    public static boolean isZoomActive() {
        if (mc.thePlayer == null) {
            return false;
        }

        // Keybind path (works in any perspective): requires binoculars anywhere in inventory.
        if (isZoomKeyDown() && hasBinocularsInInventory()) {
            return true;
        }

        // Right-click path (works in any perspective): binoculars must be held AND use-item held.
        return isHoldingBinoculars() && mc.gameSettings.keyBindUseItem.getIsKeyPressed();
    }

    private static boolean isZoomKeyDown() {
        return KeyHandler.keyZoom != null && KeyHandler.keyZoom.getIsKeyPressed();
    }

    private static boolean isHoldingBinoculars() {
        ItemStack held = mc.thePlayer.getHeldItem();
        return held != null && held.getItem() instanceof ItemBinoculars;
    }

    private static boolean hasBinocularsInInventory() {
        for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
            if (stack != null && stack.getItem() instanceof ItemBinoculars) {
                return true;
            }
        }
        return false;
    }
}
