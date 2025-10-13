package com.bronit.hurtfulcrafting;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import org.jetbrains.annotations.NotNull;

public class GhostPlayersData extends WorldSavedData {

    private static final String DATA_NAME = HurtfulCrafting.Constants.MOD_ID + "_GhostPlayers";
    public NBTTagCompound players = new NBTTagCompound();

    public GhostPlayersData() {
        super(DATA_NAME);
    }

    public GhostPlayersData(String str) {
        super(str);
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound nbt) {
        this.players = nbt.getCompoundTag("players");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        compound.setTag("players", this.players);
        return compound;
    }

    public static GhostPlayersData get(World world) {
        MapStorage storage = world.getMapStorage();
        if(storage == null) throw new RuntimeException(new NullPointerException());
        GhostPlayersData instance = (GhostPlayersData) storage.getOrLoadData(GhostPlayersData.class, DATA_NAME);

        if (instance == null) {
            instance = new GhostPlayersData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

}
