package amymialee.blackpowder.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {
    @Shadow private double damage;
    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public void redirectDamage(EntityHitResult entityHitResult, CallbackInfo ci) {
        DamageSource damageSource2 = DamageSource.arrow(((PersistentProjectileEntity)((Object)this)), ((PersistentProjectileEntity)((Object)this)));
        if (damageSource2.name.contains("bullet")) {
            int i = (int) damage;
        }
    }
}
