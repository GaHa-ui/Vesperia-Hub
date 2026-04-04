package com.vesperia.hub;

import com.vesperia.hub.client.effects.*;
import com.vesperia.hub.client.hud.*;
import com.vesperia.hub.client.crosshair.*;
import com.vesperia.hub.client.pvp.*;
import com.vesperia.hub.client.qol.*;
import com.vesperia.hub.client.builders.*;
import com.vesperia.hub.client.stream.*;
import com.vesperia.hub.client.managers.*;
import com.vesperia.hub.client.gui.VesperiaSettingsScreen;
import com.vesperia.hub.config.VesperiaConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VesperiaHubClient implements ClientModInitializer {
    public static final String MOD_ID = "vesperia-hub";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static VesperiaHubClient INSTANCE;

    private KeyBinding zoomKey;
    private KeyBinding configKey;

    private HitEffectManager hitEffectManager;
    private DamageNumberManager damageNumberManager;
    private TargetHUDManager targetHUDManager;
    private ComboCounterManager comboCounterManager;
    private CrosshairManager crosshairManager;
    private HUDManager hudManager;
    private QOLManager qolManager;
    private EffectRenderer effectRenderer;
    private TrajectoryManager trajectoryManager;
    private BuilderHelperManager builderHelperManager;
    private AutoTotemIndicator autoTotemIndicator;
    private StreamEffectsManager streamEffectsManager;
    private ViewModelManager viewModelManager;
    private DynamicLightsManager dynamicLightsManager;
    private EnhancedTooltipsManager enhancedTooltipsManager;
    private MLGIndicatorManager mlgIndicatorManager;
    private ParkourHelperManager parkourHelperManager;
    private SoundVisualizerManager soundVisualizerManager;
    private SoundIndicatorsManager soundIndicatorsManager;
    private ClientEventHandler eventHandler;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("===========================================");
        LOGGER.info("Vesperia Hub initializing...");
        LOGGER.info("60 visual features loaded!");
        LOGGER.info("===========================================");

        VesperiaConfig.getInstance().load();

        initKeybindings();
        initManagers();

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        LOGGER.info("Vesperia Hub initialized!");
        LOGGER.info("Press RSHIFT to open settings");
    }

    private void initKeybindings() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.vesperia-hub.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category.vesperia-hub"
        ));

        configKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.vesperia-hub.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.vesperia-hub"
        ));
    }

    private void initManagers() {
        hitEffectManager = new HitEffectManager();
        damageNumberManager = new DamageNumberManager();
        targetHUDManager = new TargetHUDManager();
        comboCounterManager = new ComboCounterManager();
        crosshairManager = new CrosshairManager();
        hudManager = new HUDManager();
        qolManager = new QOLManager();
        effectRenderer = new EffectRenderer();
        trajectoryManager = new TrajectoryManager();
        builderHelperManager = new BuilderHelperManager();
        autoTotemIndicator = new AutoTotemIndicator();
        streamEffectsManager = new StreamEffectsManager();
        viewModelManager = new ViewModelManager();
        dynamicLightsManager = new DynamicLightsManager();
        enhancedTooltipsManager = new EnhancedTooltipsManager();
        mlgIndicatorManager = new MLGIndicatorManager();
        parkourHelperManager = new ParkourHelperManager();
        soundVisualizerManager = new SoundVisualizerManager();
        soundIndicatorsManager = new SoundIndicatorsManager();
        eventHandler = new ClientEventHandler(this);
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        if (configKey.wasPressed()) {
            client.setScreen(new VesperiaSettingsScreen(client.currentScreen));
            return;
        }

        crosshairManager.update();

        if (VesperiaConfig.QOL_MANAGER) {
            qolManager.tick(client);
        }

        if (VesperiaConfig.TRAJECTORY_PREDICTION) {
            trajectoryManager.update();
        }

        if (VesperiaConfig.BLOCK_IN_VISUALIZER || VesperiaConfig.SCAFFOLD_VISUAL) {
            builderHelperManager.update();
        }

        if (VesperiaConfig.DYNAMIC_LIGHTS) {
            dynamicLightsManager.tick();
        }

        if (VesperiaConfig.VIEWMODEL || VesperiaConfig.SWING_ANIMATION) {
            viewModelManager.tick();
        }

        if (VesperiaConfig.MLG_INDICATOR) {
            mlgIndicatorManager.tick();
        }

        if (VesperiaConfig.PARKOUR_HELPER) {
            parkourHelperManager.tick();
        }

        if (VesperiaConfig.SOUND_VISUALIZER) {
            soundVisualizerManager.tick();
        }

        if (VesperiaConfig.SOUND_INDICATORS) {
            soundIndicatorsManager.tick();
        }

        effectRenderer.tick();
    }

    public static VesperiaHubClient getInstance() {
        return INSTANCE;
    }

    public HitEffectManager getHitEffectManager() { return hitEffectManager; }
    public DamageNumberManager getDamageNumberManager() { return damageNumberManager; }
    public TargetHUDManager getTargetHUDManager() { return targetHUDManager; }
    public ComboCounterManager getComboCounterManager() { return comboCounterManager; }
    public CrosshairManager getCrosshairManager() { return crosshairManager; }
    public HUDManager getHUDManager() { return hudManager; }
    public QOLManager getQOLManager() { return qolManager; }
    public EffectRenderer getEffectRenderer() { return effectRenderer; }
    public TrajectoryManager getTrajectoryManager() { return trajectoryManager; }
    public BuilderHelperManager getBuilderHelperManager() { return builderHelperManager; }
    public AutoTotemIndicator getAutoTotemIndicator() { return autoTotemIndicator; }
    public StreamEffectsManager getStreamEffectsManager() { return streamEffectsManager; }
    public ViewModelManager getViewModelManager() { return viewModelManager; }
    public DynamicLightsManager getDynamicLightsManager() { return dynamicLightsManager; }
    public EnhancedTooltipsManager getEnhancedTooltipsManager() { return enhancedTooltipsManager; }
    public MLGIndicatorManager getMlgIndicatorManager() { return mlgIndicatorManager; }
    public ParkourHelperManager getParkourHelperManager() { return parkourHelperManager; }
    public SoundVisualizerManager getSoundVisualizerManager() { return soundVisualizerManager; }
    public SoundIndicatorsManager getSoundIndicatorsManager() { return soundIndicatorsManager; }
    public ClientEventHandler getEventHandler() { return eventHandler; }

    public KeyBinding getZoomKey() { return zoomKey; }
    public KeyBinding getConfigKey() { return configKey; }

    public MinecraftClient getClient() { return MinecraftClient.getInstance(); }
}
