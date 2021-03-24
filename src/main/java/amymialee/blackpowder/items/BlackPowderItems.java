package amymialee.blackpowder.items;

import amymialee.blackpowder.BlackPowder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlackPowderItems {
    public static Item MUSKET_BALL = new BulletItem(new FabricItemSettings().group(ItemGroup.COMBAT));
    public static Item BLUNDER_BALL = new BulletItem(new FabricItemSettings().group(ItemGroup.COMBAT));

    public static Item GRIP = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static Item FIRING_MECHANISM = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static Item BARREL = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static Item RIFLED_BARREL = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "musket_ball"), MUSKET_BALL);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "blunder_ball"), BLUNDER_BALL);

        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "grip"), GRIP);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "firing_mechanism"), FIRING_MECHANISM);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "barrel"), BARREL);
        Registry.register(Registry.ITEM, new Identifier(BlackPowder.MODID, "rifled_barrel"), RIFLED_BARREL);
    }
}
