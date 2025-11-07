package com.bronit.hurtfulcrafting.event;

import com.bronit.hurtfulcrafting.Tags;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber(modid = Tags.MOD_ID)
public class PlayerTickEvent {

    private static int tick;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        NBTTagCompound nbt = new NBTTagCompound();
        CraftingEvent.db.writeToNBT(nbt);
        NBTTagCompound players = (NBTTagCompound) nbt.getTag("players");
        String uuid = EntityPlayer.getUUID(event.player.getGameProfile()).toString();
        if (players.getKeySet().contains(uuid)) {
            if (!event.player.isSpectator()) event.player.setGameType(GameType.SPECTATOR);
            if (tick == 20) {
                event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:blindness"), 500, 999999, false, true));
                tick = 0;
            } else tick++;
        }
    }

}