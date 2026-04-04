package com.vesperia.hub.client.hud;

import com.vesperia.hub.config.VesperiaConfig;
import com.vesperia.hub.VesperiaHubClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;

public class HUDManager {
    private long fps = 0;
    private long lastFpsUpdate = 0;

    public void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();

        if (VesperiaConfig.FPS_COUNTER) renderFps(context, sw, sh);
        if (VesperiaConfig.PING_DISPLAY) renderPing(context, sw, sh);
        if (VesperiaConfig.COORDS_DISPLAY) renderCoords(context, sw, sh);
        if (VesperiaConfig.DIRECTION_HUD) renderDirection(context, sw, sh);
        if (VesperiaConfig.POTION_HUD) renderPotions(context, sw, sh);
        if (VesperiaConfig.ARMOR_HUD) renderArmor(context, sh);
        if (VesperiaConfig.KEYSTROKES) renderKeystrokes(context, sw, sh);
        if (VesperiaConfig.CPS_COUNTER) renderCps(context, sw, sh);
        if (VesperiaConfig.REACH_DISPLAY) renderReach(context, sw, sh);
        if (VesperiaConfig.TOTEM_COUNTER) renderTotemCounter(context, sw, sh);
        if (VesperiaConfig.SCOREBOARD_CUSTOM) renderScoreboard(context);
    }

    private void renderFps(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        long now = System.currentTimeMillis();
        if (now - lastFpsUpdate > 500) {
            fps = client.getCurrentFps();
            lastFpsUpdate = now;
        }
        context.drawTextWithShadow(client.textRenderer, "FPS: " + fps, sw - 60, 5, 0xFFFFFF);
    }

    private void renderPing(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() == null || client.player == null) return;

        PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if (entry != null) {
            int ping = entry.getLatency();
            int color = ping < 50 ? 0xFF00FF00 : ping < 100 ? 0xFFFFFF00 : 0xFFFF0000;
            context.drawTextWithShadow(client.textRenderer, "Пинг: " + ping + "ms", sw - 80, 20, color);
        }
    }

    private void renderCoords(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        BlockPos pos = client.player.getBlockPos();
        String coords = String.format("X: %d  Y: %d  Z: %d", pos.getX(), pos.getY(), pos.getZ());
        context.drawTextWithShadow(client.textRenderer, coords, 5, 5, 0xFFFFFF);
    }

    private void renderDirection(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Direction dir = Direction.fromHorizontalAngle(client.player.getYaw());
        String[] names = {"С", "В", "Ю", "З"};
        String[] full = {"Север", "Восток", "Юг", "Запад"};
        int idx = dir.getHorizontal();
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                names[idx], sw / 2, 5, 0xFFFFFF);
    }

    private void renderPotions(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Collection<StatusEffectInstance> effects = client.player.getStatusEffects();
        int y = 25;
        for (StatusEffectInstance effect : effects) {
            String name = effect.getEffectType().getName().getString();
            int amplifier = effect.getAmplifier() + 1;
            int duration = effect.getDuration() / 20;
            String text = name + " " + amplifier + " (" + duration + "s)";
            context.drawTextWithShadow(client.textRenderer, text, 5, y, 0xFFFFFF);
            y += 12;
        }
    }

    private void renderArmor(DrawContext context, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int totalArmor = 0;
        for (int i = 0; i < 4; i++) {
            if (!client.player.getInventory().getStack(39 - i).isEmpty()) {
                totalArmor += 5;
            }
        }
        context.drawTextWithShadow(client.textRenderer, "🛡 " + totalArmor, 5, sh - 50, 0xAAAAAA);
    }

    private void renderKeystrokes(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        int cx = sw / 2;
        int cy = sh - 50;

        int pressed = 0xFF5555FF;
        int normal = 0x88555555;

        boolean w = client.options.forwardKey.isPressed();
        boolean a = client.options.leftKey.isPressed();
        boolean s = client.options.backKey.isPressed();
        boolean d = client.options.rightKey.isPressed();

        context.fill(cx - 20, cy - 20, cx + 20, cy - 5, w ? pressed : normal);
        context.fill(cx - 40, cy, cx - 20, cy + 15, a ? pressed : normal);
        context.fill(cx - 20, cy, cx + 20, cy + 15, s ? pressed : normal);
        context.fill(cx + 20, cy, cx + 40, cy + 15, d ? pressed : normal);

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "W", cx, cy - 15, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "A", cx - 30, cy + 3, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "S", cx, cy + 3, 0xFFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(), "D", cx + 30, cy + 3, 0xFFFFFF);
    }

    private void renderCps(DrawContext context, int sw, int sh) {
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "CPS: 0", sw - 60, 35, 0xFFFFFF);
    }

    private void renderReach(DrawContext context, int sw, int sh) {
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Reach: 3.0", sw - 80, 50, 0xFFFFFF);
    }

    private void renderTotemCounter(DrawContext context, int sw, int sh) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int totems = 0;
        for (int i = 0; i < 36; i++) {
            if (client.player.getInventory().getStack(i).getItem().toString().contains("totem")) {
                totems++;
            }
        }
        context.drawTextWithShadow(client.textRenderer, "Тотемы: " + totems, sw - 80, 65, totems > 0 ? 0xFF55FF55 : 0xFFFF5555);
    }

    private void renderScoreboard(DrawContext context) {
    }
}
