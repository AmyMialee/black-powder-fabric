package amymialee.blackpowder.client;

import amymialee.blackpowder.BlackPowderItems;
import amymialee.blackpowder.guns.GunItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BlackPowderClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    }

    static {
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.FLINTLOCK_PISTOL, new Identifier("charged"), (gun_flintlock_0, gun_flintlock_1, gun_flintlock_2)
                -> gun_flintlock_2 != null && GunItem.isCharged(gun_flintlock_0) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.BLUNDERBUSS, new Identifier("charged"), (gun_blunderbuss_0, gun_blunderbuss_1, gun_blunderbuss_2)
                -> gun_blunderbuss_2 != null && GunItem.isCharged(gun_blunderbuss_0) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.RIFLE, new Identifier("charged"), (gun_rifle_0, gun_rifle_1, gun_rifle_2)
                -> gun_rifle_2 != null && GunItem.isCharged(gun_rifle_0) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.MUSKET, new Identifier("charged"), (gun_musket_0, gun_musket_1, gun_musket_2)
                -> gun_musket_2 != null && GunItem.isCharged(gun_musket_0) ? 1.0F : 0.0F);

        FabricModelPredicateProviderRegistry.register(BlackPowderItems.FLINTLOCK_CARBINE, new Identifier("charged"), (gun_flintlock_0, gun_flintlock_1, gun_flintlock_2)
                -> gun_flintlock_2 != null && GunItem.isCharged(gun_flintlock_0) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.BLUNDERBEHEMOTH, new Identifier("charged"), (gun_blunderbuss_0, gun_blunderbuss_1, gun_blunderbuss_2)
                -> gun_blunderbuss_2 != null && GunItem.isCharged(gun_blunderbuss_0) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.RESOLUTE_RIFLE, new Identifier("charged"), (gun_rifle_0, gun_rifle_1, gun_rifle_2)
                -> gun_rifle_2 != null && GunItem.isCharged(gun_rifle_0) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(BlackPowderItems.BOUNDLESS_MUSKET, new Identifier("charged"), (gun_musket_0, gun_musket_1, gun_musket_2)
                -> gun_musket_2 != null && GunItem.isCharged(gun_musket_0) ? 1.0F : 0.0F);
    }
}
