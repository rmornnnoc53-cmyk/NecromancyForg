package com.example.necromancy;

import com.example.necromancy.registry.ModBlocks;
import com.example.necromancy.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NecromancyMod.MODID)
public class NecromancyMod {
    public static final String MODID = "necromancy";

    public NecromancyMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModBlocks.register(bus);
    }
}
