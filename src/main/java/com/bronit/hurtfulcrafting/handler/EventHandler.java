package com.bronit.hurtfulcrafting.handler;

import com.bronit.hurtfulcrafting.GhostPlayersData;
import com.bronit.hurtfulcrafting.HurtfulCrafting;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.GameType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

@EventBusSubscriber
public class EventHandler {

    public static GhostPlayersData db;

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public static void craftingEvent(PlayerEvent.ItemCraftedEvent event) {
        NBTTagCompound temp = new NBTTagCompound();
        temp = db.writeToNBT(temp);
        HurtfulCrafting.LOGGER.info("Keka " + ((NBTTagCompound) temp.getTag("players")).getKeySet());

        float health = event.player.getMaxHealth();
        if (health > 1f) {
            if (!(event.player.getHealth() < health)) {
                event.player.attackEntityFrom(DamageSource.MAGIC, HurtfulCrafting.config.damage);
            }
            event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health - HurtfulCrafting.config.damage);
        } else {
            event.player.setGameType(GameType.SURVIVAL);
            event.player.capabilities.disableDamage = true;
            event.player.capabilities.allowEdit = false;

            Map<Integer, ItemStack> inventory = new HashMap<>();

            InventoryPlayer inv = event.player.inventory;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                inventory.put(i, inv.getStackInSlot(i));
            }

            Map<Integer, ArrayList<String>> map = new HashMap<>();

            for (int i = 0; i < inventory.values().size(); i++) {
                ArrayList<String> item = new ArrayList<>();
                item.add(inventory.get(i).getDisplayName());
                item.add("" + inventory.get(i).getCount());
                item.add(inventory.get(i).getMetadata() + "");
                map.put(i, item);
            }

            HurtfulCrafting.LOGGER.info(map);

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

//            nbt = db.writeToNBT(nbt);
//            Set<String> set = ((NBTTagCompound) nbt.getTag("players")).getKeySet();
//            HurtfulCrafting.LOGGER.info(set);
//            MinecraftServer server = event.player.getServer();
//            if (server != null) {
//                for (String uuid : set) {
//                    GameProfile profile = server.getPlayerProfileCache().getProfileByUUID(UUID.fromString(uuid));
//                    HurtfulCrafting.LOGGER.info(profile.getName() + " gggggggggg");
//                    if (profile.getName().equals(event.player.getName())) {
//                        HurtfulCrafting.LOGGER.info("HAPPY");
//                        break;
//                    }
//                }
//            }

        }
    }

    @SubscribeEvent
    public void blockPlace(BlockEvent.EntityPlaceEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt = db.writeToNBT(nbt);
            NBTTagCompound players = (NBTTagCompound) nbt.getTag("players");
            for (int i = 0; i < players.getSize(); i++) {
                if (EntityPlayer.getUUID(((EntityPlayer) event.getEntity()).getGameProfile()).toString().equals(players.getKeySet().toArray()[i])) {
                    HurtfulCrafting.LOGGER.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                }
            }
        }
    }

}
