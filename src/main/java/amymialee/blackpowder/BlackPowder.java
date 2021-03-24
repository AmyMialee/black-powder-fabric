package amymialee.blackpowder;

import amymialee.blackpowder.guns.BlackPowderGuns;
import amymialee.blackpowder.items.BlackPowderItems;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.ToIntFunction;

public class BlackPowder implements ModInitializer {
    public static final String MODID = "blackpowder";

    public static final Block TARGET_LAMP = new TargetLampBlock(FabricBlockSettings.of(Material.NETHER_WOOD).strength(1.0f).luminance(fullLight()));
    private static ToIntFunction<BlockState> fullLight() {
        return (blockState) -> (Boolean)blockState.get(Properties.LIT) ? 15 : 0;
    }

    public static BlackPowderConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(BlackPowderConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(BlackPowderConfig.class).getConfig();

        BlackPowderGuns.register();
        BlackPowderItems.register();

        Registry.register(Registry.BLOCK, new Identifier(MODID, "target_lamp"), TARGET_LAMP);
        Registry.register(Registry.ITEM, new Identifier(MODID, "target_lamp"),
                new BlockItem(TARGET_LAMP, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    }
}
