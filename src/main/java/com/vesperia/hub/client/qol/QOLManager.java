package com.vesperia.hub.client.qol;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.LightType;

public class QOLManager {
    private boolean wasZooming = false;
    private float originalFov = 1.0f;

    public void tick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        if (VesperiaConfig.FULLBRIGHT) {
            client.world.setLightning(LightType.SKY, 15);
            client.world.setLightning(LightType.BLOCK, 15);
        }

        if (VesperiaConfig.ZOOM) {
            boolean zoomPressed = VesperiaHubClient.getInstance().getZoomKey().wasPressed();
            if (zoomPressed) {
                wasZooming = !wasZooming;
                if (wasZooming) {
                    originalFov = client.options.getFov().getValue().floatValue();
                    client.options.getFov().setValue(originalFov * VesperiaConfig.ZOOM_LEVEL);
                } else {
                    client.options.getFov().setValue(originalFov);
                }
            }
        }

        if (VesperiaConfig.DAYTIME_OVERRIDE) {
            client.world.setTimeOfDay(VesperiaConfig.DAYTIME_HOUR * 1000L);
        }

        if (VesperiaConfig.CLEAR_WEATHER) {
            client.world.setRainGradient(0);
        }
    }

    public boolean isZooming() { return wasZooming; }
}
