package com.bronit.hurtfulcrafting.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

import static com.bronit.hurtfulcrafting.event.CraftingEvent.db;

public class CommandArise extends CommandBase {

    public static final String NAME = "arise";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + NAME;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            NBTTagCompound nbt = new NBTTagCompound();
            db.writeToNBT(nbt);
            NBTTagCompound players = (NBTTagCompound) nbt.getTag("players");
            String uuid = EntityPlayer.getUUID(player.getGameProfile()).toString();
            if (players.getKeySet().contains(uuid)) {
                players.removeTag(uuid);
                nbt.setTag("players", players);
                NBTTagCompound playerPos = players.getCompoundTag(uuid);
                int x = playerPos.getInteger("x");
                int y = playerPos.getInteger("y");
                int z = playerPos.getInteger("z");
                player.posX = x;
                player.posY = y;
                player.posZ = z;
                player.setPositionAndUpdate(x, y, z);
                db.readFromNBT(nbt);
                db.markDirty();
                player.setGameType(GameType.SURVIVAL);
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
            } else player.sendMessage(new TextComponentTranslation("command.arise_fail.message"));
        }
    }
}
