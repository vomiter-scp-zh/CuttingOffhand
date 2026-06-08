package com.vomiter.offcutting;

import com.mojang.logging.LogUtils;
import com.vomiter.offcutting.common.event.EventHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(OffCutting.MOD_ID)
public class OffCutting
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "offcutting";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String path){
        return Helpers.id(OffCutting.MOD_ID, path);
    }

    public OffCutting(ModContainer mod, IEventBus modBus) {
        EventHandler.init();
        modBus.addListener(this::commonSetup);
        mod.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

}
