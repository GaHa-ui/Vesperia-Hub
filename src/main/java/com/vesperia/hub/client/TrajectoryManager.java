package com.vesperia.hub.client;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class TrajectoryManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final List<Vec3d> points = new ArrayList<>();

    public void update() {
        points.clear();
        if (!VesperiaConfig.TRAJECTORY_PREDICTION) return;
        if (client.player == null) return;

        Vec3d start = client.player.getEyePos();
        float yaw = client.player.getYaw();
        float pitch = client.player.getPitch();
        
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);
        
        double dirX = -Math.sin(yawRad) * Math.cos(pitchRad);
        double dirY = -Math.sin(pitchRad);
        double dirZ = Math.cos(yawRad) * Math.cos(pitchRad);
        
        Vec3d vel = new Vec3d(dirX * 1.5, dirY * 1.5, dirZ * 1.5);
        Vec3d pos = start;
        Vec3d gravity = new Vec3d(0, -0.05, 0);
        
        for (int i = 0; i < 50; i++) {
            points.add(pos);
            pos = pos.add(vel);
            vel = vel.add(gravity);
            if (pos.y < 0) break;
        }
    }

    public List<Vec3d> getPoints() { return points; }
}
