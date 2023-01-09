package xyz.amymialee.blackpowder.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.amymialee.blackpowder.BlackPowder;

public final class BlackPowderSounds {
    //Flintlock Pistol
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_LOADING_END = registerEvent("flintlock_loading_end");
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_LOADING_MIDDLE = registerEvent("flintlock_loading_middle");
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_LOADING_START = registerEvent("flintlock_loading_start");
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_SHOOT = registerEvent("flintlock_shoot");
    //Blunderbuss
    public static final SoundEvent ITEM_BLUNDERBUSS_LOADING_END = registerEvent("blunderbuss_loading_end");
    public static final SoundEvent ITEM_BLUNDERBUSS_LOADING_MIDDLE = registerEvent("blunderbuss_loading_middle");
    public static final SoundEvent ITEM_BLUNDERBUSS_LOADING_START = registerEvent("blunderbuss_loading_start");
    public static final SoundEvent ITEM_BLUNDERBUSS_SHOOT = registerEvent("blunderbuss_shoot");
    //Musket
    public static final SoundEvent ITEM_MUSKET_LOADING_END = registerEvent("musket_loading_end");
    public static final SoundEvent ITEM_MUSKET_LOADING_MIDDLE = registerEvent("musket_loading_middle");
    public static final SoundEvent ITEM_MUSKET_LOADING_START = registerEvent("musket_loading_start");
    public static final SoundEvent ITEM_MUSKET_SHOOT = registerEvent("musket_shoot");
    //Rifle
    public static final SoundEvent ITEM_RIFLE_LOADING_END = registerEvent("rifle_loading_end");
    public static final SoundEvent ITEM_RIFLE_LOADING_MIDDLE = registerEvent("rifle_loading_middle");
    public static final SoundEvent ITEM_RIFLE_LOADING_START = registerEvent("rifle_loading_start");
    public static final SoundEvent ITEM_RIFLE_SHOOT = registerEvent("rifle_shoot");
    //Bullet
    public static final SoundEvent ENTITY_BULLET_IMPACT = registerEvent("bullet_impact");

    public static void init() {}

    private static SoundEvent registerEvent(String key) {
        Identifier location = BlackPowder.id(key);
        return Registry.register(Registry.SOUND_EVENT, location, new SoundEvent(location));
    }
}