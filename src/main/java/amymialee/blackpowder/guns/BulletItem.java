package amymialee.blackpowder.guns;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletItem extends ArrowItem {
    public BulletItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createBullet(World world, ItemStack stack, LivingEntity shooter) {
        System.out.println("T");
        BulletEntity bulletEntity = new BulletEntity(world, shooter);
        bulletEntity.initFromStack(stack);
        return bulletEntity;
    }
}
