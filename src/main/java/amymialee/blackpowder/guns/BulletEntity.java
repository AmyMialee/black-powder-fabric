package amymialee.blackpowder.guns;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class BulletEntity extends ArrowEntity {
    public BulletEntity(World world, LivingEntity owner) {
        super(world, owner);
    }
    public BulletEntity(World world) {
        super(world, null);
    }
}
