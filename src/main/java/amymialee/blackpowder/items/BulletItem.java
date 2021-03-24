package amymialee.blackpowder.items;

import amymialee.blackpowder.guns.BulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class BulletItem extends ArrowItem {
    public BulletItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createBullet(World world, ItemStack stack, LivingEntity shooter, double damage, int punch, SoundEvent sound, String damageType) {
        return new BulletEntity(world, shooter, damage, punch, sound, damageType);
    }
}
