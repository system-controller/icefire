package com.ikalon.icefire.items.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class NetherOrb extends Item {
    public NetherOrb(Settings settings) {
        super(settings);
    }

    protected static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        float f = player.getPitch();
        float g = player.getYaw();
        Vec3d vec3d = player.getEyePos();
        float h = MathHelper.cos(-g * 0.017453292F - 3.1415927F);
        float i = MathHelper.sin(-g * 0.017453292F - 3.1415927F);
        float j = -MathHelper.cos(-f * 0.017453292F);
        float k = MathHelper.sin(-f * 0.017453292F);
        float l = i * j;
        float n = h * j;
        double d = 5.0D;
        Vec3d vec3d2 = vec3d.add((double)l * 50.0D, (double)k * 50.0D, (double)n * 50.0D);
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, fluidHandling, player));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            BlockHitResult b = raycast(world,user, RaycastContext.FluidHandling.NONE);
            ItemStack itemStack = user.getStackInHand(hand);
            if (b.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            }

            if (b.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = b.getBlockPos();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (block == Blocks.STONE)
                {
                    world.setBlockState(blockPos,Blocks.NETHERRACK.getDefaultState());
                }
                else if(block == Blocks.SAND || block == Blocks.RED_SAND)
                {
                    world.setBlockState(blockPos,Blocks.SOUL_SAND.getDefaultState());
                }
                else if(block == Blocks.GRASS_BLOCK)
                {
                    world.setBlockState(blockPos,Blocks.CRIMSON_NYLIUM.getDefaultState());
                }
            }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
