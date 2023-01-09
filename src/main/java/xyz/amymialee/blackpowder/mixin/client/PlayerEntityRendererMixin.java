package xyz.amymialee.blackpowder.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.blackpowder.items.GunItem;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void blackPowder$holdGun(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack itemStack = abstractClientPlayerEntity.getStackInHand(hand);
        if (itemStack.getItem() instanceof GunItem && !abstractClientPlayerEntity.handSwinging) {
            if (GunItem.isCharged(itemStack)) {
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
            } else if (itemStack.getOrCreateNbt().getBoolean("reloading")) {
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
            }
        }
    }
}