package com.example.necromancy.item;

import com.example.necromancy.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonSigilItem extends Item {
    private final EntityType<? extends Mob> entityType;
    private final String summonName;

    public SummonSigilItem(Properties properties, EntityType<? extends Mob> entityType, String summonName) {
        super(properties);
        this.entityType = entityType;
        this.summonName = summonName;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(held);

        int soulSlot = -1;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).is(ModItems.SOUL.get())) {
                soulSlot = i;
                break;
            }
        }
        if (soulSlot < 0) return InteractionResultHolder.fail(held);

        if (!level.isClientSide) {
            player.getInventory().getItem(soulSlot).shrink(1);
            Vec3 look = player.getLookAngle();
            double x = player.getX() + look.x * 1.8;
            double y = player.getY();
            double z = player.getZ() + look.z * 1.8;
            Mob minion = entityType.create(level);
            if (minion != null) {
                minion.moveTo(x, y, z, player.getYRot(), 0);
                minion.setPersistenceRequired();
                minion.setCustomName(net.minecraft.network.chat.Component.literal(summonName));
                level.addFreshEntity(minion);
                if (level instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.SOUL, x, y + 1.0, z,
                            20, 0.45, 0.55, 0.45, 0.025);
                    server.sendParticles(ParticleTypes.PORTAL, x, y + 0.5, z,
                            12, 0.3, 0.4, 0.3, 0.02);
                }
                player.getCooldowns().addCooldown(this, 60);
                if (!player.getAbilities().instabuild) held.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(held, level.isClientSide);
    }
}
