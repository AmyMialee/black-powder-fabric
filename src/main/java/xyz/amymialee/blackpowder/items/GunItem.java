package xyz.amymialee.blackpowder.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.blackpowder.BlackPowder;
import xyz.amymialee.blackpowder.util.GunEntry;
import xyz.amymialee.blackpowder.util.IClickConsumingItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GunItem extends Item implements IClickConsumingItem {
    public final GunEntry gunEntry;

    public GunItem(GunEntry gunEntry, FabricItemSettings settings) {
        super(settings.maxCount(1));
        this.gunEntry = gunEntry;
    }

    @Override
    public void blackPowder$doAttack(ServerPlayerEntity player, Vec3d pos, Vec3d rot) {
        ItemStack stack = player.getMainHandStack();
        if (!(player.world instanceof ServerWorld serverWorld) || player.getItemCooldownManager().isCoolingDown(this) || !this.hasAmmo(stack)) {
            return;
        }
        if (this.gunEntry.getFireSound() != null) {
            serverWorld.playSoundFromEntity(null, player, this.gunEntry.getFireSound(), player.getSoundCategory(), 1.0F, 1.0F);
        }
        double distanceCap = 4096f;
        Vec3d cameraRot = player.getRotationVec(1.0f);
        int count = 0;
        Map<LivingEntity, Float> hitEntities = new HashMap<>();
        for (int i = 0; i < this.gunEntry.getPelletCount(); i++) {
            Vec3d newRot = cameraRot;
            if (i != 0) {
                newRot = newRot.rotateX(player.getRandom().nextFloat() * this.gunEntry.getBulletSpread());
                newRot = newRot.rotateY(player.getRandom().nextFloat() * this.gunEntry.getBulletSpread());
                newRot = newRot.rotateZ(player.getRandom().nextFloat() * this.gunEntry.getBulletSpread());
            }
            Vec3d cameraTarget = pos.add(newRot.multiply(distanceCap));
            Box box = player.getBoundingBox().stretch(cameraTarget).expand(1.0, 1.0, 1.0);
            EntityHitResult entityHitResult = ProjectileUtil.raycast(player, pos, cameraTarget, box, (entity) -> this.isValidTarget(player.world, player, entity), distanceCap);
            if (entityHitResult != null) {
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof EnderDragonPart part) {
                    entity = part.owner;
                }
                if (entity instanceof LivingEntity livingEntity) {
                    double distance = entityHitResult.getPos().distanceTo(pos);
                    float damage = this.gunEntry.getDamage(distance);
                    if (hitEntities.containsKey(livingEntity)) {
                        damage += hitEntities.get(livingEntity);
                    }
                    hitEntities.put(livingEntity, damage);
                }
                Vec3d vec3d = entityHitResult.getPos();
                serverWorld.spawnParticles(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                if (this.gunEntry.getHitSound() != null) {
                    serverWorld.playSound(vec3d.x, vec3d.y, vec3d.z, this.gunEntry.getHitSound(), player.getSoundCategory(), 1f, 0.75f + player.getRandom().nextFloat() * 0.5f, true);
                }
            }
        }
        float total = 0f;
        for (Map.Entry<LivingEntity, Float> entry : hitEntities.entrySet()) {
            entry.getKey().timeUntilRegen = 0;
            entry.getKey().damage(this.gunEntry.getSource(player), entry.getValue());
            total += entry.getValue();
        }
        this.consumeAmmo(stack);
        player.getItemCooldownManager().set(this, this.gunEntry.getFireRate());
        if (total > 0) {
            float averageDamage = Math.max(1, this.gunEntry.getDamage() * this.gunEntry.getPelletCount());
            float pitch = (total / averageDamage) * 0.45f;
            serverWorld.playSoundFromEntity(null, player, SoundEvents.ENTITY_ARROW_HIT_PLAYER, player.getSoundCategory(), 1.0F, pitch);
        }
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(player.getId());
        Packet<ClientPlayPacketListener> packet = ServerPlayNetworking.createS2CPacket(BlackPowder.fireParticle, buf);
        for(int j = 0; j < serverWorld.getPlayers().size(); j++) {
            serverWorld.sendToPlayerIfNearby(serverWorld.getPlayers().get(j), false, pos.x, pos.y, pos.z, packet);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            if (!user.isSneaking()) {
                if (this.isFullyLoaded(stack)) {
                    user.getItemCooldownManager().set(this, 2);
                    return TypedActionResult.fail(stack);
                }
                if (this.getAmmoStack(user).isEmpty()) {
                    return TypedActionResult.fail(stack);
                }
                stack.getOrCreateNbt().putBoolean("reloading", true);
            } else if (this.gunEntry.hasScope()) {
                boolean isScoped = this.shouldScope(stack);
                stack.getOrCreateNbt().putBoolean("scoped", !isScoped);
                if (isScoped) {
                    user.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0F, 1.0F);
                } else {
                    user.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0F, 1.0F);
                }
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerWorld serverWorld && entity instanceof ServerPlayerEntity player) {
            if (stack.getOrCreateNbt().getBoolean("reloading")) {
                ItemStack ammoStack = this.getAmmoStack(player);
                if (ammoStack.isEmpty()) {
                    stack.getOrCreateNbt().putBoolean("reloading", false);
                    return;
                }
                if (selected) {
                    int nextProgress = stack.getOrCreateNbt().getInt("reloadProgress") + 1;
                    stack.getOrCreateNbt().putInt("reloadProgress", nextProgress);
                    if (nextProgress == 1) {
                        if (this.gunEntry.getReloadSoundStart() != null) {
                            serverWorld.playSoundFromEntity(null, player, this.gunEntry.getReloadSoundStart(), player.getSoundCategory(), 1.0F, 1.0F);
                        }
                    } else if (nextProgress == this.gunEntry.getReloadTime() / 2) {
                        if (this.gunEntry.getReloadSoundMiddle() != null) {
                            serverWorld.playSoundFromEntity(null, player, this.gunEntry.getReloadSoundMiddle(), player.getSoundCategory(), 1.0F, 1.0F);
                        }
                    } else if (nextProgress >= this.gunEntry.getReloadTime()) {
                        stack.getOrCreateNbt().putBoolean("reloading", false);
                        stack.getOrCreateNbt().putInt("reloadProgress", 0);
                        int numToLoad = Math.min(this.gunEntry.getAmmoCount() - this.getAmmo(stack), ammoStack.getCount());
                        if (this.addAmmo(stack, numToLoad)) {
                            if (!player.isCreative()) ammoStack.decrement(numToLoad);
                            if (this.gunEntry.getReloadSoundEnd() != null) {
                                serverWorld.playSoundFromEntity(null, player, this.gunEntry.getReloadSoundEnd(), player.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }
                        stack.getOrCreateNbt().putBoolean("reloading", false);
                    }
                } else {
                    stack.getOrCreateNbt().putInt("reloadProgress", 0);
                }
            }
            if (stack.getOrCreateNbt().getBoolean("scoped")) {
                if (!selected) {
                    stack.getOrCreateNbt().putBoolean("scoped", false);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public boolean shouldScope(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean("scoped") && !stack.getOrCreateNbt().getBoolean("reloading");
    }

    private ItemStack getAmmoStack(PlayerEntity player) {
        for(int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (this.gunEntry.getAmmoPredicate().test(itemStack)) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean addAmmo(ItemStack stack, int amount) {
        int current = stack.getOrCreateNbt().getInt("ammo");
        if (current + amount > this.gunEntry.getAmmoCount()) {
            return false;
        }
        stack.getOrCreateNbt().putInt("ammo", current + amount);
        return true;
    }

    private void consumeAmmo(ItemStack stack) {
        int newAmmo = Math.max(0, stack.getOrCreateNbt().getInt("ammo") - 1);
        stack.getOrCreateNbt().putInt("ammo", newAmmo);
        if (newAmmo == 0) {
            stack.getOrCreateNbt().putBoolean("reloading", true);
        }
    }

    private void setAmmo(ItemStack stack, int amount) {
        stack.getOrCreateNbt().putInt("ammo", amount);
    }

    private int getAmmo(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("ammo");
    }

    private boolean isFullyLoaded(ItemStack stack) {
        return this.getAmmo(stack) >= this.gunEntry.getAmmoCount();
    }

    private boolean hasAmmo(ItemStack stack) {
        return this.getAmmo(stack) > 0;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.getOrCreateNbt().getBoolean("reloading")) {
            return (int) (14 * ((float) stack.getOrCreateNbt().getInt("reloadProgress") / this.gunEntry.getReloadTime()));
        } else {
            return (int) (14 * ((float) this.getAmmo(stack) / this.gunEntry.getAmmoCount()));
        }
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (this.isFullyLoaded(stack)) {
            return 6711039;
        }
        if (stack.getOrCreateNbt().getBoolean("reloading")) {
            return 16737894;
        }
        return 6750054;
    }

    public boolean isValidTarget(World world, LivingEntity attacker, Entity target) {
        if (target == null) return false;
        if (attacker == null) return false;
        if (target == attacker) return false;
        if (!attacker.canSee(target)) return false;
        if (target.world != attacker.world) return false;
        if (target instanceof LivingEntity livingEntity) {
            if (livingEntity.isDead()) return false;
            if (target.isRemoved()) return false;
            if (target.isTeammate(attacker)) return false;
            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) return false;
            if (target instanceof TameableEntity tamed && tamed.isOwner(attacker)) return false;
            return target.canHit();
        }
        if (target instanceof EnderDragonPart part) {
            if (part.owner.isDead()) return false;
            if (part.owner.isRemoved()) return false;
            if (part.owner.isTeammate(attacker)) return false;
            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(part.owner)) return false;
            return part.owner.canHit();
        }
        return false;
    }

    public static boolean isCharged(ItemStack itemStack) {
        return !itemStack.getOrCreateNbt().getBoolean("reloading");
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        List<Text> list = text.getWithStyle(text.getStyle().withColor(this.gunEntry.getTextColor()));
        if (list.size() > 0) {
            return list.get(0);
        }
        return text;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.gunitem.tooltip1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.gunitem.tooltip2").formatted(Formatting.GRAY));
        if (this.gunEntry.hasScope()) tooltip.add(Text.translatable("item.gunitem.tooltip3").formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.gunEntry.hasGlint();
    }
}