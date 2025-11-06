package com.bronit.hurtfulcrafting.proxy;

import com.bronit.hurtfulcrafting.Config;
import com.bronit.hurtfulcrafting.GhostPlayersData;
import com.bronit.hurtfulcrafting.HurtfulCrafting;
import com.bronit.hurtfulcrafting.Tags;
import com.bronit.hurtfulcrafting.event.CraftingEvent;
import com.bronit.hurtfulcrafting.handler.CommandHandler;
import com.bronit.hurtfulcrafting.handler.ItemHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        HurtfulCrafting.config = new Config(new File("config/" + Tags.MOD_NAME + "/general.cfg"));
        new ItemHandler();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void onServerStart(FMLServerStartingEvent event) {
        CraftingEvent.db = GhostPlayersData.get(event.getServer().getEntityWorld());
        new CommandHandler(event);
    }
}
