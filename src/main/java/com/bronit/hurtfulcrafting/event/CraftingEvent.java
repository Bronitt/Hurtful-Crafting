package com.bronit.hurtfulcrafting.event;

import com.bronit.hurtfulcrafting.GhostPlayersData;
import com.bronit.hurtfulcrafting.HurtfulCrafting;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.*;

@EventBusSubscriber
public class CraftingEvent {

    public static GhostPlayersData db;

    @SubscribeEvent
    public static void craftingEvent(PlayerEvent.ItemCraftedEvent event) {

        float health = event.player.getMaxHealth();
        if (health > 1f) {
            if (!(event.player.getHealth() < health)) {
                event.player.attackEntityFrom(DamageSource.MAGIC, HurtfulCrafting.config.damage);
            }
            event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health - HurtfulCrafting.config.damage);
        } else {
            event.player.setGameType(GameType.SPECTATOR);


            NBTTagCompound nbt = new NBTTagCompound();
            nbt = db.writeToNBT(nbt);
            NBTTagCompound playersMap = (NBTTagCompound) nbt.getTag("players");
            NBTTagCompound playerPos = new NBTTagCompound();
            playerPos.setInteger("x", event.player.getPosition().getX());
            playerPos.setInteger("y", event.player.getPosition().getY());
            playerPos.setInteger("z", event.player.getPosition().getZ());
            playersMap.setTag(EntityPlayer.getUUID(event.player.getGameProfile()).toString(), playerPos);
            nbt.setTag("players", playersMap);
            db.readFromNBT(nbt);
            db.markDirty();

        }
    }

}
