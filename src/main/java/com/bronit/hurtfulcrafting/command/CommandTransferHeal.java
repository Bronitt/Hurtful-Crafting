package com.bronit.hurtfulcrafting.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static com.bronit.hurtfulcrafting.event.CraftingEvent.db;

public class CommandTransferHeal extends CommandBase {

    public static final String NAME = "transferheal";

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
            EntityPlayer giver = server.getPlayerList().getPlayerByUsername(args[0]);
            EntityPlayer recipient = server.getPlayerList().getPlayerByUsername(args[1]);
            int heals = Integer.parseInt(args[2]);
            if (giver.getMaxHealth() > heals) {
                giver.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(giver.getMaxHealth() - heals);
                recipient.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(recipient.getMaxHealth() + heals);
                NBTTagCompound nbt = new NBTTagCompound();
                db.writeToNBT(nbt);
                NBTTagCompound players = (NBTTagCompound) nbt.getTag("players");
                String uuid = EntityPlayer.getUUID(recipient.getGameProfile()).toString();
                if (players.getKeySet().contains(uuid)) {
                    players.removeTag(uuid);
                    nbt.setTag("players", players);
                    NBTTagCompound playerPos = (NBTTagCompound) players.getTag(uuid);
                    int x = playerPos.getInteger("x");
                    int y = playerPos.getInteger("y");
                    int z = playerPos.getInteger("z");
                    recipient.posX = x;
                    recipient.posY = y;
                    recipient.posZ = z;
                    recipient.setPositionAndUpdate(x, y, z);
                    db.readFromNBT(nbt);
                    db.markDirty();
                    recipient.setGameType(GameType.SURVIVAL);
                    recipient.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
                }
            } else giver.sendMessage(new TextComponentTranslation("command.low_hp.message"));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1 || args.length == 2) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        } else return Collections.emptyList();
    }

}
