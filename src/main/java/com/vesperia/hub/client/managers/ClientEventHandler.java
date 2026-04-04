package com.vesperia.hub.client.managers;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.effects.*;
import com.vesperia.hub.client.hud.*;
import com.vesperia.hub.client.crosshair.*;
import com.vesperia.hub.client.pvp.*;
import com.vesperia.hub.client.qol.*;
import com.vesperia.hub.client.builders.*;
import com.vesperia.hub.client.stream.*;
import com.vesperia.hub.config.VesperiaConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ClientEventHandler {
    private final VesperiaHubClient mod;
    private long ticks = 0;

    public ClientEventHandler(VesperiaHubClient mod) {
        this.mod = mod;
    }

    public void onClientTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        ticks++;

        if (VesperiaConfig.HIT_PARTICLES || VesperiaConfig.HIT_COLOR_FLASH || VesperiaConfig.HIT_BUBBLES) {
            mod.getHitEffectManager().tick();
        }

        if (VesperiaConfig.DAMAGE_NUMBERS) {
            mod.getDamageNumberManager().tick();
        }

        if (VesperiaConfig.TARGET_HUD) {
            if (client.targetedEntity instanceof LivingEntity living) {
                mod.getTargetHUDManager().updateTarget(living);
            }
        }

        if (VesperiaConfig.COMBO_COUNTER) {
            mod.getComboCounterManager().tick(client);
        }

        if (VesperiaConfig.QOL_MANAGER) {
            mod.getQOLManager().tick(client);
        }

        if (VesperiaConfig.JUMP_CIRCLE && client.options.jumpKey.wasPressed()) {
            mod.getEffectRenderer().spawnJumpCircle(client.player);
        }

        if (VesperiaConfig.SCREEN_SHAKE) {
            mod.getEffectRenderer().tickScreenShake();
        }

        if (VesperiaConfig.KILL_EFFECT && ticks % 20 == 0) {
            mod.getEffectRenderer().tickKillEffects();
        }
    }
}
