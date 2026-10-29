package com.vomiter.offcutting;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = OffCutting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static int CUTTING_BOARD_INSERT_COUNT = Integer.MAX_VALUE;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.IntValue CUTTING_BOARD_INSERT_COUNT_VALUE = BUILDER
            .comment("Maximum number of items inserted into a cutting board per interaction.",
                    "The actual amount is also limited by the held stack and the cutting board inventory.",
                    "Use 2147483647 to allow the item's maximum stack size, including changes made by other mods.",
                    "Use 1 to mimic the cutting board behavior before version 1.3 of Farmer's Delight.")
            .defineInRange("cuttingBoardInsertCount", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() != SPEC) return;
        CUTTING_BOARD_INSERT_COUNT = CUTTING_BOARD_INSERT_COUNT_VALUE.get();
    }
}
