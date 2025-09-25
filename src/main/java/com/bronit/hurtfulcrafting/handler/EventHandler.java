package com.bronit.hurtfulcrafting.handler;

import com.bronit.hurtfulcrafting.HurtfulCrafting;
import com.bronit.hurtfulcrafting.HurtfulCrafting.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EventHandler {

    private static final Map<UUID, BlockPos> db = new HashMap<>();
    public static final String dbPath = "saves/" + HurtfulCrafting.worldName + "/data/hurtfulcrafting/db.json";

    @SubscribeEvent
    public static void craftingEvent(PlayerEvent.ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        float health = player.getMaxHealth();
        if (health > 1f) {
            if (!(player.getHealth() < health)) {
                player.attackEntityFrom(DamageSource.MAGIC, HurtfulCrafting.config.damage);
            }
            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health - HurtfulCrafting.config.damage);
        } else {
            player.setGameType(GameType.ADVENTURE);
            PlayerCapabilities capabilities = player.capabilities;
            capabilities.disableDamage = true;
            db.put(EntityPlayer.getUUID(player.getGameProfile()), player.getPosition());
        }
        write(UUID.randomUUID(), new BlockPos(0, 0, 0));
    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent event) {

        if (db.get(EntityPlayer.getUUID(((EntityPlayer) event.getEntity()).getGameProfile())) != null) {
            event.getEntityPlayer().closeScreen();
        }

    }

//    private static Map<UUID, BlockPos> read() {
//        GsonBuilder bulder = new GsonBuilder();
//        Gson gson = bulder.create();
//        try (FileReader reader = new FileReader(Constants.DB_FILE)) {
//
//            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
//            JsonObject playersObject = jsonObject.getAsJsonObject("players");
//            Map<UUID, BlockPos> playersMap = new HashMap<>();
//
//            playersObject.entrySet().forEach(entry ->
//                    playersMap.put(UUID.fromString(entry.getKey()), new BlockPos(entry.getValue()))
//            );
//            db = playersMap;
//            return db;
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return db;
//    }

    private static void write(UUID uuid, BlockPos playerPos) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
//        Map<UUID, BlockPos> map = read();
//        map.put(uuid, playerPos);

        File file = new File(dbPath);
        if (!file.exists()) file.mkdir();
        try (FileWriter writer = new FileWriter(file)) {

            JsonObject jsonObject = new JsonObject();

//            jsonObject.add("players", gson.toJsonTree(map));
//            gson.toJson(jsonObject);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
