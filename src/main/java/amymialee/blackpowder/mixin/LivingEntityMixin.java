package amymialee.blackpowder.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    public int timeUntilRegen;
    @Inject(method = "damage", at = @At("HEAD"))
    public void damageHead(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.name.contains("bullet")) {
            this.timeUntilRegen = 0;
        }
    }
}
