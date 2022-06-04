package com.ikalon.icefire.registry;

import com.ikalon.icefire.IceFire;
import com.ikalon.icefire.entity.EntitySpawnPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class IceFireModClient implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(IceFire.MOD_ID, "spawn_packet");
    @Override
    public void onInitializeClient() {
        IceFire.LOGGER.info("INITIALIZING CLIENT");
        EntityRendererRegistry.register(IceFire.ICE_SHARD_ENTITY_ENTITY_TYPE, (context) ->
                new FlyingItemEntityRenderer(context));
        receiveEntityPacket();
        /* Adds our particle textures to vanilla's Texture Atlas so it can be shown properly.
         * Modify the namespace and particle id accordingly.
         *
         * This is only used if you plan to add your own textures for the particle. Otherwise, remove  this.*/

        /* Registers our particle client-side.
         * First argument is our particle's instance, created previously on ExampleMod.
         * Second argument is the particle's factory. The factory controls how the particle behaves.
         * In this example, we'll use FlameParticle's Factory.*/

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier("icefire", "particle/simple_test_particle"));
        }));

        ParticleFactoryRegistry.getInstance().register(IceFire.SIMPLE_TEST_PARTICLE, FlameParticle.Factory::new);
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
