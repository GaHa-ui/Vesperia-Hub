package com.vesperia.hub.client.effects;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

public class DynamicLightsManager {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void tick() {
        if (!VesperiaConfig.DYNAMIC_LIGHTS) return;
        if (client.player == null || client.world == null) return;

        ClientPlayerEntity player = client.player;
        
        var mainHand = player.getMainHandStack();
        var offHand = player.getOffHandStack();

        boolean hasTorch = false;
        if (mainHand.getItem().toString().contains("torch") || 
            offHand.getItem().toString().contains("torch")) {
            hasTorch = true;
        }

        if (hasTorch) {
            BlockPos pos = player.getBlockPos();
            client.world.setLightning(LightType.BLOCK, 15);
        }
    }

    public boolean isHoldingLightSource() {
        if (!VesperiaConfig.DYNAMIC_LIGHTS) return false;
        if (client.player == null) return false;

        var mainHand = client.player.getMainHandStack();
        var offHand = client.player.getOffHandStack();

        return mainHand.getItem().toString().contains("torch") ||
               offHand.getItem().toString().contains("torch") ||
               mainHand.getItem().toString().contains("lantern") ||
               offHand.getItem().toString().contains("lantern") ||
               mainHand.getItem().toString().contains("candle") ||
               offHand.getItem().toString().contains("candle");
    }
}
