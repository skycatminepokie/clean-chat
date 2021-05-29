package com.skycat.cleanchat;

import com.google.gson.Gson;
import lombok.Getter;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//What's the "static" mean here?
import static com.skycat.cleanchat.MessageSource.*;

public class ChatHandler {
    private int messagesRemoved = 0;
    ArrayList<ChatFilterSettingGroup> chatFilterSettingGroups = new ArrayList<ChatFilterSettingGroup>();
    @Getter
    private ChatFilter chatFilter = new ChatFilter(true,
            chatFilterSettingGroups
    );

    public ChatHandler() {
        chatFilterSettingGroups.add(new ChatFilterSettingGroup(true, "Ads",
                new ChatFilterSetting("buying", PLAYER, true, "Ad (buying)", "buyAd"),
                new ChatFilterSetting("selling", PLAYER, true, "Ad (selling)", "sellAd"),
                new ChatFilterSetting("party me", PLAYER, true, "Ad (party me)", "partyAd"),
                new ChatFilterSetting(">>>>", PLAYER, true, "Ad", "rightChevronAd"),
                new ChatFilterSetting("<<<<", PLAYER, true, "Ad", "leftChevronAd"),
                new ChatFilterSetting("[!]", PLAYER, true, "Ad", "exclaimAd"),
                new ChatFilterSetting("coins fast", PLAYER, true, "Get coins fast scam", "fastCoinScam"),
                new ChatFilterSetting("ending soon", PLAYER, true, "ending soon", "lmaoToLol", "lol"),
                new ChatFilterSetting("go quick", PLAYER, true, "go quick", "goQuick"),
                new ChatFilterSetting("bid", PLAYER, true, "bid", "bidAd")));

        chatFilterSettingGroups.add(new ChatFilterSettingGroup(true, "Profanity Filter",
                new ChatFilterSetting("wtf", PLAYER, true, "What the", "whatThe", "wot"),
                new ChatFilterSetting("wth", PLAYER, true, "What the huh", "whatTheHuh", "wot"),
                new ChatFilterSetting("lamo", PLAYER, true , "lamo to lol", "lamoToLol", "lol"),
                new ChatFilterSetting("lmao", PLAYER, true , "lmao to lol", "lmaoToLol", "lol")));

        chatFilterSettingGroups.add(new ChatFilterSettingGroup(true, "Annoyances",
                new ChatFilterSetting("Your Spirit Sceptre hit ", SERVER, true, "Spirit Sceptre damage message", "spiritSceptre"),
                new ChatFilterSetting("§f §6sled into the lobby!", SERVER, true, "Lobby join (Holidays)", "lobbyJoinHolidays"),
                new ChatFilterSetting(" has obtained Superboom TNT!", SERVER, true, "Teammate found Superboom TNT", "teammateFoundSuperboom"),
                new ChatFilterSetting("flex", PLAYER, false, "flex", "flex", "flapjacks"),
                new ChatFilterSetting("Please wait a little before using that!", SERVER, false, "Please wait a little bit", "waitABit"),
                new ChatFilterSetting(" §b>§c>§a>§r §6\\[MVP", SERVER, true, "MVP++ Lobby join", "mvpPlusPlusJoin"),
                new ChatFilterSetting("teh", PLAYER, false, "teh to the (does not work)", "tehToThe", new String[] {"the"}, ChatFilterSettingFlag.REPLACEMENT, ChatFilterSettingFlag.WHOLE_WORD_ONLY)
                ));

        chatFilterSettingGroups.add(new ChatFilterSettingGroup(true, "Custom EZ",
                new ChatFilterSetting("Your personality shines brighter than the sun!", PLAYER, false, "EZ Personality", "ezPersonality", "Stop! Hammer time."),
                new ChatFilterSetting("Does anybody else really like Rick Astley?", PLAYER, false, "EZ Rick", "ezRick", "Does anybody else really like french fries?"),
                new ChatFilterSetting("Pineapple doesn't go on pizza!", PLAYER, false, "Pineapple pizza truth", "pineappleGoesOnPizza", "Pineapple goes on pizza!")
                ));

                //new ChatFilterSetting("[WATCHDOG ANNOUNCEMENT]", SERVER, false, "Watchdog announcement header"),
                //new ChatFilterSetting("Watchdog has banned.* players in the last 7 days.", SERVER, false, "Watchdog ban 7 days"),
                //new ChatFilterSetting("Staff have banned an additional .* in the last 7 days.", SERVER, false, "Staff ban 7 days"),
                //new ChatFilterSetting("Blacklisted modifications are a bannable offense!", SERVER, false, "Blacklisted mods warning")
    }


    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (!(chatFilter.isMessageAllowed(event.message.getUnformattedText(), UNKNOWN_ALL))) {
            //If the message is blocked or modified
            String modifiedMessage = chatFilter.modifyChatMessage(event.message.getFormattedText());
            if (modifiedMessage.equals(event.message.getFormattedText())) {
                event.setCanceled(true);
                messagesRemoved++;
                System.out.println("Removed a message.");
            } else {
                event.message = new ChatComponentText(modifiedMessage);
            }
        }
    }

    public void saveChatFilter() {
        try {
            Gson gson = new Gson();
            File saveFile = new File("cleanchatFilter.txt");
            saveFile.createNewFile(); //Won't make a new file if it already exists
            PrintWriter printWriter = new PrintWriter(saveFile);
            printWriter.write(gson.toJson(chatFilter));
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Cleanchat encountered an IOException while trying to save the chat filter: ");
            e.printStackTrace();
        }
    }

    /**
     * Loads the chat filter from cleanchatFilter.txt
     */
    public void loadChatFilter() {
        try {
            Gson gson = new Gson();
            Scanner saveFile = new Scanner(new File("cleanchatFilter.txt"));
            String fileContents = "";
            while (saveFile.hasNextLine()) {
                fileContents = fileContents + saveFile.nextLine();
            }
            ChatFilter newChatFilter = gson.fromJson(fileContents, ChatFilter.class);

            while (true) {
                // TODO: More efficient way to wait?
                if (!newChatFilter.isFullyCreated()) {
                    continue;
                }
                chatFilter = newChatFilter;
                break;
            }



        } catch (IOException e) {
            System.out.println("Cleanchat encountered an IOException while trying to load the chat filter: ");
            e.printStackTrace();
        }
    }
}
