package amymialee.blackpowder.mixin;

import com.oroarmor.multiitemlib.api.UniqueItemRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Redirect(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean getHandRenderType(ItemStack stack, Item item, ClientPlayerEntity player) {
        return UniqueItemRegistry.BOW.isItemInRegistry(stack.getItem()) || UniqueItemRegistry.CROSSBOW.isItemInRegistry(stack.getItem());

    }
}
