package com.vesperia.hub.client.stream;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class StreamEffectsManager {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void render(DrawContext context) {
        if (VesperiaConfig.CHUNK_BORDERS) {
            renderChunkBorders(context);
        }
    }

    private void renderChunkBorders(DrawContext context) {
        if (client.player == null) return;

        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();

        int chunkX = (client.player.getBlockX() >> 4) << 4;
        int chunkZ = (client.player.getBlockZ() >> 4) << 4;

        String text = "Чанк: " + (client.player.getBlockX() >> 4) + ", " + (client.player.getBlockZ() >> 4);
        context.drawTextWithShadow(client.textRenderer, text, sw - 100, sh - 20, 0x888888);
    }

    public void triggerKillFlash() {
    }
}
