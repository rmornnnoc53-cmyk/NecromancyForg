package com.example.necromancy.item;

import com.example.necromancy.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NecromancerStaffItem extends Item {
    public NecromancerStaffItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack staff = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(staff);

        int soulSlot = -1;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.SOUL.get())) { soulSlot = i; break; }
        }
        if (soulSlot < 0) return InteractionResultHolder.fail(staff);

        if (!level.isClientSide) {
            ItemStack soul = player.getInventory().getItem(soulSlot);
            soul.shrink(1);
            Vec3 look = player.getLookAngle();
            double x = player.getX() + look.x * 1.5;
            double y = player.getY();
            double z = player.getZ() + look.z * 1.5;

            Skeleton minion = EntityType.SKELETON.create(level);
            if (minion != null) {
                minion.moveTo(x, y, z, player.getYRot(), 0);
                minion.setPersistenceRequired();
                minion.setCustomName(net.minecraft.network.chat.Component.literal("Призванный скелет"));
                minion.setCustomNameVisible(false);
                minion.setTarget(null);
                level.addFreshEntity(minion);
                if (level instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.SOUL, x, y + 1, z, 18, 0.4, 0.6, 0.4, 0.03);
                }
                staff.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                player.getCooldowns().addCooldown(this, 80);
            }
        }
        return InteractionResultHolder.sidedSuccess(staff, level.isClientSide);
    }
}
