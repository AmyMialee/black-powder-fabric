package amymialee.blackpowder.guns;

import amymialee.blackpowder.BlackPowder;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class GunSoundEvents {
    private GunSoundEvents() {}

    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_LOADING_END = createEvent("flintlock_loading_end");
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_LOADING_MIDDLE = createEvent("flintlock_loading_middle");
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_LOADING_START = createEvent("flintlock_loading_start");
    public static final SoundEvent ITEM_FLINTLOCK_PISTOL_SHOOT = createEvent("flintlock_shoot");

    public static final SoundEvent ITEM_BLUNDERBUSS_PISTOL_LOADING_END = createEvent("blunderbuss_loading_end");
    public static final SoundEvent ITEM_BLUNDERBUSS_PISTOL_LOADING_MIDDLE = createEvent("blunderbuss_loading_middle");
    public static final SoundEvent ITEM_BLUNDERBUSS_PISTOL_LOADING_START = createEvent("blunderbuss_loading_start");
    public static final SoundEvent ITEM_BLUNDERBUSS_PISTOL_SHOOT = createEvent("blunderbuss_shoot");

    public static final SoundEvent ITEM_MUSKET_PISTOL_LOADING_END = createEvent("musket_loading_end");
    public static final SoundEvent ITEM_MUSKET_PISTOL_LOADING_MIDDLE = createEvent("musket_loading_middle");
    public static final SoundEvent ITEM_MUSKET_PISTOL_LOADING_START = createEvent("musket_loading_start");
    public static final SoundEvent ITEM_MUSKET_PISTOL_SHOOT = createEvent("musket_shoot");

    public static final SoundEvent ITEM_RIFLE_PISTOL_LOADING_END = createEvent("rifle_loading_end");
    public static final SoundEvent ITEM_RIFLE_PISTOL_LOADING_MIDDLE = createEvent("rifle_loading_middle");
    public static final SoundEvent ITEM_RIFLE_PISTOL_LOADING_START = createEvent("rifle_loading_start");
    public static final SoundEvent ITEM_RIFLE_PISTOL_SHOOT = createEvent("rifle_shoot");

    public static final SoundEvent ENTITY_BULLET_IMPACT = createEvent("bullet_impact");

    private static SoundEvent createEvent(String key) {
        Identifier location = new Identifier(BlackPowder.MODID, key);
        return Registry.register(Registry.SOUND_EVENT, location, new SoundEvent(location));
    }
}
