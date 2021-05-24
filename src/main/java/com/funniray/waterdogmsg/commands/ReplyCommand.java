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

public class ReplyCommand extends Command {
    public ReplyCommand() {
        super("reply", CommandSettings.builder()
                .setDescription("Messages a player from the network")
                .setAliases(new String[]{"r"})
                //.setPermission("litebans.base")
                .setUsageMessage("Please use /reply <message>").build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length >=1) {
            String message = String.join(" ", strings);
            UUID senderUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

            if (commandSender.isPlayer()) {
                senderUUID = ((ProxiedPlayer) commandSender).getUniqueId();
            }

            UUID toUUID = wdmsg.messageHistory.get(senderUUID);

            CommandSender to;

            if (toUUID == null) {
                commandSender.sendMessage(wdmsg.config.getString("reply_null"));
                return true;
            } else if (toUUID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
                to = ProxyServer.getInstance().getConsoleSender();
            } else {
                to = ProxyServer.getInstance().getPlayer(toUUID);
            }


            if (commandSender.isPlayer()) {
                senderUUID = ((ProxiedPlayer) commandSender).getUniqueId();
            }

            if (to == null) {
                commandSender.sendMessage(wdmsg.config.getString("to_null"));
                return true;
            }

            //wdmsg.messageHistory.put(senderUUID,to.getUniqueId()); //This would be redundant
            wdmsg.messageHistory.put(toUUID,senderUUID);

            to.sendMessage(wdmsg.config.getString("from").replace("%player%", commandSender.getName()).replace("%msg%",message));
            commandSender.sendMessage(wdmsg.config.getString("to").replace("%player%", to.getName()).replace("%msg%",message));

            return true;
        }
        return false;
    }
}
