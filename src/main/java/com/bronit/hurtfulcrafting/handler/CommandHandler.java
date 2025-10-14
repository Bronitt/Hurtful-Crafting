package com.bronit.hurtfulcrafting.handler;

import com.bronit.hurtfulcrafting.command.CommandTransferHeal;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandHandler {

    public CommandHandler(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTransferHeal());
    }

}
