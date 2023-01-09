package xyz.amymialee.blackpowder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import xyz.amymialee.blackpowder.items.GunItem;
import xyz.amymialee.blackpowder.registry.BlackPowderItems;
import xyz.amymialee.blackpowder.registry.BlackPowderSounds;

import java.util.function.ToIntFunction;

public class BlackPowder implements ModInitializer {
    public static final String MOD_ID = "blackpowder";
    public static final Identifier clickConsume = id("click_consume");
    public static final Identifier fireParticle = id("fire_particle");

    @Override
    public void onInitialize() {
        BlackPowderItems.init();
        BlackPowderSounds.init();

        ServerPlayNetworking.registerGlobalReceiver(clickConsume, (minecraftServer, serverPlayer, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            Vec3d pos = new Vec3d(packetByteBuf.readDouble(), packetByteBuf.readDouble(), packetByteBuf.readDouble());
            Vec3d rot = new Vec3d(packetByteBuf.readDouble(), packetByteBuf.readDouble(), packetByteBuf.readDouble());
            minecraftServer.execute(() -> {
                if (serverPlayer.getMainHandStack().getItem() instanceof GunItem gunItem) {
                    gunItem.blackPowder$doAttack(serverPlayer, pos, rot);
                }
            });
        });
    }

    public static Identifier id(String... path) {
        return new Identifier(MOD_ID, String.join(".", path));
    }
}