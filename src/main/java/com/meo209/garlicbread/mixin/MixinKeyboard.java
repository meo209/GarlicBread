package com.meo209.garlicbread.mixin;

import com.github.meo209.keventbus.EventBus;
import com.meo209.garlicbread.events.KeyPressEvent;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At("TAIL"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) {

            EventBus.Companion.global().post(new KeyPressEvent(key, modifiers));

            /*
            if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                MinecraftClient.getInstance().setScreen(Garlicbread.Companion.getTERMINAL_SCREEN());
            }
             */

        }
    }

}
