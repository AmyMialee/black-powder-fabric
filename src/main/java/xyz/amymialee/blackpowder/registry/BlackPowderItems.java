package xyz.amymialee.blackpowder.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import xyz.amymialee.blackpowder.BlackPowder;
import xyz.amymialee.blackpowder.items.GunItem;

import java.util.ArrayList;

public class BlackPowderItems {
    public static final ArrayList<ArrayList<ItemStack>> MOD_ITEMS = new ArrayList<>();
    public static final ArrayList<ItemStack> ITEMS_MATERIALS = new ArrayList<>();
    public static final ArrayList<ItemStack> ITEMS_AMMO = new ArrayList<>();
    public static final ArrayList<ItemStack> ITEMS_GUNS = new ArrayList<>();
    public static final ArrayList<ItemStack> ITEMS_CREATIVE_GUNS = new ArrayList<>();
    //Materials
    public static Item GRIP = registerItem("grip", new Item(new FabricItemSettings()), ITEMS_MATERIALS);
    public static Item FIRING_MECHANISM = registerItem("firing_mechanism", new Item(new FabricItemSettings()), ITEMS_MATERIALS);
    public static Item BARREL = registerItem("barrel", new Item(new FabricItemSettings()), ITEMS_MATERIALS);
    public static Item RIFLED_BARREL = registerItem("rifled_barrel", new Item(new FabricItemSettings()), ITEMS_MATERIALS);
    //Ammo
    public static Item MUSKET_BALL = registerItem("musket_ball", new Item(new FabricItemSettings()), ITEMS_AMMO);
    public static Item BLUNDER_BALL = registerItem("blunder_ball", new Item(new FabricItemSettings()), ITEMS_AMMO);
    //Guns
    public static Item FLINTLOCK_PISTOL = registerItem("flintlock_pistol", new GunItem(BlackPowderGunEntries.ENTRY_FLINTLOCK_PISTOL, new FabricItemSettings()), ITEMS_GUNS);
    public static Item BLUNDERBUSS = registerItem("blunderbuss", new GunItem(BlackPowderGunEntries.ENTRY_BLUNDERBUSS, new FabricItemSettings()), ITEMS_GUNS);
    public static Item RIFLE = registerItem("rifle", new GunItem(BlackPowderGunEntries.ENTRY_RIFLE, new FabricItemSettings()), ITEMS_GUNS);
    public static Item MUSKET = registerItem("musket", new GunItem(BlackPowderGunEntries.ENTRY_MUSKET, new FabricItemSettings()), ITEMS_GUNS);

    public static Item FLINTLOCK_CARBINE = registerItem("flintlock_carbine", new GunItem(BlackPowderGunEntries.ENTRY_FLINTLOCK_CARBINE, new FabricItemSettings()), ITEMS_CREATIVE_GUNS);
    public static Item BLUNDERBEHEMOTH = registerItem("blunderbehemoth", new GunItem(BlackPowderGunEntries.ENTRY_BLUNDERBEHEMOTH, new FabricItemSettings()), ITEMS_CREATIVE_GUNS);
    public static Item RESOLUTE_RIFLE = registerItem("resolute_rifle", new GunItem(BlackPowderGunEntries.ENTRY_RESOLUTE_RIFLE, new FabricItemSettings()), ITEMS_CREATIVE_GUNS);
    public static Item BOUNDLESS_MUSKET = registerItem("boundless_musket", new GunItem(BlackPowderGunEntries.ENTRY_BOUNDLESS_MUSKET, new FabricItemSettings()), ITEMS_CREATIVE_GUNS);

    public static void init() {
        MOD_ITEMS.add(ITEMS_MATERIALS);
        MOD_ITEMS.add(ITEMS_AMMO);
        MOD_ITEMS.add(ITEMS_GUNS);
        MOD_ITEMS.add(ITEMS_CREATIVE_GUNS);
        FabricItemGroupBuilder.create(BlackPowder.id("blackpowder_group")).icon(BlackPowderItems::getRecipeKindIcon).appendItems((i) -> {
            for (ArrayList<ItemStack> stacks : MOD_ITEMS) {
                i.addAll(stacks);
                while (i.size() % 9 != 0) {
                    i.add(ItemStack.EMPTY);
                }
            }
        }).build();
    }

    @SafeVarargs
    public static Item registerItem(String name, Item item, ArrayList<ItemStack> ... group) {
        Registry.register(Registry.ITEM, BlackPowder.id(name), item);
        for (ArrayList<ItemStack> list : group) {
            list.add(new ItemStack(item));
        }
        return item;
    }

    public static ItemStack getRecipeKindIcon() {
        return new ItemStack(FLINTLOCK_PISTOL);
    }
}