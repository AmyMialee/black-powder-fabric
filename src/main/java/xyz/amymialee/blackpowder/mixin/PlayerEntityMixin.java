package xyz.amymialee.blackpowder.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.blackpowder.items.GunItem;
import xyz.amymialee.blackpowder.util.IPlayerEntityHolding;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerEntityHolding {
    @Unique public boolean blackPowder$Held = false;
    @Unique private int blackPowder$HeldTime = 0;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isUsingSpyglass", at = @At("HEAD"), cancellable = true)
    public void blackPowder$gunScopes(CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.getMainHandStack();
        if (this.getMainHandStack().getItem() instanceof GunItem gunItem) {
            if (gunItem.shouldScope(itemStack)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void blackPowder$tick(CallbackInfo ci) {
        if (this.blackPowder$Held) {
            this.blackPowder$HeldTime++;
        }
    }

    @Override
    public void blackPowder$onPress() {

    }

    @Override
    public void blackPowder$setHeld(boolean held) {
        this.blackPowder$HeldTime = 0;
        this.blackPowder$Held = held;
        if (held) {
            this.blackPowder$onPress();
        }
    }

    @Override
    public boolean blackPowder$getHeld() {
        return this.blackPowder$Held;
    }

    @Override
    public void blackPowder$setHeldTime(int held) {
        this.blackPowder$HeldTime = held;
    }

    @Override
    public int blackPowder$getHeldTime() {
        return this.blackPowder$HeldTime;
    }
}