package com.skycat.cleanchat;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * A class containing chat message and parts of chat messages that the mod displays.
 * @author skycatminepokie
 * @version 12/16/2020
 */

public class ChatMessageSender {
    @Getter private static final ChatComponentText cleanChatTag = new ChatComponentText("[CleanChat]");
    @Getter private static final IChatComponent confirmReport = cleanChatTag.appendSibling(new ChatComponentText(" Confirm report?").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/report confirm"))));
    @Getter private static final IChatComponent textMainMenuHeader = cleanChatTag.appendSibling(new ChatComponentText(" Main Menu").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)));
    private static final String[] mainMenuCommandSuggestions = {"list", "listOn", "listOff"};
    private static final String[] mainMenuCommandDescriptions = {
            ": Lists all available chat filter settings (l, listAll).",
            ": Lists all active chat filter settings.",
            ": Lists all inactive chat filter settings."};

    public static void displayTextMainMenu() {
        sendMessageToPlayer(textMainMenuHeader);
        for(int i = 0; i < mainMenuCommandSuggestions.length; i++) {
            //TODO: Clean this thing up. It looks horrible XD
            sendMessageToPlayer(formatForMenuClickable(mainMenuCommandSuggestions[i]).appendSibling(new ChatComponentText(mainMenuCommandDescriptions[i]).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW))));
        }
    }

    /**
     * Sends a message to the player. Makes it a bit easier than retyping it all the time.
     * @param message The message to send.
     */
    public static void sendMessageToPlayer(IChatComponent message){
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(message);
    }

    private static IChatComponent formatForMenuClickable(String str) {
        return new ChatComponentText(str).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW).setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/cleanchat " + str)));
    }
}

