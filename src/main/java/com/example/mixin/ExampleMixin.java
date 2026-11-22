package com.example.antieffects.mixin;

import com.example.antieffects.AntiEffectsClient;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(method = "setupFog", at = @At("RETURN"))
    private void disableAllFog(Camera camera, FogType fogType, float renderDistance, boolean thickFog, float partialTick, CallbackInfoReturnable<FogData> cir) {
        if (AntiEffectsClient.noFogAndEffects) {
            FogData data = cir.getReturnValue();
            if (data != null) {
                data.start = 10000.0F;
                data.end = 10001.0F;
            }
        }
    }
}
