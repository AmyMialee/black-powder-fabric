package xyz.amymialee.blackpowder.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.blackpowder.items.GunItem;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);
    @Shadow protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);
    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void blackPowder$holdGunProperly(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack stack, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (stack.getItem() instanceof GunItem gunItem) {
            matrices.push();
            boolean scoped = stack.getOrCreateNbt().getBoolean("scoped");
            boolean mainHand = hand == Hand.MAIN_HAND;
            Arm arm = mainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            boolean rightArm = arm == Arm.RIGHT;
            int armOffset = rightArm ? 1 : -1;
            if (stack.getOrCreateNbt().getBoolean("reloading")) {
                this.applyEquipOffset(matrices, arm, 0.0f);
                matrices.translate(armOffset * -0.4785682f, -0.0943870022892952, 0.05731530860066414);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-11.935f));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) armOffset * 65.3f));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) armOffset * -9.785f));
                float timeRemaining = stack.getOrCreateNbt().getInt("reloadProgress") - tickDelta + 1.0f;
                float reloadProgress = timeRemaining / gunItem.gunEntry.getReloadTime();
                if (reloadProgress > 1.0f) {
                    reloadProgress = 1.0f;
                }
                if (reloadProgress > 0.1f) {
                    float h = MathHelper.sin((timeRemaining - 0.1F) * 1.3f);
                    float j = reloadProgress - 0.1f;
                    float k = h * j;
                    matrices.translate(k * 0.0f, k * 0.004f, k * 0.0f);
                }
                matrices.translate(0.0f, 0.2f, reloadProgress * 0.04f);
                matrices.scale(1.0f, 1.0f, 1.0f + reloadProgress * 0.2f);
                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(armOffset * 45.0f));
            } else {
                float f = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927f);
                float g = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855f);
                float h = -0.2f * MathHelper.sin(swingProgress * 3.1415927f);
                matrices.translate(armOffset * f, g, h);
                this.applyEquipOffset(matrices, arm, equipProgress);
                this.applySwingOffset(matrices, arm, swingProgress);
                if (scoped && swingProgress < 0.001f && mainHand) {
                    matrices.translate(armOffset * -0.641864f, 0.0, 0.0);
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(armOffset * 10.0f));
                }
            }
            this.renderItem(player, stack, rightArm ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !rightArm, matrices, vertexConsumers, light);
            matrices.pop();
            ci.cancel();
        }
    }
}