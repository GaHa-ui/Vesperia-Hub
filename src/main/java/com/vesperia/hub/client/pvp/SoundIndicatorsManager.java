package com.vesperia.hub.client.pvp;

import com.vesperia.hub.config.VesperiaConfig;
import com.vesperia.hub.VesperiaHubClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;

public class SoundIndicatorsManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private Direction lastSoundDirection = null;
    private int indicatorTicks = 0;

    public void onSound(float x, float y, float z) {
        if (!VesperiaConfig.SOUND_INDICATORS) return;
        if (client.player == null) return;

        double px = client.player.getX();
        double py = client.player.getY();
        double pz = client.player.getZ();

        double dx = x - px;
        double dz = z - pz;

        if (Math.abs(dx) > Math.abs(dz)) {
            lastSoundDirection = dx > 0 ? Direction.WEST : Direction.EAST;
        } else {
            lastSoundDirection = dz > 0 ? Direction.NORTH : Direction.SOUTH;
        }

        indicatorTicks = 30;
    }

    public void tick() {
        if (indicatorTicks > 0) {
            indicatorTicks--;
        }
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.SOUND_INDICATORS || indicatorTicks <= 0 || lastSoundDirection == null) return;

        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();
        int arrowSize = 20;

        int arrowX = sw / 2;
        int arrowY = sh / 2;

        int offsetX = 0, offsetY = 0;
        switch (lastSoundDirection) {
            case NORTH -> offsetY = -60;
            case SOUTH -> offsetY = 60;
            case EAST -> offsetX = 60;
            case WEST -> offsetX = -60;
            default -> {}
        }

        String arrow = switch (lastSoundDirection) {
            case NORTH -> "↑";
            case SOUTH -> "↓";
            case EAST -> "→";
            case WEST -> "←";
            default -> "";
        };

        context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                arrow, arrowX + offsetX, arrowY + offsetY, 0xFFFF5555);
    }
}
