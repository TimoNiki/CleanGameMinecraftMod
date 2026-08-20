package com.example.cleangame.mixin;

import com.example.cleangame.CleanGameConfig;
import net.minecraft.client.gui.screen.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JoinMultiplayerScreen.class)
public class JoinMultiplayerScreenMixin extends Screen {

    protected JoinMultiplayerScreenMixin(Text title) {
        super(title);
    }

    
    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {  
        int buttonX = 10;
        int buttonY = 10;
        int width = 120;
        int height = 20;

        ButtonWidget antiSpamButton = ButtonWidget.builder(
                getButtonText(), 
                button -> {
                    CleanGameConfig.isAntiSpamEnabled = !CleanGameConfig.isAntiSpamEnabled;
                    button.setMessage(getButtonText());
                }
        ).dimensions(buttonX, buttonY, width, height).build();
        this.addDrawableChild(antiSpamButton);
    }
    @Unique
    private Text getButtonText() {
        if (CleanGameConfig.isAntiSpamEnabled) {
            return Text.literal("Защита LAN: §aВКЛ");
        } else {
            return Text.literal("Защита LAN: §cВЫКЛ");
        }
    }
}
