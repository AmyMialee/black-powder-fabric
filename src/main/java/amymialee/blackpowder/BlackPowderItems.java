package amymialee.blackpowder;

import amymialee.blackpowder.guns.BulletItem;
import amymialee.blackpowder.guns.GunItem;
import amymialee.blackpowder.guns.GunSoundEvents;
import com.oroarmor.multi_item_lib.UniqueItemRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlackPowderItems {
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

    public static Item MUSKET_BALL = new BulletItem(new FabricItemSettings().group(ItemGroup.COMBAT));
    public static Item BLUNDER_BALL = new BulletItem(new FabricItemSettings().group(ItemGroup.COMBAT));

    public static Item FLINTLOCK_PISTOL = new GunItem(1, 7, 50, 8, flintlockSounds, 12F, MUSKET_BALL);
    public static Item BLUNDERBUSS = new GunItem(8, 14, 160, 25, blunderbussSounds, 8F, BLUNDER_BALL);
    public static Item RIFLE = new GunItem(1, 2, 160, 30, rifleSounds, 22F, MUSKET_BALL);
    public static Item MUSKET = new GunItem(1, 4, 100, 15, musketSounds, 16F, MUSKET_BALL);

    //public static final Item FLINTLOCK_PISTOL = new GunItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item BLUNDERBEHEMOTH = new GunItem(800, 28, 480, 120, blunderbussSounds, 8F, BLUNDER_BALL);
    //public static final Item RIFLE = new GunItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    //public static final Item MUSKET = new GunItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "musket_ball"), MUSKET_BALL);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "blunder_ball"), BLUNDER_BALL);

        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "flintlock_pistol"), FLINTLOCK_PISTOL);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "blunderbuss"), BLUNDERBUSS);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "rifle"), RIFLE);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "musket"), MUSKET);

        //Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "flintlock_pistol"), FLINTLOCK_PISTOL);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "blunderbehemoth"), BLUNDERBEHEMOTH);
        //Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "rifle"), RIFLE);
        //Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "musket"), MUSKET);

        UniqueItemRegistry.CROSSBOW.addItemToRegistry(FLINTLOCK_PISTOL);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(BLUNDERBUSS);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(RIFLE);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(MUSKET);

        //UniqueItemRegistry.CROSSBOW.addItemToRegistry(FLINTLOCK_PISTOL);
        UniqueItemRegistry.CROSSBOW.addItemToRegistry(BLUNDERBEHEMOTH);
        //UniqueItemRegistry.CROSSBOW.addItemToRegistry(RIFLE);
        //UniqueItemRegistry.CROSSBOW.addItemToRegistry(MUSKET);
    }
}
