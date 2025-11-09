package com.lyviamc.lyvia_creeper_demo;

import com.lyviamc.lyvia_creeper_demo.command.CreeperBehaviorConfigCommand;
import com.lyviamc.lyvia_creeper_demo.item.LyviaModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LyviaCreeperDemo implements ModInitializer {
    public static final String MOD_ID = "lyviamc_creeper_demo";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CreeperBehaviorConfigCommand.register();
        LyviaModItems.initialize();

        LOGGER.info("Lyvia's Mod Is Initialized.");
    }
}
