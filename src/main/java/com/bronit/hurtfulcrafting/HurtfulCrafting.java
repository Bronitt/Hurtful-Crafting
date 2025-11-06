package com.bronit.hurtfulcrafting;

import com.bronit.hurtfulcrafting.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.VERSION
)
public class HurtfulCrafting {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    public static Config config;

    @SidedProxy(
            clientSide = "com.bronit.hurtfulcrafting.proxy.ClientProxy",
            serverSide = "com.bronit.hurtfulcrafting.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Instance
    public static HurtfulCrafting instance;

    public static ResourceLocation getIdentifier(final String name) {
        return new ResourceLocation(Tags.MOD_ID, name);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        proxy.onServerStart(event);
    }

}
