package com.example.necromancy.registry;

import com.example.necromancy.NecromancyMod;
import com.example.necromancy.item.NecromancerStaffItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NecromancyMod.MODID);

    public static final RegistryObject<Item> SOUL = ITEMS.register("soul",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NECROMANCER_STAFF = ITEMS.register("necromancer_staff",
            () -> new NecromancerStaffItem(new Item.Properties().stacksTo(1).durability(180)));
    public static final RegistryObject<Item> NECROMANCER_ALTAR_ITEM = ITEMS.register("necromancer_altar",
            () -> new BlockItem(ModBlocks.NECROMANCER_ALTAR.get(), new Item.Properties()));

    public static void register(IEventBus bus) { ITEMS.register(bus); }
}
