package amymialee.blackpowder.guns;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class GunItem extends CrossbowItem {
    public int bulletCount;
    public float inaccuracy;
    public int chargeTime;
    public int quickChargeChange;
    public SoundEvent START;
    public SoundEvent MIDDLE;
    public SoundEvent END;
    public SoundEvent SHOOT;
    public SoundEvent HIT;
    public float velocity;
    public Item ammo;
    public int damage;
    public int piercing;

    public GunItem(int bulletCount, float inaccuracy, int chargeTime, int quickChargeChange, SoundEvent[] soundEvents, float velocity, Item ammo, int damage, int piercing) {
        super(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof());
        this.bulletCount = bulletCount;
        this.inaccuracy = inaccuracy;
        this.chargeTime = chargeTime;
        this.quickChargeChange = quickChargeChange;
        this.START = soundEvents[0];
        this.MIDDLE = soundEvents[1];
        this.END = soundEvents[2];
        this.SHOOT = soundEvents[3];
        this.HIT = soundEvents[4];
        this.velocity = velocity;
        this.ammo = ammo;
        this.damage = damage;
        this.piercing = piercing;
    }

    public static int getChargeTime(GunItem item) {
        return item.chargeTime;
    }

    public static int getQuickChargeChange(GunItem item) {
        return item.quickChargeChange;
    }

    public static int getBulletCount(GunItem item) {
        return item.bulletCount;
    }

    public static SoundEvent getShootSound(GunItem item) {
        return item.SHOOT;
    }

    public static SoundEvent getHitSound(GunItem item) {
        return item.HIT;
    }

    private boolean charged = false;
    private boolean loaded = false;

    public Predicate<ItemStack> getHeldProjectiles() {
        return getProjectiles().or((stack) -> stack.getItem() == Items.ARROW);
    }

    public Predicate<ItemStack> getProjectiles() {
        return (stack) -> stack.getItem() == ammo;
    }

    public Item getAmmo() {
        return ammo;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isCharged(itemStack)) {
            shootAll(world, user, itemStack, getSpeed(), inaccuracy);
            setCharged(itemStack, false);
            return TypedActionResult.consume(itemStack);
        } else if (!user.getArrowType(itemStack).isEmpty()) {
            if (!isCharged(itemStack)) {
                this.charged = false;
                this.loaded = false;
                user.setCurrentHand(hand);
            }

            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        float f = getPullProgress(i, stack);
        if (f >= 1.0F && !isCharged(stack) && loadProjectiles(user, stack)) {
            setCharged(stack, true);
            SoundCategory soundCategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), END, soundCategory, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
        int i = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
        int j = i == 0 ? 1 : 3;
        boolean bl = shooter instanceof PlayerEntity && ((PlayerEntity)shooter).abilities.creativeMode;
        ItemStack itemStack = shooter.getArrowType(projectile);
        ItemStack itemStack2 = itemStack.copy();
        for(int k = 0; k < j; ++k) {
            if (bl) {
                itemStack = new ItemStack(((GunItem)projectile.getItem()).ammo);
            }
            if (k > 0) {
                itemStack = itemStack2.copy();
            }
            if (itemStack.isEmpty() && bl) {
                itemStack = new ItemStack(((GunItem)projectile.getItem()).ammo);
                itemStack2 = itemStack.copy();
            }
            if (!loadProjectile(shooter, projectile, itemStack, k > 0, bl)) {
                return false;
            }
        }
        return true;
    }

    private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        if (projectile.isEmpty()) {
            return false;
        } else {
            boolean bl = creative && projectile.getItem() instanceof BulletItem;
            ItemStack itemStack2;
            if (!bl && !creative && !simulated) {
                itemStack2 = projectile.split(1);
                if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                    ((PlayerEntity)shooter).inventory.removeOne(projectile);
                }
            } else {
                itemStack2 = projectile.copy();
            }

            putProjectile(crossbow, itemStack2);
            return true;
        }
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        compoundTag.putBoolean("Charged", charged);
    }

    private static void putProjectile(ItemStack gun, ItemStack projectile) {
        CompoundTag compoundTag = gun.getOrCreateTag();
        ListTag listTag2;
        if (compoundTag.contains("ChargedProjectiles", 9)) {
            listTag2 = compoundTag.getList("ChargedProjectiles", 10);
        } else {
            listTag2 = new ListTag();
        }

        CompoundTag compoundTag2 = new CompoundTag();
        projectile.toTag(compoundTag2);
        listTag2.add(compoundTag2);
        compoundTag.put("ChargedProjectiles", listTag2);
    }

    private static List<ItemStack> getProjectiles(ItemStack gun) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundTag = gun.getTag();
        if (compoundTag != null && compoundTag.contains("ChargedProjectiles", 9)) {
            ListTag listTag = compoundTag.getList("ChargedProjectiles", 10);
            if (listTag != null) {
                for(int i = 0; i < listTag.size(); ++i) {
                    CompoundTag compoundTag2 = listTag.getCompound(i);
                    list.add(ItemStack.fromTag(compoundTag2));
                }
            }
        }

        return list;
    }

    private static void clearProjectiles(ItemStack gun) {
        CompoundTag compoundTag = gun.getTag();
        if (compoundTag != null) {
            ListTag listTag = compoundTag.getList("ChargedProjectiles", 9);
            listTag.clear();
            compoundTag.put("ChargedProjectiles", listTag);
        }
    }

    private static void shoot(World world, LivingEntity shooter, ItemStack gun, ItemStack projectile, float soundPitch, float speed, float divergence, float simulated) {
        if (!world.isClient) {
            for(int b = 0; b < GunItem.getBulletCount((GunItem) gun.getItem()); b++) {
                PersistentProjectileEntity projectileEntity2 = createBullet(world, shooter, gun, projectile);
                if (projectileEntity2 != null) {
                    projectileEntity2.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

                    if (shooter instanceof CrossbowUser) {
                        CrossbowUser crossbowUser = (CrossbowUser) shooter;
                        crossbowUser.shoot(crossbowUser.getTarget(), gun, projectileEntity2, simulated);
                    } else {
                        Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
                        Quaternion quaternion = new Quaternion(new Vector3f(vec3d), simulated, true);
                        Vec3d vec3d2 = shooter.getRotationVec(1.0F);
                        Vector3f vector3f = new Vector3f(vec3d2);
                        vector3f.rotate(quaternion);
                        projectileEntity2.setVelocity(vector3f.getX(), vector3f.getY(), vector3f.getZ(), speed, divergence);
                    }

                    world.spawnEntity(projectileEntity2);
                }
            }
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), GunItem.getShootSound((GunItem) gun.getItem()), SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }

    private static PersistentProjectileEntity createBullet(World world, LivingEntity entity, ItemStack gun, ItemStack bullet) {
        PersistentProjectileEntity persistentProjectileEntity = null;
        if (bullet.getItem() instanceof BulletItem) {
            persistentProjectileEntity = ((BulletItem) bullet.getItem()).createBullet(world, bullet, entity, ((GunItem)gun.getItem()).damage, 0, ((GunItem)gun.getItem()).HIT);
        }
        if (entity instanceof PlayerEntity && persistentProjectileEntity != null) {
            persistentProjectileEntity.setCritical(true);
        }
        if (persistentProjectileEntity != null) {
            persistentProjectileEntity.setSound(GunItem.getHitSound((GunItem) gun.getItem()));
            persistentProjectileEntity.setShotFromCrossbow(true);
            int i = EnchantmentHelper.getLevel(Enchantments.PIERCING, gun) + ((GunItem) gun.getItem()).piercing;
            if (i > 0) {
                persistentProjectileEntity.setPierceLevel((byte) i);
            }
            persistentProjectileEntity.setDamage(2);
        }
        return persistentProjectileEntity;
    }

    public static void shootAll(World world, LivingEntity entity, ItemStack stack, float speed, float divergence) {
        List<ItemStack> list = getProjectiles(stack);
        float[] fs = getSoundPitches(entity.getRandom());

        if (!entity.getEntityWorld().isClient) {
            ((ServerWorld) entity.getEntityWorld()).spawnParticles(ParticleTypes.POOF,
                    entity.getX(),
                    entity.getY() + 1.4,
                    entity.getZ(),
                    24,
                    0.2,
                    0.2,
                    0.2,
                    0.01);
        }

        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemStack = list.get(i);
            if (!itemStack.isEmpty()) {
                if (i == 0) {
                    shoot(world, entity, stack, itemStack, fs[i], speed, divergence, 0.0F);
                } else if (i == 1) {
                    shoot(world, entity, stack, itemStack, fs[i], speed, divergence, -10.0F);
                } else if (i == 2) {
                    shoot(world, entity, stack, itemStack, fs[i], speed, divergence, 10.0F);
                }
            }
        }

        postShoot(world, entity, stack);
    }

    private static float[] getSoundPitches(Random random) {
        boolean bl = random.nextBoolean();
        return new float[]{1.0F, getSoundPitch(bl), getSoundPitch(!bl)};
    }

    private static float getSoundPitch(boolean flag) {
        float f = flag ? 0.63F : 0.43F;
        return 1.0F / (RANDOM.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void postShoot(World world, LivingEntity entity, ItemStack stack) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            if (!world.isClient) {
                Criteria.SHOT_CROSSBOW.trigger(serverPlayerEntity, stack);
            }

            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
        }

        clearProjectiles(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            SoundEvent soundEvent = this.getQuickChargeSound();
            SoundEvent soundEvent2 = MIDDLE;
            float f = (float)(stack.getMaxUseTime() - remainingUseTicks) / (float)getPullTime(stack);
            if (f < 0.2F) {
                this.charged = false;
                this.loaded = false;
            }

            if (f >= 0.2F && !this.charged) {
                this.charged = true;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundEvent2 != null && !this.loaded) {
                this.loaded = true;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent2, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    public int getMaxUseTime(ItemStack stack) {
        return getPullTime(stack) + 3;
    }

    public static int getPullTime(ItemStack stack) {
        int i = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? getChargeTime((GunItem) stack.getItem()) : getChargeTime((GunItem) stack.getItem()) - getQuickChargeChange((GunItem) stack.getItem()) * i;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    private SoundEvent getQuickChargeSound() {
        return START;
    }

    private static float getPullProgress(int useTicks, ItemStack stack) {
        float f = (float)useTicks / (float)getPullTime(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<ItemStack> list = getProjectiles(stack);
        if (isCharged(stack) && !list.isEmpty()) {
            ItemStack itemStack = list.get(0);
            tooltip.add((new TranslatableText("item.minecraft.crossbow.projectile")).append(" ").append(itemStack.toHoverableText()));
            if (context.isAdvanced() && itemStack.getItem() == Items.FIREWORK_ROCKET) {
                List<Text> list2 = Lists.newArrayList();
                Items.FIREWORK_ROCKET.appendTooltip(itemStack, world, list2, context);
                if (!list2.isEmpty()) {
                    for(int i = 0; i < list2.size(); ++i) {
                        list2.set(i, (new LiteralText("  ")).append(list2.get(i)).formatted(Formatting.GRAY));
                    }

                    tooltip.addAll(list2);
                }
            }

        }
    }

    private float getSpeed() {
        return velocity;
    }

    public int getRange() {
        return 8;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.getItem() instanceof GunItem;
    }
}
