package com.vesperia.hub.client.config;

import com.vesperia.hub.VesperiaHubClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ModConfig {
    public static boolean ENABLE_HIT_PARTICLES = true;
    public static boolean ENABLE_TARGET_HUD = true;
    public static boolean ENABLE_DAMAGE_NUMBERS = true;
    public static boolean ENABLE_CRITICAL_EFFECTS = true;
    public static boolean ENABLE_TRAJECTORY_PREDICTION = true;

    public static float HIT_PARTICLE_SCALE = 1.0f;
    public static float DAMAGE_NUMBER_SCALE = 1.0f;
    public static float TARGET_HUD_SCALE = 1.0f;

    public static int HIT_PARTICLE_LIFETIME = 25;
    public static int DAMAGE_NUMBER_LIFETIME = 40;
    public static int TRAJECTORY_SEGMENTS = 20;

    public static int NORMAL_HIT_COLOR = 0xFFFFD700;
    public static int CRITICAL_HIT_COLOR = 0xFFFF4444;
    public static int HEAL_COLOR = 0xFF44FF44;
    public static int NORMAL_DAMAGE_COLOR = 0xFFFFD700;
    public static int CRITICAL_DAMAGE_COLOR = 0xFFFF4444;

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("vesperia-hub.properties");

    public static void load() {
        Properties props = new Properties();

        if (Files.exists(CONFIG_PATH)) {
            try (InputStream is = Files.newInputStream(CONFIG_PATH)) {
                props.load(is);

                ENABLE_HIT_PARTICLES = Boolean.parseBoolean(props.getProperty("enableHitParticles", "true"));
                ENABLE_TARGET_HUD = Boolean.parseBoolean(props.getProperty("enableTargetHUD", "true"));
                ENABLE_DAMAGE_NUMBERS = Boolean.parseBoolean(props.getProperty("enableDamageNumbers", "true"));
                ENABLE_CRITICAL_EFFECTS = Boolean.parseBoolean(props.getProperty("enableCriticalEffects", "true"));
                ENABLE_TRAJECTORY_PREDICTION = Boolean.parseBoolean(props.getProperty("enableTrajectoryPrediction", "true"));

                HIT_PARTICLE_SCALE = Float.parseFloat(props.getProperty("hitParticleScale", "1.0"));
                DAMAGE_NUMBER_SCALE = Float.parseFloat(props.getProperty("damageNumberScale", "1.0"));
                TARGET_HUD_SCALE = Float.parseFloat(props.getProperty("targetHUDScale", "1.0"));

                HIT_PARTICLE_LIFETIME = Integer.parseInt(props.getProperty("hitParticleLifetime", "25"));
                DAMAGE_NUMBER_LIFETIME = Integer.parseInt(props.getProperty("damageNumberLifetime", "40"));
                TRAJECTORY_SEGMENTS = Integer.parseInt(props.getProperty("trajectorySegments", "20"));

                VesperiaHubClient.LOGGER.info("Vesperia Hub config loaded");
            } catch (IOException e) {
                VesperiaHubClient.LOGGER.error("Failed to load config", e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        Properties props = new Properties();

        props.setProperty("enableHitParticles", String.valueOf(ENABLE_HIT_PARTICLES));
        props.setProperty("enableTargetHUD", String.valueOf(ENABLE_TARGET_HUD));
        props.setProperty("enableDamageNumbers", String.valueOf(ENABLE_DAMAGE_NUMBERS));
        props.setProperty("enableCriticalEffects", String.valueOf(ENABLE_CRITICAL_EFFECTS));
        props.setProperty("enableTrajectoryPrediction", String.valueOf(ENABLE_TRAJECTORY_PREDICTION));

        props.setProperty("hitParticleScale", String.valueOf(HIT_PARTICLE_SCALE));
        props.setProperty("damageNumberScale", String.valueOf(DAMAGE_NUMBER_SCALE));
        props.setProperty("targetHUDScale", String.valueOf(TARGET_HUD_SCALE));

        props.setProperty("hitParticleLifetime", String.valueOf(HIT_PARTICLE_LIFETIME));
        props.setProperty("damageNumberLifetime", String.valueOf(DAMAGE_NUMBER_LIFETIME));
        props.setProperty("trajectorySegments", String.valueOf(TRAJECTORY_SEGMENTS));

        try (OutputStream os = Files.newOutputStream(CONFIG_PATH)) {
            props.store(os, "Vesperia Hub Configuration");
            VesperiaHubClient.LOGGER.info("Vesperia Hub config saved");
        } catch (IOException e) {
            VesperiaHubClient.LOGGER.error("Failed to save config", e);
        }
    }
}
