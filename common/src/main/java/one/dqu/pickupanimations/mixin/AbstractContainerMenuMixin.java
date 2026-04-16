package one.dqu.pickupanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import one.dqu.pickupanimations.animation.AnimationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @Shadow
    private int quickcraftStatus;

    @Inject(method = "setCarried", at = @At("HEAD"))
    private void setCarried(ItemStack itemStack, CallbackInfo ci) {
        if (quickcraftStatus != 0) return;

        if (!(Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> screen)) return;
        AbstractContainerScreenAccessor acc = (AbstractContainerScreenAccessor) screen;
        Slot slot = acc.clickedSlot();
        if (slot == null) return;

        if (!itemStack.isEmpty()) {
            AnimationManager.INSTANCE.onPickup(slot, acc.leftPos(), acc.topPos());
        } else {
            AnimationManager.INSTANCE.onPlace(slot, acc.leftPos(), acc.topPos());
        }
    }
}
