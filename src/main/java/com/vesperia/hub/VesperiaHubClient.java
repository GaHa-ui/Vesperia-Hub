package com.vesperia.hub;

import com.vesperia.hub.client.event.ClientEventHandler;
import com.vesperia.hub.client.visual.*;
import com.vesperia.hub.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VesperiaHubClient implements ClientModInitializer {
    public static final String MOD_ID = "vesperia-hub";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static VesperiaHubClient INSTANCE;
    private HitParticleRenderer hitParticleRenderer;
    private DamageNumberRenderer damageNumberRenderer;
    private TargetHUD targetHUD;
    private TrajectoryRenderer trajectoryRenderer;
    private CriticalEffectRenderer criticalEffectRenderer;
    private ClientEventHandler eventHandler;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("Vesperia Hub initializing...");

        ModConfig.load();

        hitParticleRenderer = new HitParticleRenderer();
        damageNumberRenderer = new DamageNumberRenderer();
        targetHUD = new TargetHUD();
        trajectoryRenderer = new TrajectoryRenderer();
        criticalEffectRenderer = new CriticalEffectRenderer();
        eventHandler = new ClientEventHandler(this);

        ClientTickEvents.END_CLIENT_TICK.register(eventHandler::onClientTick);

        LOGGER.info("Vesperia Hub initialized!");
    }

    public static VesperiaHubClient getInstance() {
        return INSTANCE;
    }

    public HitParticleRenderer getHitParticleRenderer() {
        return hitParticleRenderer;
    }

    public DamageNumberRenderer getDamageNumberRenderer() {
        return damageNumberRenderer;
    }

    public TargetHUD getTargetHUD() {
        return targetHUD;
    }

    public TrajectoryRenderer getTrajectoryRenderer() {
        return trajectoryRenderer;
    }

    public CriticalEffectRenderer getCriticalEffectRenderer() {
        return criticalEffectRenderer;
    }

    public ClientEventHandler getEventHandler() {
        return eventHandler;
    }
}
