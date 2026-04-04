package com.vesperia.hub.client.visual;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class TargetHUD {
    private LivingEntity target;
    private Vec3d lastTargetPos;
    private long lastUpdate;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void updateTarget(LivingEntity entity, PlayerEntity player) {
        this.target = entity;
        this.lastTargetPos = entity.getPos();
        this.lastUpdate = System.currentTimeMillis();
    }

    public void render(DrawContext context) {
        if (!ModConfig.ENABLE_TARGET_HUD || target == null || client.player == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int x = screenWidth / 2;
        int y = screenHeight / 2 - 50;
        float scale = ModConfig.TARGET_HUD_SCALE;

        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        float absorption = target.getAbsorptionAmount();

        String name = target.getName().getString();
        String healthText = String.format("%.1f / %.1f", health, maxHealth);
        if (absorption > 0) {
            healthText += String.format(" (+%.1f)", absorption);
        }

        double distance = client.player.getPos().distanceTo(target.getPos());
        String distanceText = String.format("%.1f blocks", distance);

        int armor = getTotalArmor(target);
        String armorText = armor + " armor";

        int bgColor = 0x80000000;
        int textColor = 0xFFFFFFFF;
        int healthBarColor = getHealthBarColor(health / maxHealth);
        int accentColor = 0xFF55AAFF;

        int boxWidth = 120;
        int boxHeight = 60;
        int boxX = x - boxWidth / 2;
        int boxY = y - boxHeight / 2;

        context.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, bgColor);

        context.drawBorder(boxX, boxY, boxWidth, boxHeight, accentColor);

        int textX = x;
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                name, textX, boxY + 5, textColor);

        int healthBarWidth = boxWidth - 20;
        int healthBarHeight = 8;
        int healthBarX = boxX + 10;
        int healthBarY = boxY + 20;

        context.fill(healthBarX, healthBarY, healthBarX + healthBarWidth, healthBarY + healthBarHeight, 0xFF333333);

        float healthPercent = Math.min(1.0f, health / maxHealth);
        int healthFillWidth = (int) (healthBarWidth * healthPercent);

        int healthR = (healthBarColor >> 16) & 0xFF;
        int healthG = (healthBarColor >> 8) & 0xFF;
        int healthB = healthBarColor & 0xFF;
        context.fill(healthBarX, healthBarY, healthBarX + healthFillWidth, healthBarY + healthBarHeight,
                (0xFF << 24) | (healthR << 16) | (healthG << 8) | healthB);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                healthText, textX, healthBarY + healthBarHeight + 3, textColor);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                distanceText, textX, healthBarY + healthBarHeight + 14, 0xFFAAAAAA);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                armorText, textX, healthBarY + healthBarHeight + 25, 0xFF8888FF);
    }

    private int getHealthBarColor(float percentage) {
        if (percentage > 0.7f) return 0x00FF00;
        if (percentage > 0.4f) return 0xFFFF00;
        return 0xFF0000;
    }

    private int getTotalArmor(LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            return player.getInventory().getArmorStack(0, 3).isEmpty() ? 0 :
                    player.getInventory().getArmorStack(0, 0).isEmpty() ? 0 :
                            player.getInventory().getArmorStack(0, 1).isEmpty() ? 0 :
                                    player.getInventory().getArmorStack(0, 2).isEmpty() ? 0 :
                                            player.getInventory().getArmorStack(0, 3).isEmpty() ? 0 : 20;
        }
        return 0;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public float getTargetHealth() {
        return target != null ? target.getHealth() : 0;
    }

    public String getTargetName() {
        return target != null ? target.getName().getString() : "";
    }
}
