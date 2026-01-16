package flux.zoom.client;

import flux.zoom.CommonProxy;
import flux.zoom.FluxZoom;
import flux.zoom.client.model.ModelPlayerCustom;
import net.minecraftforge.client.MinecraftForgeClient;
import api.player.model.ModelPlayerAPI;
import cpw.mods.fml.common.Loader;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerHandlers() {
        super.registerHandlers();
        EventHandler.init();
        KeyHandler.init();
        MinecraftForgeClient.registerItemRenderer(FluxZoom.itemBinoculars, new ItemRenderer());
        
        if (Loader.isModLoaded("RenderPlayerAPI")) {
            ModelPlayerAPI.register(FluxZoom.MODID, ModelPlayerCustom.class);
        }
    }
    
}
