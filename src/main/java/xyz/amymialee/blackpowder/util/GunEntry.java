package xyz.amymialee.blackpowder.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.MathHelper;
import xyz.amymialee.blackpowder.registry.BlackPowderSounds;

import java.util.function.Function;
import java.util.function.Predicate;

public class GunEntry {
    int ammoCount = 1;
    int fireRate = 0;
    int reloadTime = 60;
    Predicate<ItemStack> ammoPredicate;

    int pelletCount = 1;
    float bulletSpread = 0;
    float damage = 0;
    Function<Entity, DamageSource> source = (e) -> new EntityDamageSource("generic", e).setProjectile();

    float midRange = 12f;
    float maxRange = 24f;

    SoundEvent fireSound;
    SoundEvent reloadSoundStart;
    SoundEvent reloadSoundMiddle;
    SoundEvent reloadSoundEnd;
    SoundEvent hitSound = BlackPowderSounds.ENTITY_BULLET_IMPACT;

    boolean scope = true;
    TextColor textColor = TextColor.fromRgb(16766720);
    boolean glint = false;

    public float getDamage(double distance) {
        if (distance > this.maxRange) return this.getDamage() / 2;
        if (distance < this.midRange) {
            return MathHelper.lerp((float) Math.abs(distance) / this.midRange, this.getDamage() * 1.5f, this.getDamage());
        } else {
            return MathHelper.lerp((float) (1 - (distance - this.midRange) / (this.maxRange - this.midRange)), this.getDamage() / 2, this.getDamage());
        }
    }

    public GunEntry setAmmoCount(int ammoCount) {
        this.ammoCount = ammoCount;
        return this;
    }

    public GunEntry setFireRate(int fireRate) {
        this.fireRate = fireRate;
        return this;
    }

    public GunEntry setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
        return this;
    }

    public GunEntry setAmmoPredicate(Item ammoItem) {
        this.ammoPredicate = (i) -> i.getItem() == ammoItem;
        return this;
    }

    public GunEntry setAmmoPredicate(Predicate<ItemStack> ammoPredicate) {
        this.ammoPredicate = ammoPredicate;
        return this;
    }

    public GunEntry setPelletCount(int pelletCount) {
        this.pelletCount = pelletCount;
        return this;
    }

    public GunEntry setBulletSpread(float bulletSpread) {
        this.bulletSpread = (float) Math.toRadians(bulletSpread);
        return this;
    }

    public GunEntry setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public GunEntry setSource(Function<Entity, DamageSource> source) {
        this.source = source;
        return this;
    }

    public GunEntry setFireSound(SoundEvent fireSound) {
        this.fireSound = fireSound;
        return this;
    }

    public GunEntry setReloadSoundStart(SoundEvent reloadSoundStart) {
        this.reloadSoundStart = reloadSoundStart;
        return this;
    }

    public GunEntry setReloadSoundMiddle(SoundEvent reloadSoundMiddle) {
        this.reloadSoundMiddle = reloadSoundMiddle;
        return this;
    }

    public GunEntry setReloadSoundEnd(SoundEvent reloadSoundEnd) {
        this.reloadSoundEnd = reloadSoundEnd;
        return this;
    }

    public GunEntry setHitSound(SoundEvent hitSound) {
        this.hitSound = hitSound;
        return this;
    }

    public GunEntry setMidRange(float midRange) {
        this.midRange = midRange;
        return this;
    }

    public GunEntry setMaxRange(float maxRange) {
        this.maxRange = maxRange;
        return this;
    }

    public GunEntry setScope(boolean scope) {
        this.scope = scope;
        return this;
    }

    public GunEntry setTextColor(TextColor textColor) {
        this.textColor = textColor;
        return this;
    }

    public GunEntry setGlint(boolean glint) {
        this.glint = glint;
        return this;
    }

    public int getAmmoCount() {
        return this.ammoCount;
    }

    public int getFireRate() {
        return this.fireRate;
    }

    public int getReloadTime() {
        return this.reloadTime;
    }

    public Predicate<ItemStack> getAmmoPredicate() {
        return this.ammoPredicate;
    }

    public int getPelletCount() {
        return this.pelletCount;
    }

    public float getBulletSpread() {
        return this.bulletSpread;
    }

    public float getDamage() {
        return this.damage;
    }

    public DamageSource getSource(Entity owner) {
        return this.source.apply(owner);
    }

    public SoundEvent getFireSound() {
        return this.fireSound;
    }

    public SoundEvent getReloadSoundStart() {
        return this.reloadSoundStart;
    }

    public SoundEvent getReloadSoundMiddle() {
        return this.reloadSoundMiddle;
    }

    public SoundEvent getReloadSoundEnd() {
        return this.reloadSoundEnd;
    }

    public SoundEvent getHitSound() {
        return this.hitSound;
    }

    public Function<Entity, DamageSource> getSource() {
        return this.source;
    }

    public float getMidRange() {
        return this.midRange;
    }

    public float getMaxRange() {
        return this.maxRange;
    }

    public boolean hasScope() {
        return this.scope;
    }

    public TextColor getTextColor() {
        return this.textColor;
    }

    public boolean hasGlint() {
        return this.glint;
    }
}