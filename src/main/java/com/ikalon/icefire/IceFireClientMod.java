package com.ikalon.icefire;

import com.ikalon.icefire.entity.EntitySpawnPacket;
import com.ikalon.icefire.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class IceFireClientMod implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(IceFire.MOD_ID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FAST_ICE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLOW_ICE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEEP_FREEZE_ICE, RenderLayer.getTranslucent());
        EntityRendererRegistry.register(IceFire.ICE_SHARD_ENTITY_ENTITY_TYPE, (context) ->
                new FlyingItemEntityRenderer<>(context));
        receiveEntityPacket();
    }



    public void receiveEntityPacket() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID, (client, handler, buf, responseSender) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(buf.readVarInt());
            UUID uuid = buf.readUuid();
            int entityId = buf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(buf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            client.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}
