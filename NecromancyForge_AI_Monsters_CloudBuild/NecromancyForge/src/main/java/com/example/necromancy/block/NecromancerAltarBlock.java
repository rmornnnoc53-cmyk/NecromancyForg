package com.example.necromancy.block;

import com.example.necromancy.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class NecromancerAltarBlock extends Block {
    public NecromancerAltarBlock(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);
        if (!held.is(Items.ROTTEN_FLESH)) return InteractionResult.PASS;

        if (!level.isClientSide) {
            if (!player.getAbilities().instabuild) held.shrink(1);
            ItemStack soul = new ItemStack(ModItems.SOUL.get());
            if (!player.getInventory().add(soul)) player.drop(soul, false);
            level.playSound(null, pos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8F, 0.7F);
            if (level instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SOUL, pos.getX() + 0.5, pos.getY() + 1.0,
                        pos.getZ() + 0.5, 12, 0.35, 0.3, 0.35, 0.02);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
