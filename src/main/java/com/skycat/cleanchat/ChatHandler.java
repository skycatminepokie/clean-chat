package com.skycat.cleanchat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {
    String[] filteredWords = {"p me","visit me","<!>","<>","ending soon","-=-=", "[!]", "will show pots", "~~", "****", "!!!!!!!", "Paying for", "/.ah", "on my ah", "in my ah", "go quick", "selling", "buying", "want to buy"};
    int messagesRemoved = 0;
    @SubscribeEvent
    public void onChatMessageRecived(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for(String s:filteredWords) {
            if (message.toLowerCase().contains(s)) {
                event.setCanceled(true);
                messagesRemoved++;
                System.out.println("Removed the following message: " + message);
                System.out.println("REMOVED "+messagesRemoved+ " MESSAGES SO FAR");
                break;
            }
        }
    }
}
