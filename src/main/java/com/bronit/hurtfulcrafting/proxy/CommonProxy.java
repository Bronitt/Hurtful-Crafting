package com.bronit.hurtfulcrafting.proxy;

import com.bronit.hurtfulcrafting.Config;
import com.bronit.hurtfulcrafting.HurtfulCrafting;
import com.bronit.hurtfulcrafting.handler.ItemHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        HurtfulCrafting.config = new Config(new File("config/" + HurtfulCrafting.Constants.MOD_NAME + "/general.cfg"));
        new ItemHandler();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
