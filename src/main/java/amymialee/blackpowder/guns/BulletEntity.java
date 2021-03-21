package amymialee.blackpowder.guns;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class BulletEntity extends ArrowEntity {
    private final String damageType;

    public BulletEntity(World world, LivingEntity owner, double damage, int punch, SoundEvent sound, String damageType) {
        super(world, owner);
        this.damage = damage;
        this.punch = punch;
        this.sound = sound;
        this.setAir(500);
        this.damageType = damageType;
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
    private List<Entity> piercingKilledEntities;

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }
            this.piercedEntities.add(entity.getEntityId());
        }

        Entity entity2 = this.getOwner();
        DamageSource damageSource2 = null;
        if (entity2 == null) {
            switch (damageType) {
                case "bullet":
                    damageSource2 = BulletDamageSource.bullet(this, this);
                    break;
                case "shotgun_bullet":
                    damageSource2 = BulletDamageSource.shotgun_bullet(this, this);
                    break;
                case "pierce_bullet":
                    damageSource2 = BulletDamageSource.pierce_bullet(this, this);
                    break;
                case "strong_bullet":
                    damageSource2 = BulletDamageSource.strong_bullet(this, this);
                    break;
            }
        } else {
            switch (damageType) {
                case "bullet":
                    damageSource2 = BulletDamageSource.bullet(this, entity2);
                    break;
                case "shotgun_bullet":
                    damageSource2 = BulletDamageSource.shotgun_bullet(this, entity2);
                    break;
                case "pierce_bullet":
                    damageSource2 = BulletDamageSource.pierce_bullet(this, entity2);
                    break;
                case "strong_bullet":
                    damageSource2 = BulletDamageSource.strong_bullet(this, entity2);
                    break;
            }
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity) entity2).onAttacking(entity);
            }
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }

        if (entity.damage(damageSource2, (float) damage)) {
            if (bl) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (!this.world.isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }

                if (this.punch > 0) {
                    Vec3d vec3d = this.getVelocity().multiply(1.0D, 0.0D, 1.0D).normalize().multiply((double) this.punch * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingEntity.addVelocity(vec3d.x * 1.5, 0.15D, vec3d.z * 1.5);
                    }
                }

                if (!this.world.isClient && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
                }

                this.onHit(livingEntity);
                if (livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity) entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.piercingKilledEntities != null) {
                    this.piercingKilledEntities.add(livingEntity);
                }

                /*if (!this.world.isClient && entity2 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity2;
                    if (this.piercingKilledEntities != null && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, this.piercingKilledEntities);
                    } else if (!entity.isAlive() && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, Arrays.asList(entity));
                    }
                }*/
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
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.remove();
            }
        }
    }
}