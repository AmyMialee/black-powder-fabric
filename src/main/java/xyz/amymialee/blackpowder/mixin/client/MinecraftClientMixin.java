package xyz.amymialee.blackpowder.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.blackpowder.BlackPowder;
import xyz.amymialee.blackpowder.util.IClickConsumingItem;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void blackPowder$cancelAttack(CallbackInfoReturnable<Boolean> cir) {
        if (this.player != null) {
            if (this.player.getMainHandStack().getItem() instanceof IClickConsumingItem item) {
                PacketByteBuf buf = PacketByteBufs.create();
                Vec3d pos = this.player.getEyePos();
                buf.writeDouble(pos.getX());
                buf.writeDouble(pos.getY());
                buf.writeDouble(pos.getZ());
                Vec3d rot = this.player.getRotationVector();
                buf.writeDouble(rot.getX());
                buf.writeDouble(rot.getY());
                buf.writeDouble(rot.getZ());
                ClientPlayNetworking.send(BlackPowder.clickConsume, buf);
                cir.setReturnValue(false);
            }
        }
    }
}