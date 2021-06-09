package com.skycat.cleanchat.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiButton;

public class OnOffButton extends GuiButton {
    @Getter
    @Setter
    private boolean on = true;

    public OnOffButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public OnOffButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        on = !on;
        this.displayString = String.valueOf(on);
        super.mouseReleased(mouseX, mouseY);
    }
}
