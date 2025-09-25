package com.bronit.hurtfulcrafting;

import com.bronit.hurtfulcrafting.HurtfulCrafting.Constants;
import com.bronit.hurtfulcrafting.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = Constants.MOD_ID,
        name = Constants.MOD_NAME,
        version = Constants.VERSION
)
public class HurtfulCrafting {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_NAME);
    public static Config config;
    public static String worldName;

    @SidedProxy(
            clientSide = "com.bronit.hurtfulcrafting.proxy.ClientProxy",
            serverSide = "com.bronit.hurtfulcrafting.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Instance
    public static HurtfulCrafting instance;

    public static ResourceLocation getIdentifier(final String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        worldName = event.getServer().getWorldName();
        LOGGER.info("{}   aaaaaaaaaaaaaaaaaaaaa\n{}",
                worldName,
                ((File) (FMLInjectionData.data()[6]))
                        .getAbsolutePath()
                        .replace(File.separatorChar, '/')
                        .replace("/.", ""));
    }

    public static class Constants {
        public static final String MOD_ID = "hurtfulcrafting";
        public static final String MOD_NAME = "Hurtful Crafting";
        public static final String VERSION = "1.0";

    }
}
