package amymialee.blackpowder.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.jetbrains.annotations.Nullable;

public class BulletDamageSource extends ProjectileDamageSource {
    public BulletDamageSource(String name, Entity projectile, @Nullable Entity attacker) {
        super(name, projectile, attacker);
    }

    public static DamageSource bullet(PersistentProjectileEntity projectile, @Nullable Entity attacker) {
        return (new BulletDamageSource("bullet", projectile, attacker)).setProjectile();
    }
    public static DamageSource shotgun_bullet(PersistentProjectileEntity projectile, @Nullable Entity attacker) {
        return (new BulletDamageSource("shotgun_bullet", projectile, attacker)).setProjectile();
    }
    public static DamageSource pierce_bullet(PersistentProjectileEntity projectile, @Nullable Entity attacker) {
        return (new BulletDamageSource("pierce_bullet", projectile, attacker)).setProjectile().setUsesMagic();
    }

    public static DamageSource strong_bullet(PersistentProjectileEntity projectile, @Nullable Entity attacker) {
        return (new BulletDamageSource("strong_bullet", projectile, attacker)).setProjectile();
    }
}
