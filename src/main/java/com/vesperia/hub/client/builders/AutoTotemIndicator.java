package com.vesperia.hub.client.builders;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class AutoTotemIndicator {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void render(DrawContext context) {
        if (!VesperiaConfig.AUTO_TOTEM_INDICATOR) return;
        if (client.player == null) return;

        boolean hasTotem = false;
        var offhand = client.player.getInventory().getStack(40);
        if (offhand.getItem().toString().contains("totem")) {
            hasTotem = true;
        }

        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();

        int x = sw / 2 - 10;
        int y = sh - 60;

        if (hasTotem) {
            context.drawTextWithShadow(client.textRenderer, "✦", x, y, 0xFF55FF55);
        } else {
            context.drawTextWithShadow(client.textRenderer, "✦", x, y, 0xFFFF5555);
        }
    }
}
