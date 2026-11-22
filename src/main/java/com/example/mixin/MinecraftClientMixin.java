package com.example.antieffects.mixin;

import com.example.antieffects.AntiEffectsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void forceEntityOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (AntiEffectsClient.forceGlowing && entity instanceof LivingEntity) {
            // Исключаем самого себя, чтобы не мешало от третьего лица
            if (entity != MinecraftClient.getInstance().player) {
                cir.setReturnValue(true);
            }
        }
    }
}
