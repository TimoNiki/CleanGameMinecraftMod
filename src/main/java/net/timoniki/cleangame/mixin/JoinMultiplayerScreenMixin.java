package net.timoniki.cleangame.mixin;

import net.timoniki.cleangame.CleanGameConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JoinMultiplayerScreen.class)
public abstract class JoinMultiplayerScreenMixin extends Screen {

    protected JoinMultiplayerScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        // Координаты кнопки в верхнем левом углу (X=10, Y=10)
        int x = 10;
        int y = 10;
        int width = 120;
        int height = 20;

        // Создаем кнопку по новому стандарту 1.21+
        Button antiSpamButton = Button.builder(
                getButtonText(), 
                button -> {
                    // Переключаем true/false в конфигурации
                    CleanGameConfig.isAntiSpamEnabled = !CleanGameConfig.isAntiSpamEnabled;
                    // Обновляем текст кнопки прямо при нажатии
                    button.setMessage(getButtonText());
                }
        ).bounds(x, y, width, height).build();
        this.addRenderableWidget(antiSpamButton);
    }

    @Unique
    private Component getButtonText() {
        if (CleanGameConfig.isAntiSpamEnabled) {
            return Component.literal("Защита LAN: §aВКЛ"); // Зеленый текст
        } else {
            return Component.literal("Защита LAN: §cВЫКЛ"); // Красный текст
        }
    }
}
