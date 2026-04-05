package com.vesperia.hub;

import com.vesperia.hub.client.VesperiaHUD;
import com.vesperia.hub.client.EffectsManager;
import com.vesperia.hub.client.TrajectoryManager;
import com.vesperia.hub.client.gui.VesperiaSettingsScreen;
import com.vesperia.hub.config.VesperiaConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VesperiaHubClient implements ClientModInitializer {
    public static final String MOD_ID = "vesperia-hub";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static VesperiaHubClient INSTANCE;
    
    private KeyBinding zoomKey;
    private KeyBinding settingsKey;
    private VesperiaHUD hud;
    private EffectsManager effects;
    private TrajectoryManager trajectory;
    private int cps = 0;
    private int clicks = 0;
    private long lastClickTime = 0;

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
            KeyBinding.Category.MISC
        ));

        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.vesperia-hub.settings",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            KeyBinding.Category.MISC
        ));

        hud = new VesperiaHUD();
        effects = new EffectsManager();
        trajectory = new TrajectoryManager();

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        LOGGER.info("Vesperia Hub initialized!");
        LOGGER.info("Z = Zoom | RSHIFT = Settings");
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null) return;

        if (settingsKey.wasPressed()) {
            client.setScreen(new VesperiaSettingsScreen(client.currentScreen));
            return;
        }

        effects.tick();
        
        if (VesperiaConfig.TRAJECTORY_PREDICTION) {
            trajectory.update();
        }

        if (VesperiaConfig.ZOOM && zoomKey.wasPressed()) {
            int currentFov = client.options.getFov().getValue();
            if (currentFov == 70) {
                client.options.getFov().setValue((int)(70f * VesperiaConfig.ZOOM_LEVEL));
            } else {
                client.options.getFov().setValue(70);
            }
        }

        if (client.options.jumpKey.wasPressed() && VesperiaConfig.JUMP_CIRCLE) {
            effects.onJump(new Vec3d(client.player.getX(), client.player.getY(), client.player.getZ()));
        }

        long now = System.currentTimeMillis();
        if (now - lastClickTime > 1000) {
            cps = clicks;
            clicks = 0;
            lastClickTime = now;
        }
        clicks++;
    }

    public static VesperiaHubClient getInstance() {
        return INSTANCE;
    }

    public VesperiaHUD getHUD() {
        return hud;
    }

    public EffectsManager getEffects() {
        return effects;
    }

    public TrajectoryManager getTrajectory() {
        return trajectory;
    }

    public KeyBinding getZoomKey() {
        return zoomKey;
    }

    public KeyBinding getSettingsKey() {
        return settingsKey;
    }

    public int getCps() {
        return cps;
    }
}
