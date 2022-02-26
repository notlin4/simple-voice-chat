package de.maxhenkel.voicechat.gui.volume;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.gui.GameProfileUtils;
import de.maxhenkel.voicechat.gui.widgets.ListScreenEntryBase;
import de.maxhenkel.voicechat.voice.common.PlayerState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class PlayerVolumeEntry extends ListScreenEntryBase<PlayerVolumeEntry> {

    protected static final TranslationTextComponent SYSTEM_VOLUME = new TranslationTextComponent("message.voicechat.system_volume");
    protected static final ResourceLocation SYSTEM_VOLUME_ICON = new ResourceLocation(Voicechat.MODID, "textures/icons/system_volume.png");

    protected static final int SKIN_SIZE = 24;
    protected static final int PADDING = 4;
    protected static final int BG_FILL = ColorHelper.PackedColor.color(255, 74, 74, 74);
    protected static final int PLAYER_NAME_COLOR = ColorHelper.PackedColor.color(255, 255, 255, 255);

    protected final Minecraft minecraft;
    @Nullable
    protected final PlayerState state;
    protected final AdjustVolumeSlider volumeSlider;

    public PlayerVolumeEntry(@Nullable PlayerState state) {
        this.minecraft = Minecraft.getInstance();
        this.state = state;
        this.volumeSlider = new AdjustVolumeSlider(0, 0, 100, 20, state != null ? state.getUuid() : Util.NIL_UUID);
        this.children.add(volumeSlider);
    }

    @Override
    public void render(MatrixStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovered, float delta) {
        int skinX = left + PADDING;
        int skinY = top + (height - SKIN_SIZE) / 2;
        int textX = skinX + SKIN_SIZE + PADDING;
        int textY = top + (height - minecraft.font.lineHeight) / 2;

        AbstractGui.fill(poseStack, left, top, left + width, top + height, BG_FILL);

        if (state != null) {
            minecraft.getTextureManager().bind(GameProfileUtils.getSkin(state.getUuid()));
            AbstractGui.blit(poseStack, skinX, skinY, SKIN_SIZE, SKIN_SIZE, 8, 8, 8, 8, 64, 64);
            RenderSystem.enableBlend();
            AbstractGui.blit(poseStack, skinX, skinY, SKIN_SIZE, SKIN_SIZE, 40, 8, 8, 8, 64, 64);
            RenderSystem.disableBlend();
            minecraft.font.draw(poseStack, state.getName(), (float) textX, (float) textY, PLAYER_NAME_COLOR);
        } else {
            minecraft.getTextureManager().bind(SYSTEM_VOLUME_ICON);
            AbstractGui.blit(poseStack, skinX, skinY, SKIN_SIZE, SKIN_SIZE, 16, 16, 16, 16, 16, 16);
            minecraft.font.draw(poseStack, SYSTEM_VOLUME, (float) textX, (float) textY, PLAYER_NAME_COLOR);
        }

        volumeSlider.x = left + (width - volumeSlider.getWidth() - PADDING);
        volumeSlider.y = top + (height - volumeSlider.getHeight()) / 2;
        volumeSlider.render(poseStack, mouseX, mouseY, delta);
    }

    @Nullable
    public PlayerState getState() {
        return state;
    }
}
