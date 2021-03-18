package amymialee.blackpowder;

import net.fabricmc.api.ModInitializer;

public class BlackPowder implements ModInitializer {
    public static final String MODID = "blackpowder";

    @Override
    public void onInitialize() {
        BlackPowderItems.register();
    }
}
