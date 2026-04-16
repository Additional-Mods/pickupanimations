package one.dqu.pickupanimations.animation;

import net.minecraft.util.Mth;

public record SlotAnimation(
        long startTime,
        float startScale,
        float endScale,
        float startOffsetX,
        float startOffsetY
) {
    public SlotAnimation(float startScale, float endScale, float startOffsetX, float startOffsetY) {
        this(System.currentTimeMillis(), startScale, endScale, startOffsetX, startOffsetY);
    }

    private float t() {
        float t = Mth.clamp((System.currentTimeMillis() - startTime) / 100f, 0f, 1f);
        return t < 0.5f ? 2*t*t : -1+(4-2*t)*t;
    }

    public float scale() {
        return Mth.lerp(t(), startScale, endScale);
    }

    public float offsetX() {
        return Mth.lerp(t(), startOffsetX, 0f);
    }

    public float offsetY() {
        return Mth.lerp(t(), startOffsetY, 0f);
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= 100f;
    }
}
