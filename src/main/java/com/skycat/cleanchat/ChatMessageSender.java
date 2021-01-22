package com.skycat.cleanchat;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;

/**
 * A class containing chat message and parts of chat messages that the mod displays.
 * @author skycatminepokie
 * @version 12/16/2020
 */

//After a lot of struggling, i found out that .appendSibling is a mutator method, not just an algorithm.
    //The lesson: MAKE COPIES BEFORE you append siblings to a reused ChatComponent

public class ChatMessageSender {
    @Getter private static final IChatComponent cleanChatTag = new ChatComponentText("[CleanChat] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
    @Getter private static final IChatComponent confirmReport = cleanChatTag.createCopy().appendSibling(new ChatComponentText("Confirm report?").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/report confirm"))));
    @Getter private static final IChatComponent textMainMenuHeader = cleanChatTag.createCopy().appendSibling(new ChatComponentText("Main Menu").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)));
    private static final String[] mainMenuCommandSuggestions = {"list", "listOn", "listOff"};
    private static final String[] mainMenuCommandDescriptions = {
            ": Lists all available chat filter settings (l, listAll).",
            ": Lists all active chat filter settings.",
            ": Lists all inactive chat filter settings."};
    private static final ChatStyle PLAIN_WHITE = new ChatStyle().setColor(EnumChatFormatting.WHITE);
    private static final ChatStyle FILTER_ENABLED = new ChatStyle().setColor(EnumChatFormatting.GREEN).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("This filter is enabled.")));
    private static final ChatStyle FILTER_DISABLED = new ChatStyle().setColor(EnumChatFormatting.RED).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("This filter is disabled.")));

    public static void displayTextMainMenu() {
        sendMessageToPlayer(textMainMenuHeader);
        sendMessageToPlayer(new ChatComponentText("Welcome to CleanChat! Use /cleanchat list to see all your filters."));
        sendMessageToPlayer(new ChatComponentText("Use /cleanchat setting create to start creating your own filters."));
        sendMessageToPlayer(new ChatComponentText("Created by skycatminepokie (skycatminepokie#3711)"));
        sendMessageToPlayer(new ChatComponentText("Feel free to contact me in game with questions or suggestions!"));
        sendMessageToPlayer(new ChatComponentText("Credits").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/skycatminepokie/clean-chat/blob/ac5a66646c3a0c1f340d173b0d9b63304c427f4d/CleanChatCredits")).setUnderlined(true).setColor(EnumChatFormatting.BLUE)));
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

    /**
     * Displays a chat with a list of filter settings to the player
     * @param listOn Show filter settings that are on
     * @param listOff Show filter settings that are off
     */
    public static void listFilterSettings(boolean listOn, boolean listOff) {
        ArrayList<ChatFilterSetting> settings = CleanChat.getChatHandler().getChatFilter().getSettings();
        for (ChatFilterSetting setting: settings) {
            if (listOn && setting.isEnabled()) {
                sendMessageToPlayer(cleanChatTag.createCopy().appendSibling(new ChatComponentText(setting.getDescription())).setChatStyle(FILTER_ENABLED.createDeepCopy().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cleanchat setting " + setting.getName() + " toggle"))));
            } else
            if (listOff && !setting.isEnabled()) {
                sendMessageToPlayer(cleanChatTag.createCopy().appendSibling(new ChatComponentText(setting.getDescription())).setChatStyle(FILTER_DISABLED.createDeepCopy().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cleanchat setting " + setting.getName() + " toggle"))));
            }
        }
        if (CleanChat.getChatHandler().getChatFilter().isEnabled() == false) {
            sendMessageToPlayer(cleanChatTag.createCopy().appendSibling((new ChatComponentText("Warning: Chat filter is currently disabled."))));
        }
    }
}

