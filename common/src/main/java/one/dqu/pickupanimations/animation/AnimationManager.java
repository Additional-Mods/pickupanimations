package one.dqu.pickupanimations.animation;

import net.minecraft.world.inventory.Slot;

import java.util.IdentityHashMap;
import java.util.Map;

public class AnimationManager {
    public static final AnimationManager INSTANCE = new AnimationManager();

    private final Map<Slot, SlotAnimation> slots = new IdentityHashMap<>();
    private SlotAnimation cursor = null;

    public float lastMouseX, lastMouseY;

    public void onPickup(Slot slot, int leftPos, int topPos) {
        float ox = (leftPos + slot.x + 8) - lastMouseX;
        float oy = (topPos + slot.y + 8) - lastMouseY;
        cursor = new SlotAnimation(1f, 1.5f, ox, oy);
    }

    public void onPlace(Slot slot, int leftPos, int topPos) {
        cursor = null;
        float ox = (lastMouseX - leftPos) - (slot.x + 8);
        float oy = (lastMouseY - topPos) - (slot.y + 8);
        slots.put(slot, new SlotAnimation(1.5f, 1f, ox, oy));
    }

    public SlotAnimation getSlot(Slot slot) {
        SlotAnimation anim = slots.get(slot);
        if (anim == null) return null;
        if (anim.isFinished()) {
            slots.remove(slot);
            return null;
        }
        return anim;
    }

    public SlotAnimation getCursor() {
        return cursor;
    }
}
