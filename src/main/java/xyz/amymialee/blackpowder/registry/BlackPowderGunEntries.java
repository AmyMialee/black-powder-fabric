package xyz.amymialee.blackpowder.registry;

import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.text.TextColor;
import xyz.amymialee.blackpowder.util.GunEntry;

public class BlackPowderGunEntries {
    public static GunEntry ENTRY_FLINTLOCK_PISTOL = new GunEntry()
            .setReloadTime(35)
            .setAmmoPredicate(BlackPowderItems.MUSKET_BALL)
            .setDamage(8)
            .setSource((e) -> new EntityDamageSource("flintlock", e).setProjectile())
            .setFireSound(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_LOADING_END)
            .setScope(false);
    public static GunEntry ENTRY_BLUNDERBUSS = new GunEntry()
            .setReloadTime(80)
            .setAmmoPredicate(BlackPowderItems.BLUNDER_BALL)
            .setPelletCount(8)
            .setBulletSpread(15)
            .setDamage(5)
            .setSource((e) -> new EntityDamageSource("blunderbuss", e).setProjectile())
            .setFireSound(BlackPowderSounds.ITEM_BLUNDERBUSS_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_BLUNDERBUSS_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_BLUNDERBUSS_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_BLUNDERBUSS_LOADING_END)
            .setScope(false);
    public static GunEntry ENTRY_RIFLE = new GunEntry()
            .setReloadTime(60)
            .setAmmoPredicate(BlackPowderItems.MUSKET_BALL)
            .setDamage(22)
            .setSource((e) -> new EntityDamageSource("rifle", e).setProjectile())
            .setFireSound(BlackPowderSounds.ITEM_RIFLE_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_RIFLE_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_RIFLE_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_RIFLE_LOADING_END);
    public static GunEntry ENTRY_MUSKET = new GunEntry()
            .setReloadTime(120)
            .setAmmoPredicate(BlackPowderItems.MUSKET_BALL)
            .setDamage(28)
            .setSource((e) -> new EntityDamageSource("musket", e).setProjectile().setBypassesArmor())
            .setFireSound(BlackPowderSounds.ITEM_MUSKET_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_MUSKET_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_MUSKET_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_MUSKET_LOADING_END);
    public static GunEntry ENTRY_FLINTLOCK_CARBINE = new GunEntry()
            .setReloadTime(1)
            .setAmmoPredicate(BlackPowderItems.MUSKET_BALL)
            .setDamage(8)
            .setSource((e) -> new EntityDamageSource("flintlock", e).setProjectile())
            .setFireSound(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_FLINTLOCK_PISTOL_LOADING_END)
            .setScope(false)
            .setTextColor(TextColor.fromRgb(8802476));
    public static GunEntry ENTRY_BLUNDERBEHEMOTH = new GunEntry()
            .setReloadTime(80)
            .setAmmoPredicate(BlackPowderItems.BLUNDER_BALL)
            .setPelletCount(160)
            .setBulletSpread(15)
            .setDamage(4)
            .setSource((e) -> new EntityDamageSource("blunderbuss", e).setProjectile())
            .setFireSound(BlackPowderSounds.ITEM_BLUNDERBUSS_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_BLUNDERBUSS_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_BLUNDERBUSS_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_BLUNDERBUSS_LOADING_END)
            .setScope(false)
            .setTextColor(TextColor.fromRgb(8802476));
    public static GunEntry ENTRY_RESOLUTE_RIFLE = new GunEntry()
            .setReloadTime(60)
            .setAmmoPredicate(BlackPowderItems.MUSKET_BALL)
            .setPelletCount(8)
            .setDamage(22)
            .setSource((e) -> new EntityDamageSource("rifle", e).setProjectile())
            .setFireSound(BlackPowderSounds.ITEM_RIFLE_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_RIFLE_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_RIFLE_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_RIFLE_LOADING_END)
            .setTextColor(TextColor.fromRgb(8802476));
    public static GunEntry ENTRY_BOUNDLESS_MUSKET = new GunEntry()
            .setReloadTime(120)
            .setAmmoPredicate(BlackPowderItems.MUSKET_BALL)
            .setDamage(320)
            .setSource((e) -> new EntityDamageSource("musket", e).setProjectile().setBypassesArmor().setBypassesProtection())
            .setFireSound(BlackPowderSounds.ITEM_MUSKET_SHOOT)
            .setReloadSoundStart(BlackPowderSounds.ITEM_MUSKET_LOADING_START)
            .setReloadSoundMiddle(BlackPowderSounds.ITEM_MUSKET_LOADING_MIDDLE)
            .setReloadSoundEnd(BlackPowderSounds.ITEM_MUSKET_LOADING_END)
            .setTextColor(TextColor.fromRgb(8802476));
}