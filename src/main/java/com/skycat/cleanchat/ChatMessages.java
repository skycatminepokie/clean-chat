package com.skycat.cleanchat;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

/**
 * A class containing chat message and parts of chat messages that the mod displays.
 * @author skycatminepokie
 * @version 12/16/2020
 */
// TODO: Move to enums?
public class ChatMessages {
    //Move to enums?
    private static final ChatComponentText cleanChatTag = new ChatComponentText("[CleanChat]");
    private static final IChatComponent confirmReport = cleanChatTag.appendSibling(new ChatComponentText(" Confirm report?").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/report confirm"))));


}
