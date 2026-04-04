package com.vesperia.hub.client.event;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.visual.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.joml.Matrix4f;

import java.util.Iterator;

public class ClientEventHandler {
    private final VesperiaHubClient mod;
    private long lastTargetUpdate = 0;

    public ClientEventHandler(VesperiaHubClient mod) {
        this.mod = mod;
        setupWorldRender();
    }

    private void setupWorldRender() {
        WorldRenderEvents.END.register(context -> {
            if (mod.getTrajectoryRenderer() != null && mod.getTrajectoryRenderer().isActive()) {
                Matrix4f matrix = context.matrixStack().peek().getPositionMatrix();
                mod.getTrajectoryRenderer().render(context.matrixStack(), matrix);
            }
        });
    }

    public void onClientTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        HitParticleRenderer hitRenderer = mod.getHitParticleRenderer();
        if (hitRenderer != null) {
            hitRenderer.tick();
        }

        DamageNumberRenderer damageRenderer = mod.getDamageNumberRenderer();
        if (damageRenderer != null) {
            damageRenderer.tick();
        }

        TargetHUD targetHUD = mod.getTargetHUD();
        if (targetHUD != null && client.targetedEntity instanceof LivingEntity livingTarget) {
            targetHUD.updateTarget(livingTarget, client.player);
        }

        CriticalEffectRenderer criticalRenderer = mod.getCriticalEffectRenderer();
        if (criticalRenderer != null) {
            criticalRenderer.tick();
        }
    }
}
