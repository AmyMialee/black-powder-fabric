package amymialee.blackpowder.guns;

import amymialee.blackpowder.BlackPowder;
import com.oroarmor.multi_item_lib.UniqueItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static amymialee.blackpowder.items.BlackPowderItems.*;
import static amymialee.blackpowder.BlackPowder.*;

public class BlackPowderGuns {
    static SoundEvent[] blunderbussSounds = {
            GunSoundEvents.ITEM_BLUNDERBUSS_PISTOL_LOADING_START,
            GunSoundEvents.ITEM_BLUNDERBUSS_PISTOL_LOADING_MIDDLE,
            GunSoundEvents.ITEM_BLUNDERBUSS_PISTOL_LOADING_END,
            GunSoundEvents.ITEM_BLUNDERBUSS_PISTOL_SHOOT,
            GunSoundEvents.ENTITY_BULLET_IMPACT
    };
    static SoundEvent[] flintlockSounds = {
            GunSoundEvents.ITEM_FLINTLOCK_PISTOL_LOADING_START,
            GunSoundEvents.ITEM_FLINTLOCK_PISTOL_LOADING_MIDDLE,
            GunSoundEvents.ITEM_FLINTLOCK_PISTOL_LOADING_END,
            GunSoundEvents.ITEM_FLINTLOCK_PISTOL_SHOOT,
            GunSoundEvents.ENTITY_BULLET_IMPACT
    };
    static SoundEvent[] musketSounds = {
            GunSoundEvents.ITEM_MUSKET_PISTOL_LOADING_START,
            GunSoundEvents.ITEM_MUSKET_PISTOL_LOADING_MIDDLE,
            GunSoundEvents.ITEM_MUSKET_PISTOL_LOADING_END,
            GunSoundEvents.ITEM_MUSKET_PISTOL_SHOOT,
            GunSoundEvents.ENTITY_BULLET_IMPACT
    };
    static SoundEvent[] rifleSounds = {
            GunSoundEvents.ITEM_RIFLE_PISTOL_LOADING_START,
            GunSoundEvents.ITEM_RIFLE_PISTOL_LOADING_MIDDLE,
            GunSoundEvents.ITEM_RIFLE_PISTOL_LOADING_END,
            GunSoundEvents.ITEM_RIFLE_PISTOL_SHOOT,
            GunSoundEvents.ENTITY_BULLET_IMPACT
    };

    public static Item FLINTLOCK_PISTOL = new GunItem(1, config.FlintlockInaccuracy, config.FlintlockReloadTime, config.FlintlockQuickChargeTime,
            flintlockSounds, 12F, MUSKET_BALL, config.FlintlockDamage, 0, "bullet");

    public static Item BLUNDERBUSS = new GunItem(8, config.BlunderbussInaccuracy, config.BlunderbussReloadTime, config.BlunderbussQuickChargeTime,
            blunderbussSounds, 8F, BLUNDER_BALL, config.BlunderbussDamage, 0, "shotgun_bullet");

    public static Item RIFLE = new GunItem(1, config.RifleInaccuracy, config.RifleReloadTime, config.RifleQuickChargeTime,
            rifleSounds, 22F, MUSKET_BALL, config.RifleDamage, 0, "pierce_bullet");

    public static Item MUSKET = new GunItem(1, config.MusketInaccuracy, config.MusketReloadTime, config.MusketQuickChargeTime,
            musketSounds, 16F, MUSKET_BALL, config.MusketDamage, 0, "strong_bullet");


    public static Item FLINTLOCK_CARBINE = new GunItem(1, 7, 3, 1,
            flintlockSounds, 12F, MUSKET_BALL, 12, 0, "bullet");

    public static Item BLUNDERBEHEMOTH = new GunItem(800, 28, 320, 40,
            blunderbussSounds, 8F, BLUNDER_BALL, 4, 0, "shotgun_bullet");

    public static Item RESOLUTE_RIFLE = new GunItem(1, 0, 240, 40,
            rifleSounds, 198F, MUSKET_BALL, 22, 100, "pierce_bullet");

    public static Item BOUNDLESS_MUSKET = new GunItem(1, 4, 200, 20,
            musketSounds, 16F, MUSKET_BALL, 318, 0, "strong_bullet");

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "flintlock_pistol"), FLINTLOCK_PISTOL);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "blunderbuss"), BLUNDERBUSS);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "rifle"), RIFLE);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "musket"), MUSKET);

        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "flintlock_carbine"), FLINTLOCK_CARBINE);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "blunderbehemoth"), BLUNDERBEHEMOTH);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "resolute_rifle"), RESOLUTE_RIFLE);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "boundless_musket"), BOUNDLESS_MUSKET);

        UniqueItemRegistry.CROSSBOW.addItemToRegistry(FLINTLOCK_PISTOL);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(BLUNDERBUSS);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(RIFLE);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(MUSKET);

        UniqueItemRegistry.CROSSBOW.addItemToRegistry(FLINTLOCK_CARBINE);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(BLUNDERBEHEMOTH);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(RESOLUTE_RIFLE);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(BOUNDLESS_MUSKET);
    }
}
