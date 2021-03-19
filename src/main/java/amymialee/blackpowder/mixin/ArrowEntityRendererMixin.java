package amymialee.blackpowder.mixin;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArrowEntityRenderer.class)
public class ArrowEntityRendererMixin {
    private static final Identifier BULLET_TEXTURE = new Identifier("textures/entity/bullet.png");
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTexture(ArrowEntity arrowEntity, CallbackInfoReturnable<Identifier> cir) {
        if (arrowEntity.getAir() == 500) {
            cir.setReturnValue(BULLET_TEXTURE);
        }
    }
}
