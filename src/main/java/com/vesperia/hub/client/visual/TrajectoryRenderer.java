package com.vesperia.hub.client.visual;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryRenderer {
    private List<Vec3d> trajectoryPoints = new ArrayList<>();
    private boolean isActive = false;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void updateTrajectory() {
        trajectoryPoints.clear();

        if (client.player == null || client.world == null) return;

        World world = client.world;
        Camera camera = client.gameRenderer.getCamera();

        Vec3d start = camera.getPos();
        Vec3d direction = Vec3d.fromPolar(
                client.gameRenderer.getCamera().getPitch(),
                client.gameRenderer.getCamera().getYaw()
        ).multiply(100);

        HitResult hitResult = ProjectileUtil.raycast(
                client.player, start, start.add(direction),
                new Box(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5),
                entity -> entity instanceof PersistentProjectileEntity,
                100, 0
        );

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            Vec3d end = hitResult.getPos();
            simulateTrajectory(start, end);
            isActive = true;
        } else {
            isActive = false;
        }
    }

    private void simulateTrajectory(Vec3d start, Vec3d end) {
        trajectoryPoints.clear();

        Vec3d velocity = end.subtract(start).normalize().multiply(1.5);
        Vec3d pos = start;
        Vec3d gravity = new Vec3d(0, -0.05, 0);

        int segments = 40;

        for (int i = 0; i < segments; i++) {
            trajectoryPoints.add(pos);

            pos = pos.add(velocity);

            if (pos.y < 0) {
                trajectoryPoints.add(pos);
                break;
            }

            velocity = velocity.add(gravity);
        }
    }

    public void render(MatrixStack matrixStack, Matrix4f matrix4f) {
        if (!isActive || trajectoryPoints.isEmpty()) return;

        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();

        VertexConsumer buffer = client.getBufferBuilder().getBuffer();

        int color = 0x88FFFFFF;

        for (int i = 0; i < trajectoryPoints.size() - 1; i++) {
            Vec3d p1 = trajectoryPoints.get(i).subtract(cameraPos);
            Vec3d p2 = trajectoryPoints.get(i + 1).subtract(cameraPos);

            buffer.vertex(matrix4f, (float) p1.x, (float) p1.y, (float) p1.z).color(color);
            buffer.vertex(matrix4f, (float) p2.x, (float) p2.y, (float) p2.z).color(color);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Vec3d> getTrajectoryPoints() {
        return trajectoryPoints;
    }
}
