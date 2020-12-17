package com.skycat.cleanchat;
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
 * @version 12/16/2020
 */
public class CleanChat{
    public static final String NAME = "Clean Chat";
    public static final String MODID = "cleanchat";
    public static final String VERSION = "1.8.9-A1";


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        //Is this the right event?
        ClientCommandHandler.instance.registerCommand(new CleanChatCommand());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
}
