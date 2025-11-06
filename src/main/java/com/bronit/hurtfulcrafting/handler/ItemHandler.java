package com.bronit.hurtfulcrafting.handler;

import com.bronit.hurtfulcrafting.item.Healer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemHandler {

    public static Item healer;

    public ItemHandler() {
        healer = new Healer();
        ForgeRegistries.ITEMS.register(healer);
    }

}
