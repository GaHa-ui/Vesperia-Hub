package com.vesperia.hub.client.hud;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ComboCounterManager {
    private int combo = 0;
    private long lastHitTime = 0;
    private float scale = 1.0f;

    public void addHit() {
        long now = System.currentTimeMillis();
        if (now - lastHitTime < 800) {
            combo++;
        } else {
            combo = 1;
        }
        lastHitTime = now;
        scale = 1.5f;
    }

    public void tick(MinecraftClient client) {
        if (combo > 0 && System.currentTimeMillis() - lastHitTime > 2000) {
            combo = 0;
        }
        scale = Math.max(1.0f, scale - 0.02f);
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.COMBO_COUNTER || combo < 2) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();
        int x = sw - 60;
        int y = sh / 2;

        int color = combo >= 10 ? 0xFFFF5555 : combo >= 5 ? 0xFFFFFF55 : 0xFFFFFFFF;
        String text = combo + "x";

        int size = (int) (20 * scale);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                text, x, y, color);

        if (combo >= 5) {
            context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                    "COMBO!", x, y + 15, 0xFFFF5555);
        }
    }

    public int getCombo() { return combo; }
}
