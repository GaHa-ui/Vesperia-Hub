package com.vesperia.hub.client.hud;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TargetHUDManager {
    private LivingEntity target;
    private double lastDistance = 0;

    public void updateTarget(LivingEntity entity) {
        this.target = entity;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            this.lastDistance = client.player.getPos().distanceTo(entity.getPos());
        }
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.TARGET_HUD || target == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();
        int x = sw / 2;
        int y = sh / 2 - 60;
        float scale = VesperiaConfig.TARGET_HUD_SCALE;

        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        float absorption = target.getAbsorptionAmount();

        String name = target.getName().getString();
        String healthText = String.format("%.1f / %.1f", health, maxHealth);
        if (absorption > 0) healthText += String.format(" (+%.1f)", absorption);
        String distText = String.format("%.1f м", lastDistance);

        int bw = 140, bh = 70;
        int bx = x - bw / 2;
        int by = y - bh / 2;

        context.fill(bx, by, bx + bw, by + bh, 0x90000000);
        context.drawBorder(bx, by, bw, bh, 0xFF55AAFF);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                name, x, by + 6, 0xFFFFFF);

        int hbX = bx + 10, hbY = by + 22, hbW = bw - 20, hbH = 10;
        context.fill(hbX, hbY, hbX + hbW, hbY + hbH, 0xFF333333);
        float hp = Math.min(1.0f, health / maxHealth);
        int hbw = (int) (hbW * hp);
        int hbc = getHealthColor(hp);
        context.fill(hbX, hbY, hbX + hbw, hbY + hbH, hbc);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                healthText, x, hbY + hbH + 3, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                distText, x, hbY + hbH + 15, 0xFFAAAAAA);
    }

    private int getHealthColor(float p) {
        if (p > 0.7f) return 0xFF00FF00;
        if (p > 0.4f) return 0xFFFFFF00;
        return 0xFFFF0000;
    }

    public boolean hasTarget() { return target != null; }
    public LivingEntity getTarget() { return target; }
}
