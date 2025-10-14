package com.bronit.hurtfulcrafting.command;

import com.bronit.hurtfulcrafting.HurtfulCrafting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandTransferHeal extends CommandBase {

    public static final String NAME = "transfer";

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
