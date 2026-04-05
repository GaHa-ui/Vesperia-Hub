package com.vesperia.hub.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class VesperiaConfig {
    private static final Path CONFIG = FabricLoader.getInstance().getConfigDir().resolve("vesperia-hub.txt");

    // HUD
    public static boolean FPS_COUNTER = true;
    public static boolean PING_DISPLAY = true;
    public static boolean COORDS_DISPLAY = true;
    public static boolean DIRECTION_HUD = true;
    public static boolean POTION_HUD = true;
    public static boolean ARMOR_HUD = true;
    public static boolean KEYSTROKES = true;
    public static boolean CPS_COUNTER = true;
    public static boolean TOTEM_COUNTER = true;
    public static boolean TARGET_HUD = true;
    public static boolean COMBO_COUNTER = true;

    // Effects
    public static boolean HIT_EFFECTS = true;
    public static boolean DAMAGE_NUMBERS = true;
    public static boolean HIT_PARTICLES = true;
    public static boolean SCREEN_SHAKE = true;
    public static boolean JUMP_CIRCLE = true;

    // Crosshair
    public static boolean CUSTOM_CROSSHAIR = true;
    public static boolean DYNAMIC_CROSSHAIR = true;
    public static boolean DOT_CROSSHAIR = false;
    public static float CROSSHAIR_SIZE = 1.0f;
    public static int CROSSHAIR_COLOR = 0xFFFFFFFF;

    // QoL
    public static boolean ZOOM = true;
    public static float ZOOM_LEVEL = 0.4f;
    public static boolean FULLBRIGHT = false;
    public static boolean TRAJECTORY_PREDICTION = true;

    public static void load() {
        if (!Files.exists(CONFIG)) {
            save();
            return;
        }
        try {
            for (String line : Files.readAllLines(CONFIG)) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] p = line.split("=");
                if (p.length != 2) continue;
                String k = p[0].trim(), v = p[1].trim();
                switch (k) {
                    case "fpsCounter" -> FPS_COUNTER = Boolean.parseBoolean(v);
                    case "pingDisplay" -> PING_DISPLAY = Boolean.parseBoolean(v);
                    case "coordsDisplay" -> COORDS_DISPLAY = Boolean.parseBoolean(v);
                    case "directionHud" -> DIRECTION_HUD = Boolean.parseBoolean(v);
                    case "potionHud" -> POTION_HUD = Boolean.parseBoolean(v);
                    case "armorHud" -> ARMOR_HUD = Boolean.parseBoolean(v);
                    case "keystrokes" -> KEYSTROKES = Boolean.parseBoolean(v);
                    case "cpsCounter" -> CPS_COUNTER = Boolean.parseBoolean(v);
                    case "totemCounter" -> TOTEM_COUNTER = Boolean.parseBoolean(v);
                    case "targetHud" -> TARGET_HUD = Boolean.parseBoolean(v);
                    case "comboCounter" -> COMBO_COUNTER = Boolean.parseBoolean(v);
                    case "hitEffects" -> HIT_EFFECTS = Boolean.parseBoolean(v);
                    case "damageNumbers" -> DAMAGE_NUMBERS = Boolean.parseBoolean(v);
                    case "hitParticles" -> HIT_PARTICLES = Boolean.parseBoolean(v);
                    case "screenShake" -> SCREEN_SHAKE = Boolean.parseBoolean(v);
                    case "jumpCircle" -> JUMP_CIRCLE = Boolean.parseBoolean(v);
                    case "customCrosshair" -> CUSTOM_CROSSHAIR = Boolean.parseBoolean(v);
                    case "dynamicCrosshair" -> DYNAMIC_CROSSHAIR = Boolean.parseBoolean(v);
                    case "dotCrosshair" -> DOT_CROSSHAIR = Boolean.parseBoolean(v);
                    case "crosshairSize" -> CROSSHAIR_SIZE = Float.parseFloat(v);
                    case "crosshairColor" -> CROSSHAIR_COLOR = Integer.parseInt(v);
                    case "zoom" -> ZOOM = Boolean.parseBoolean(v);
                    case "zoomLevel" -> ZOOM_LEVEL = Float.parseFloat(v);
                    case "fullbright" -> FULLBRIGHT = Boolean.parseBoolean(v);
                    case "trajectoryPrediction" -> TRAJECTORY_PREDICTION = Boolean.parseBoolean(v);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Vesperia Hub Config\n\n");
        sb.append("# HUD\n");
        sb.append("fpsCounter=").append(FPS_COUNTER).append("\n");
        sb.append("pingDisplay=").append(PING_DISPLAY).append("\n");
        sb.append("coordsDisplay=").append(COORDS_DISPLAY).append("\n");
        sb.append("directionHud=").append(DIRECTION_HUD).append("\n");
        sb.append("potionHud=").append(POTION_HUD).append("\n");
        sb.append("armorHud=").append(ARMOR_HUD).append("\n");
        sb.append("keystrokes=").append(KEYSTROKES).append("\n");
        sb.append("cpsCounter=").append(CPS_COUNTER).append("\n");
        sb.append("totemCounter=").append(TOTEM_COUNTER).append("\n");
        sb.append("targetHud=").append(TARGET_HUD).append("\n");
        sb.append("comboCounter=").append(COMBO_COUNTER).append("\n\n");
        sb.append("# Effects\n");
        sb.append("hitEffects=").append(HIT_EFFECTS).append("\n");
        sb.append("damageNumbers=").append(DAMAGE_NUMBERS).append("\n");
        sb.append("hitParticles=").append(HIT_PARTICLES).append("\n");
        sb.append("screenShake=").append(SCREEN_SHAKE).append("\n");
        sb.append("jumpCircle=").append(JUMP_CIRCLE).append("\n\n");
        sb.append("# Crosshair\n");
        sb.append("customCrosshair=").append(CUSTOM_CROSSHAIR).append("\n");
        sb.append("dynamicCrosshair=").append(DYNAMIC_CROSSHAIR).append("\n");
        sb.append("dotCrosshair=").append(DOT_CROSSHAIR).append("\n");
        sb.append("crosshairSize=").append(CROSSHAIR_SIZE).append("\n");
        sb.append("crosshairColor=").append(CROSSHAIR_COLOR).append("\n\n");
        sb.append("# QoL\n");
        sb.append("zoom=").append(ZOOM).append("\n");
        sb.append("zoomLevel=").append(ZOOM_LEVEL).append("\n");
        sb.append("fullbright=").append(FULLBRIGHT).append("\n");
        sb.append("trajectoryPrediction=").append(TRAJECTORY_PREDICTION).append("\n");
        try { Files.writeString(CONFIG, sb.toString()); } catch (Exception e) { e.printStackTrace(); }
    }
}
