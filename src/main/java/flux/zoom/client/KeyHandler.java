package flux.zoom.client;

import flux.zoom.FluxZoom;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyHandler {
    
    public static KeyBinding keyZoom;
    
    public static void init() {
        keyZoom = new KeyBinding(FluxZoom.PREFIX + "keybind.zoom", Keyboard.KEY_Z, "Zoom");
        ClientRegistry.registerKeyBinding(keyZoom);
    }
    
}
