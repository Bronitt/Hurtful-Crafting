package com.bronit.hurtfulcrafting.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlockPlaceEvent {

    @SubscribeEvent
    public static void blockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt = CraftingEvent.db.writeToNBT(nbt);
            if (((NBTTagCompound) nbt.getTag("players")).getKeySet().contains(
                    EntityPlayer.getUUID(
                            ((EntityPlayer) event.getEntity()).getGameProfile()).toString())) {
                event.setCanceled(true);
            }
        }
    }

}
