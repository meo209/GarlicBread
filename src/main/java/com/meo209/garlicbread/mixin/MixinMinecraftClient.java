package com.meo209.garlicbread.mixin;

import com.meo209.garlicbread.Garlicbread;
import com.meo209.garlicbread.utils.PerformanceMonitor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Unique
    private long bootTimeStart = 0L;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/System;currentTimeMillis()J"))
    private void initInvoke(RunArgs args, CallbackInfo ci) {
        bootTimeStart = System.currentTimeMillis();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initTail(RunArgs args, CallbackInfo ci) {
        long bootTimeEnd = System.currentTimeMillis();
        long bootTime = (bootTimeEnd - bootTimeStart);
        PerformanceMonitor.INSTANCE.setBootTime(bootTime);
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void stop(CallbackInfo ci) {
        Garlicbread.Companion.stop();
    }
}