package com.skycat.cleanchat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//What's the "static" mean here?
import static com.skycat.cleanchat.MessageSource.*;

public class ChatHandler {
    //Outdated
    /*String[] filteredPlayerMessages = {
            "Buying.*?\\/?p"};
    String[] filteredServerMessages = {
            "",
            ""
    };
    */
    private int messagesRemoved = 0;
    ChatFilter chatFilter = new ChatFilter(true,
            new ChatFilterSetting[] {
                    new ChatFilterSetting("buying", PLAYER, true),
                    new ChatFilterSetting("selling", PLAYER, true),
                    new ChatFilterSetting("party me", PLAYER, true)
            }
        );
    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        /*
        String message = event.message.getUnformattedText();
        for(String s:filteredWords) {
            //CURRENT ISSUE: Some things that are meant to be filtered include regex special characters, which causes a failure.
            if (message.toLowerCase().matches(("[\\s\\S]*?" + s + "[\\s\\S]*?"))) {
                event.setCanceled(true);
                messagesRemoved++;
                System.out.println("Removed the following message: " + message);
                System.out.println("REMOVED "+messagesRemoved+ " MESSAGES SO FAR");

                break;
            }
        }
        */
        if (!(chatFilter.isMessageAllowed(event.message.getUnformattedText(), UNKNOWN_ALL))) {
            //If the message is blocked
            event.setCanceled(true);
            messagesRemoved++;
            System.out.println("Removed a message.");
        }
    }
}
