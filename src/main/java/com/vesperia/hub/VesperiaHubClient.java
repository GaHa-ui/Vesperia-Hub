package com.vesperia.hub;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VesperiaHubClient implements ClientModInitializer {
    public static final String MOD_ID = "vesperia-hub";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Vesperia Hub loaded!");
        LOGGER.info("60 visual features ready!");
        LOGGER.info("Press RSHIFT to open settings");
    }
}
