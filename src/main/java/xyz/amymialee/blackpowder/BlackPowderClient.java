package xyz.amymialee.blackpowder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import xyz.amymialee.blackpowder.items.GunItem;
import xyz.amymialee.blackpowder.registry.BlackPowderItems;

@Environment(EnvType.CLIENT)
public class BlackPowderClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(BlackPowder.fireParticle, (client, handler, packetByteBuf, responseSender) -> {
            if (client.world != null) {
                Entity entity = client.world.getEntityById(packetByteBuf.readInt());
                client.execute(() -> {
                    if (entity != null) {
                        Vec3d pos = entity.getEyePos();
                        Vec3d rot = entity.getRotationVector();
                        for (int i = 0; i < 16; i++) {
                            client.world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, rot.x * ((client.world.random.nextFloat() * 0.1f) + 0.05f), rot.y * ((client.world.random.nextFloat() * 0.1f) + 0.05f), rot.z * ((client.world.random.nextFloat() * 0.1f) + 0.05f));
                        }
                    }
                });
            }
        });
    }

    static {
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.FLINTLOCK_PISTOL, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.BLUNDERBUSS, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.RIFLE, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.MUSKET, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);

        FabricModelPredicateProviderRegistry.register(BlackPowderItems.FLINTLOCK_CARBINE, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.BLUNDERBEHEMOTH, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.RESOLUTE_RIFLE, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.BOUNDLESS_MUSKET, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && GunItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }
}