/*
 *    WD-Msg - A WaterDogPE plugin that allows proxy PMing
 *    Copyright (C) 2021  Funniray
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    I am available for any questions/requests: funniray10@gmail.com
 */

package com.funniray.waterdogmsg.commands;

import com.funniray.waterdogmsg.utils.UseQuotes;
import com.funniray.waterdogmsg.wdmsg;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.Arrays;
import java.util.UUID;

public class MsgCommand extends Command {
    public MsgCommand() {
        super("msg", CommandSettings.builder()
                .setDescription("Messages a player from the network")
                .setAliases(new String[]{"w","wisper","message"})
                //.setPermission("litebans.base")
                .setUsageMessage("Please use /msg <Player> <message>").build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        strings = UseQuotes.parseArgs(strings); //Quotes will be yeeted from strings btw

        if (strings.length >=2) {
            ProxiedPlayer to = ProxyServer.getInstance().getPlayer(strings[0]);
            String message = String.join(" ", Arrays.copyOfRange(strings, 1, strings.length));
            UUID senderUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

            if (commandSender.isPlayer()) {
                senderUUID = ((ProxiedPlayer) commandSender).getUniqueId();
            }

            if (to == null) {
                commandSender.sendMessage(wdmsg.config.getString("to_null"));
                return true;
            }

            wdmsg.messageHistory.put(senderUUID,to.getUniqueId());
            wdmsg.messageHistory.put(to.getUniqueId(),senderUUID);

            to.sendMessage(wdmsg.config.getString("from").replace("%player%", commandSender.getName()).replace("%msg%",message));
            commandSender.sendMessage(wdmsg.config.getString("to").replace("%player%", to.getName()).replace("%msg%",message));

            return true;
        }
        return false;
    }
}
