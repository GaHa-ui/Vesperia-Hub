package com.vesperia.hub.mixin;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.visual.DamageNumberRenderer;
import com.vesperia.hub.client.visual.HitParticleRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void onAttack(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (player.world.isClient && target instanceof LivingEntity livingTarget) {
            VesperiaHubClient.getInstance().getHitParticleRenderer()
                    .addHitParticle(target.getPos(), false);
        }
    }

    @Inject(method = "attackEntityWithRightClick", at = @At("HEAD"))
    private void onRightClickAttack(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (player.world.isClient && target instanceof LivingEntity livingTarget) {
            VesperiaHubClient.getInstance().getHitParticleRenderer()
                    .addHitParticle(target.getPos(), false);
        }
    }
}
