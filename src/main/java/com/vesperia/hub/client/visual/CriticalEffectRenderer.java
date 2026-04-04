package com.vesperia.hub.client.visual;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CriticalEffectRenderer {
    private final List<CriticalEffect> effects = new ArrayList<>();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void addCriticalEffect(Vec3d position) {
        if (!ModConfig.ENABLE_CRITICAL_EFFECTS) return;

        effects.add(new CriticalEffect(position, 20));
    }

    public void tick() {
        Iterator<CriticalEffect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            CriticalEffect effect = iterator.next();
            effect.update();
            if (effect.isDead()) {
                iterator.remove();
            }
        }
    }

    public void render(MatrixStack matrixStack, Matrix4f matrix4f) {
        if (effects.isEmpty()) return;

        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();

        VertexConsumer buffer = client.getBufferBuilder().getBuffer();

        for (CriticalEffect effect : effects) {
            Vec3d pos = effect.getPosition().subtract(cameraPos);
            float alpha = effect.getAlpha();
            float size = effect.getSize();

            int color = (int) (alpha * 255) << 24 | 0x00FFFF;

            buffer.vertex(matrix4f, (float) pos.x - size, (float) pos.y, (float) pos.z).color(color);
            buffer.vertex(matrix4f, (float) pos.x + size, (float) pos.y, (float) pos.z).color(color);
            buffer.vertex(matrix4f, (float) pos.x, (float) pos.y + size * 2, (float) pos.z).color(color);
        }
    }

    public List<CriticalEffect> getEffects() {
        return effects;
    }

    public static class CriticalEffect {
        private final Vec3d position;
        private final int maxLifetime;
        private int currentLifetime;

        public CriticalEffect(Vec3d position, int lifetime) {
            this.position = position;
            this.maxLifetime = lifetime;
            this.currentLifetime = lifetime;
        }

        public void update() {
            currentLifetime--;
        }

        public boolean isDead() {
            return currentLifetime <= 0;
        }

        public float getAlpha() {
            return (float) currentLifetime / maxLifetime;
        }

        public float getSize() {
            return (1.0f - getAlpha()) * 0.5f + 0.2f;
        }

        public Vec3d getPosition() {
            return position;
        }
    }
}
