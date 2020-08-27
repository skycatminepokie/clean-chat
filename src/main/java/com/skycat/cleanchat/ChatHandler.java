package com.skycat.cleanchat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {
    String[] filteredWords = {"p me","visit me","<!>","<>","ending soon"};
    int messagesRemoved = 0;
    @SubscribeEvent
    public void onChatMessageRecived(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for(String s:filteredWords) {
            if (message.toLowerCase().contains(s)) {
                event.setCanceled(true);
                messagesRemoved++;
                System.out.println("REMOVED "+messagesRemoved+ " MESSAGES SO FAR");
                break;
            }
        }
    }
}
