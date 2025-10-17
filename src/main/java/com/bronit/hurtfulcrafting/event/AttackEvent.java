package com.bronit.hurtfulcrafting.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class AttackEvent {

    @SubscribeEvent
    public static void attackEvent(AttackEntityEvent event) {
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