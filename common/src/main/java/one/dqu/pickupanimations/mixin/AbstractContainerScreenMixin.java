package one.dqu.pickupanimations.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import one.dqu.pickupanimations.animation.AnimationManager;
import one.dqu.pickupanimations.animation.SlotAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void onMouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl, CallbackInfoReturnable<Boolean> cir) {
        AnimationManager.INSTANCE.lastMouseX = (float) mouseButtonEvent.x();
        AnimationManager.INSTANCE.lastMouseY = (float) mouseButtonEvent.y();
    }

    @Inject(method = "renderFloatingItem", at = @At("HEAD"))
    private void startRenderFloating(GuiGraphics guiGraphics, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
        SlotAnimation anim = AnimationManager.INSTANCE.getCursor();
        float scale = anim == null ? 1f : anim.scale();
        float ox = anim == null ? 0f : anim.offsetX();
        float oy = anim == null ? 0f : anim.offsetY();

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(i + 8 + ox, j + 8 + oy);
        guiGraphics.pose().scale(scale, scale);
        guiGraphics.pose().translate(-i - 8, -j - 8);
    }

    @Inject(method = "renderFloatingItem", at = @At("RETURN"))
    private void endRenderFloating(GuiGraphics guiGraphics, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
        guiGraphics.pose().popMatrix();
    }

    @Inject(method = "renderSlot", at = @At("HEAD"))
    private void startRenderSlot(GuiGraphics guiGraphics, Slot slot, int i, int j, CallbackInfo ci) {
        SlotAnimation anim = AnimationManager.INSTANCE.getSlot(slot);
        float scale = anim == null ? 1f : anim.scale();
        float ox = anim == null ? 0f : anim.offsetX();
        float oy = anim == null ? 0f : anim.offsetY();

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(slot.x + 8 + ox, slot.y + 8 + oy);
        guiGraphics.pose().scale(scale, scale);
        guiGraphics.pose().translate(-slot.x - 8, -slot.y - 8);
    }

    @Inject(method = "renderSlot", at = @At("RETURN"))
    private void endRenderSlot(GuiGraphics guiGraphics, Slot slot, int i, int j, CallbackInfo ci) {
        guiGraphics.pose().popMatrix();
    }
}