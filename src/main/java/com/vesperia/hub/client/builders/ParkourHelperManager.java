package com.vesperia.hub.client.builders;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.BlockPos;

public class ParkourHelperManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private BlockPos lastGroundPos = null;

    public void tick() {
        if (!VesperiaConfig.PARKOUR_HELPER) return;
        if (client.player == null) return;

        BlockPos feet = client.player.getBlockPos();
        BlockPos below = feet.down();

        if (client.world.getBlockState(below).isSolid() && !below.equals(lastGroundPos)) {
            lastGroundPos = below;
        }
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.PARKOUR_HELPER || lastGroundPos == null) return;
        if (client.player == null) return;

        double distance = client.player.getPos().distanceTo(lastGroundPos.toCenterPos());
        
        if (distance > 5 && distance < 15) {
            int sw = client.getWindow().getScaledWidth();
            context.drawCenteredTextWithShadow(client.textRenderer, context.getMatrices(),
                    String.format("%.1f", distance) + "b", sw / 2, 100, 0xFF55FF55);
        }
    }

    public BlockPos getLastGroundPos() { return lastGroundPos; }
}
