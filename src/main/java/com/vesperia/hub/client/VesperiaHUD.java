package com.vesperia.hub.client;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class VesperiaHUD {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private long lastFpsUpdate = 0;
    private long fps = 60;
    private int combo = 0;
    private long lastHitTime = 0;

    public void render(DrawContext context) {
        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();

        if (VesperiaConfig.FPS_COUNTER) renderFps(context, sw);
        if (VesperiaConfig.PING_DISPLAY) renderPing(context, sw);
        if (VesperiaConfig.COORDS_DISPLAY) renderCoords(context);
        if (VesperiaConfig.DIRECTION_HUD) renderDirection(context, sw);
        if (VesperiaConfig.POTION_HUD) renderPotions(context);
        if (VesperiaConfig.ARMOR_HUD) renderArmor(context, sh);
        if (VesperiaConfig.KEYSTROKES) renderKeystrokes(context, sw, sh);
        if (VesperiaConfig.CPS_COUNTER) renderCps(context, sw);
        if (VesperiaConfig.TOTEM_COUNTER) renderTotem(context, sw, sh);
        if (VesperiaConfig.TARGET_HUD) renderTargetHud(context, sw, sh);
        if (VesperiaConfig.CUSTOM_CROSSHAIR) renderCrosshair(context, sw, sh);
        if (VesperiaConfig.COMBO_COUNTER) renderCombo(context, sw, sh);
    }

    private void renderFps(DrawContext context, int sw) {
        long now = System.currentTimeMillis();
        if (now - lastFpsUpdate > 500) {
            fps = client.getCurrentFps();
            lastFpsUpdate = now;
        }
        context.drawTextWithShadow(client.textRenderer, "FPS: " + fps, sw - 65, 5, 0xFFFFFF);
    }

    private void renderPing(DrawContext context, int sw) {
        if (client.getNetworkHandler() == null) return;
        var entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if (entry != null) {
            int ping = entry.getLatency();
            int color = ping < 50 ? 0x55FF55 : ping < 100 ? 0xFFFF55 : 0xFF5555;
            context.drawTextWithShadow(client.textRenderer, "Ping: " + ping + "ms", sw - 85, 20, color);
        }
    }

    private void renderCoords(DrawContext context) {
        BlockPos pos = client.player.getBlockPos();
        String text = String.format("X: %d  Y: %d  Z: %d", pos.getX(), pos.getY(), pos.getZ());
        context.drawTextWithShadow(client.textRenderer, text, 5, 5, 0xFFFFFF);
    }

    private void renderDirection(DrawContext context, int sw) {
        String[] dirs = {"N", "E", "S", "W"};
        Direction dir = client.player.getHorizontalFacing();
        int idx = switch (dir) {
            case NORTH -> 0;
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
            default -> 0;
        };
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
            dirs[idx], sw / 2, 5, 0xFFFFFF);
    }

    private void renderPotions(DrawContext context) {
        int y = 25;
        for (StatusEffectInstance effect : client.player.getStatusEffects()) {
            String name = effect.getEffectType().toString();
            int amp = effect.getAmplifier() + 1;
            int dur = effect.getDuration() / 20;
            context.drawTextWithShadow(client.textRenderer, name + " " + amp + " (" + dur + "s)", 5, y, 0xFFFFFF);
            y += 12;
        }
    }

    private void renderArmor(DrawContext context, int sh) {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            if (!client.player.getInventory().getStack(39 - i).isEmpty()) total += 5;
        }
        context.drawTextWithShadow(client.textRenderer, "Armor: " + total, 5, sh - 50, 0xAAAAAA);
    }

    private void renderKeystrokes(DrawContext context, int sw, int sh) {
        int cx = sw / 2;
        int cy = sh - 50;
        int on = 0xFF5555FF, off = 0x88555555;

        context.fill(cx - 20, cy - 20, cx + 20, cy - 5, client.options.forwardKey.isPressed() ? on : off);
        context.fill(cx - 40, cy, cx - 20, cy + 15, client.options.leftKey.isPressed() ? on : off);
        context.fill(cx - 20, cy, cx + 20, cy + 15, client.options.backKey.isPressed() ? on : off);
        context.fill(cx + 20, cy, cx + 40, cy + 15, client.options.rightKey.isPressed() ? on : off);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "W", cx, cy - 15, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "A", cx - 30, cy + 3, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "S", cx, cy + 3, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "D", cx + 30, cy + 3, 0xFFFFFF);
    }

    private void renderCps(DrawContext context, int sw) {
        context.drawTextWithShadow(client.textRenderer, "CPS: " + VesperiaHubClient.getInstance().getCps(), sw - 65, 35, 0xFFFFFF);
    }

    private void renderTotem(DrawContext context, int sw, int sh) {
        int totems = 0;
        for (int i = 0; i < 36; i++) {
            if (client.player.getInventory().getStack(i).toString().contains("totem")) totems++;
        }
        context.drawTextWithShadow(client.textRenderer, "Totems: " + totems, sw - 85, 50, totems > 0 ? 0x55FF55 : 0xFF5555);
    }

    private void renderTargetHud(DrawContext context, int sw, int sh) {
        var target = client.targetedEntity;
        if (!(target instanceof net.minecraft.entity.LivingEntity entity)) return;

        int x = sw / 2;
        int y = sh / 2 - 60;

        context.fill(x - 70, y - 35, x + 70, y + 35, 0x90000000);

        String name = entity.getName().getString();
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), name, x, y - 25, 0xFFFFFF);

        float hp = entity.getHealth();
        float maxHp = entity.getMaxHealth();
        float barW = 120;
        context.fill(x - barW/2, y - 5, x - barW/2 + (int)(barW * hp / maxHp), y + 5, getHealthColor(hp / maxHp));

        String hpText = String.format("%.1f / %.1f", hp, maxHp);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), hpText, x, y + 10, 0xFFFFFF);
    }

    private void renderCrosshair(DrawContext context, int sw, int sh) {
        int cx = sw / 2;
        int cy = sh / 2;
        int size = (int)(8 * VesperiaConfig.CROSSHAIR_SIZE);
        int thick = VesperiaConfig.DOT_CROSSHAIR ? (int)(4 * VesperiaConfig.CROSSHAIR_SIZE) : 1;

        int color = VesperiaConfig.CROSSHAIR_COLOR;
        if (VesperiaConfig.DYNAMIC_CROSSHAIR && client.targetedEntity != null) {
            color = 0xFFFF5555;
        }

        if (VesperiaConfig.DOT_CROSSHAIR) {
            context.fill(cx - thick, cy - thick, cx + thick, cy + thick, color);
        } else {
            context.fill(cx - size, cy - thick, cx + size, cy + thick, color);
            context.fill(cx - thick, cy - size, cx + thick, cy + size, color);
        }
    }

    private void renderCombo(DrawContext context, int sw, int sh) {
        if (combo >= 2) {
            int color = combo >= 10 ? 0xFFFF5555 : combo >= 5 ? 0xFFFFFF55 : 0xFFFFFFFF;
            context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                combo + "x COMBO", sw - 60, sh / 2, color);
        }

        long now = System.currentTimeMillis();
        if (now - lastHitTime > 1500) combo = 0;
    }

    private int getHealthColor(float pct) {
        return pct > 0.7f ? 0xFF55FF55 : pct > 0.4f ? 0xFFFFFF55 : 0xFFFF5555;
    }

    public void addHit() {
        long now = System.currentTimeMillis();
        if (now - lastHitTime < 800) combo++;
        else combo = 1;
        lastHitTime = now;
    }
}
