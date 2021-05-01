package com.skycat.cleanchat;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CleanChat.MODID, name = CleanChat.NAME, version = CleanChat.VERSION)

/**
 * Main mod class for CleanChat.
 * @author skycatminepokie
 * @version 1/8/2021
 */
public class CleanChat {
    public static final String NAME = "Clean Chat";
    public static final String MODID = "cleanchat";
    public static final String VERSION = "1.8.9-A1";
    @Getter @Setter
    static ChatHandler chatHandler = new ChatHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        chatHandler.loadChatFilter();
        MinecraftForge.EVENT_BUS.register(chatHandler);
        //Is this the right event?
        ClientCommandHandler.instance.registerCommand(new CleanChatCommand());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}

}
