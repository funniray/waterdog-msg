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

package com.funniray.waterdogmsg;

import com.funniray.waterdogmsg.commands.MsgCommand;
import com.funniray.waterdogmsg.commands.ReplyCommand;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

public class wdmsg extends Plugin {

    public static HashMap<UUID,UUID> messageHistory = new HashMap<>();
    public static Configuration config;

    private void createDefaultConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceFile("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {
        createDefaultConfig();

        config = getConfig();

        getProxy().getCommandMap().registerCommand(new MsgCommand());
        getProxy().getCommandMap().registerCommand(new ReplyCommand());
    }

}
