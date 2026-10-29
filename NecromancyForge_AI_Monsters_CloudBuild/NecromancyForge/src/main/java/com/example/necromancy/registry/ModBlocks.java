package com.example.necromancy.registry;

import com.example.necromancy.NecromancyMod;
import com.example.necromancy.block.NecromancerAltarBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NecromancyMod.MODID);

    public static final RegistryObject<Block> NECROMANCER_ALTAR = BLOCKS.register("necromancer_altar",
            () -> new NecromancerAltarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static void register(IEventBus bus) { BLOCKS.register(bus); }
}
