package com.bronit.hurtfulcrafting.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RightClickBlockEvent {

    @SubscribeEvent
    public static void rightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        NBTTagCompound nbt = new NBTTagCompound();
        CraftingEvent.db.writeToNBT(nbt);
        if (((NBTTagCompound) nbt.getTag("players"))
                .getKeySet().contains(EntityPlayer.getUUID(
                        ((EntityPlayer) event.getEntity())
                                .getGameProfile()).toString())) {
            event.setCanceled(true);
        }
    }

}
