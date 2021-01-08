package com.skycat.cleanchat;

import com.google.gson.Gson;
import lombok.Getter;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//What's the "static" mean here?
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static com.skycat.cleanchat.MessageSource.*;

public class ChatHandler {
    private int messagesRemoved = 0;
    @Getter
    private ChatFilter chatFilter = new ChatFilter(true,
            new ChatFilterSetting[] {
                    new ChatFilterSetting("buying", PLAYER, true, "Ad (buying)", "buyAd"),
                    new ChatFilterSetting("selling", PLAYER, true, "Ad (selling)", "sellAd"),
                    new ChatFilterSetting("party me", PLAYER, true, "Ad (party me)", "partyAd"),
                    new ChatFilterSetting("Your Spirit Sceptre hit ", SERVER, true, "Spirit Sceptre damage message", "spiritSceptre"),
                    new ChatFilterSetting("§f §6sled into the lobby!", SERVER, true, "Lobby join (Holidays)", "lobbyJoinHolidays"),
                    new ChatFilterSetting(" has obtained Superboom TNT!", SERVER, true, "Teammate found Superboom TNT", "teammateFoundSuperboom"),
                    //new ChatFilterSetting("[WATCHDOG ANNOUNCEMENT]", SERVER, false, "Watchdog announcement header"),
                    //new ChatFilterSetting("Watchdog has banned.* players in the last 7 days.", SERVER, false, "Watchdog ban 7 days"),
                    //new ChatFilterSetting("Staff have banned an additional .* in the last 7 days.", SERVER, false, "Staff ban 7 days"),
                    //new ChatFilterSetting("Blacklisted modifications are a bannable offense!", SERVER, false, "Blacklisted mods warning")
            }
        );
    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (!(chatFilter.isMessageAllowed(event.message.getUnformattedText(), UNKNOWN_ALL))) {
            //If the message is blocked
            event.setCanceled(true);
            messagesRemoved++;
            System.out.println("Removed a message.");
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
            chatFilter = gson.fromJson(fileContents, ChatFilter.class);

        } catch (IOException e) {
            System.out.println("Cleanchat encountered an IOException while trying to load the chat filter: ");
            e.printStackTrace();
        }
    }
}
