package com.vesperia.hub.client.builders;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class MLGIndicatorManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean isMLGActive = false;
    private int mlgTimer = 0;
    private long lastFallTime = 0;

    public void onFall() {
        if (!VesperiaConfig.MLG_INDICATOR) return;
        lastFallTime = System.currentTimeMillis();
        isMLGActive = true;
        mlgTimer = 60;
    }

    public void tick() {
        if (isMLGActive && mlgTimer > 0) {
            mlgTimer--;
            if (mlgTimer <= 0) {
                isMLGActive = false;
            }
        }
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.MLG_INDICATOR || !isMLGActive) return;

        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();

        String text = "MLG!";
        int color = 0xFFFF5555;

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                text, sw / 2, sh / 2 - 50, color);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                String.valueOf(mlgTimer / 20.0).substring(0, 3) + "s", sw / 2, sh / 2 - 35, 0xFFFFFF);
    }
}
