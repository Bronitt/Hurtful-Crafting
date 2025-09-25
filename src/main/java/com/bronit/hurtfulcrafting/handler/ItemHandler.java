package com.bronit.hurtfulcrafting.handler;

import com.bronit.hurtfulcrafting.item.Healer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemHandler {

    public static Item healer;

    public ItemHandler() {
        healer = new Healer();

    }

    public static <T extends Item> T registerItem(T item, ResourceLocation rl) {
        item.setRegistryName(rl);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

}
