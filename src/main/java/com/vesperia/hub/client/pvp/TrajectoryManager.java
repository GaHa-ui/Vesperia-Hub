package com.vesperia.hub.client.pvp;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryManager {
    private final List<Vec3d> points = new ArrayList<>();
    private boolean isActive = false;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void update() {
        points.clear();
        if (!VesperiaConfig.TRAJECTORY_PREDICTION) return;

        if (client.player == null || client.world == null) return;

        Camera camera = client.gameRenderer.getCamera();
        Vec3d start = camera.getPos();
        Vec3d look = Vec3d.fromPolar(
                client.gameRenderer.getCamera().getPitch(),
                client.gameRenderer.getCamera().getYaw()
        ).multiply(100);

        Vec3d velocity = look.normalize().multiply(1.5);
        Vec3d pos = start;
        Vec3d gravity = new Vec3d(0, -0.05, 0);

        for (int i = 0; i < 40; i++) {
            points.add(pos);
            pos = pos.add(velocity);
            if (pos.y < 0) break;
            velocity = velocity.add(gravity);
        }

        isActive = true;
    }

    public void render(MatrixStack matrices, Matrix4f matrix) {
        if (!isActive || points.size() < 2) return;

        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        VertexConsumer buffer = client.getBufferBuilder().getBuffer();

        int color = 0x55FFFFFF;

        for (int i = 0; i < points.size() - 1; i++) {
            Vec3d p1 = points.get(i).subtract(cameraPos);
            Vec3d p2 = points.get(i + 1).subtract(cameraPos);

            buffer.vertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z).color(color);
            buffer.vertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z).color(color);
        }
    }

    public boolean isActive() { return isActive; }
    public List<Vec3d> getPoints() { return points; }
}
