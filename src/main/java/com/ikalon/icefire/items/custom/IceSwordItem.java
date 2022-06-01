package com.ikalon.icefire.items.custom;

import com.ikalon.icefire.IceFire;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class IceSwordItem extends SwordItem {
    public IceSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1),
                attacker);
        return super.postHit(stack, target, attacker);
    }

    /*
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos blockPos = context.getBlockPos();
            Block block = world.getBlockState(blockPos).getBlock();
            IceFire.LOGGER.info(block.toString());
            if (block == Blocks.WATER) {
                world.setBlockState(blockPos, Blocks.ICE.getDefaultState());
            }
        }

        return ActionResult.SUCCESS;
    }*/

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient()) {
            BlockHitResult blockHitResult = SwordItem.raycast(world, user, RaycastContext.FluidHandling.WATER);

            if (blockHitResult.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            }

            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();

                if (block == Blocks.WATER && blockState.getFluidState().isStill()) {
                    world.setBlockState(blockPos, Blocks.ICE.getDefaultState());
                }
            }
        }

        return TypedActionResult.success(itemStack);
    }
}
