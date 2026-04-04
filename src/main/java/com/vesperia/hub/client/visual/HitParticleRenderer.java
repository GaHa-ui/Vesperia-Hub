package com.vesperia.hub.client.visual;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.config.ModConfig;
import com.vesperia.hub.client.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class HitParticleRenderer {
    private final List<HitParticle> particles = new ArrayList<>();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void addHitParticle(Vec3d position, boolean isCritical) {
        if (!ModConfig.ENABLE_HIT_PARTICLES) return;

        int color = isCritical ? ModConfig.CRITICAL_HIT_COLOR : ModConfig.NORMAL_HIT_COLOR;
        float scale = ModConfig.HIT_PARTICLE_SCALE;
        int lifetime = ModConfig.HIT_PARTICLE_LIFETIME;

        for (int i = 0; i < 8; i++) {
            double spread = 0.2;
            Vec3d velocity = new Vec3d(
                    (Math.random() - 0.5) * spread,
                    Math.random() * 0.3,
                    (Math.random() - 0.5) * spread
            );
            particles.add(new HitParticle(position, velocity, color, lifetime, scale));
        }
    }

    public void tick() {
        Iterator<HitParticle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            HitParticle particle = iterator.next();
            particle.update();
            if (particle.isDead()) {
                iterator.remove();
            }
        }
    }

    public void render(MatrixStack matrixStack, Matrix4f matrix4f) {
        if (particles.isEmpty()) return;

        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();

        VertexConsumer buffer = RenderUtils.getEntityVertexConsumer(client);

        for (HitParticle particle : particles) {
            Vec3d pos = particle.getPosition().subtract(cameraPos);

            float size = 0.1f * particle.getScale();
            float alpha = particle.getAlpha();
            int color = particle.getColor();

            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;
            int a = (int) (alpha * 255);

            RenderUtils.drawBillboardQuad(matrixStack, matrix4f, buffer,
                    (float) pos.x, (float) pos.y, (float) pos.z,
                    size, r, g, b, a);
        }
    }

    public List<HitParticle> getParticles() {
        return particles;
    }
}
