package com.vesperia.hub;

import com.vesperia.hub.client.VesperiaHUD;
import com.vesperia.hub.config.VesperiaConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.option.GameOptions;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VesperiaHubClient implements ClientModInitializer {
    public static final String MOD_ID = "vesperia-hub";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static VesperiaHubClient INSTANCE;
    
    private KeyBinding zoomKey;
    private VesperiaHUD hud;
    private int cps = 0;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("===========================================");
        LOGGER.info("Vesperia Hub initializing...");
        LOGGER.info("===========================================");

        VesperiaConfig.load();
        
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.vesperia-hub.zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            KeyBinding.Category.MISCELLANEOUS
        ));

        hud = new VesperiaHUD();

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        LOGGER.info("Vesperia Hub initialized!");
        LOGGER.info("Z = Zoom");
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null) return;

        if (VesperiaConfig.ZOOM && zoomKey.wasPressed()) {
            int currentFov = client.options.getFov().getValue();
            if (currentFov == 70) {
                client.options.getFov().setValue((int)(70f * VesperiaConfig.ZOOM_LEVEL));
            } else {
                client.options.getFov().setValue(70);
            }
        }
    }

    public static VesperiaHubClient getInstance() {
        return INSTANCE;
    }

    public VesperiaHUD getHUD() {
        return hud;
    }

    public KeyBinding getZoomKey() {
        return zoomKey;
    }

    public int getCps() {
        return cps;
    }

    public void setCps(int cps) {
        this.cps = cps;
    }
}
