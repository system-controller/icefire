package com.ikalon.icefire.items.custom;

import com.ikalon.icefire.entity.IceShardEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class IceShardItem extends Item {
    public IceShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.BLOCK_GLASS_BREAK,
                SoundCategory.NEUTRAL,
                0.5f,
                0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        if (!world.isClient()) {
            IceShardEntity iceShardEntity = new IceShardEntity(world, user);
            iceShardEntity.setItem(itemStack);
            iceShardEntity.setPitch(12f);
            iceShardEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
            iceShardEntity.setBodyYaw(90);
            world.spawnEntity(iceShardEntity);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
