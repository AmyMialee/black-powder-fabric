package amymialee.blackpowder.guns;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BulletEntity extends ArrowEntity {
    public BulletEntity(World world, LivingEntity owner, double damage, int punch, SoundEvent sound) {
        super(world, owner);
        this.damage = damage;
        this.punch = punch;
        this.sound = sound;
        this.setAir(500);
    }

    public void tick() {
        super.tick();
        if (this.inGround) {
            this.destroy();
        }
    }

    private final double damage;
    private final int punch;
    private final SoundEvent sound;
    private IntOpenHashSet piercedEntities;

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        int i = (int) damage;
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }
            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }
            this.piercedEntities.add(entity.getEntityId());
        }
        Entity entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) {
            damageSource2 = DamageSource.arrow(this, this);
        } else {
            damageSource2 = DamageSource.arrow(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }
        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }
        if (entity.damage(damageSource2, (float)i)) {
            if (bl) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (this.punch > 0) {
                    Vec3d vec3d = this.getVelocity().multiply(1.0D, 0.0D, 1.0D).normalize().multiply((double)this.punch * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingEntity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                    }
                }
                if (!this.world.isClient && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity);
                }
                this.onHit(livingEntity);
                if (livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }
            }
            this.playSound(this.sound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1D));
            this.yaw += 180.0F;
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                this.remove();
            }
        }

    }
}
