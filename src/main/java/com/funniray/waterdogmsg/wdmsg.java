package com.funniray.waterdogmsg;

import com.funniray.waterdogmsg.commands.MsgCommand;
import com.funniray.waterdogmsg.commands.ReplyCommand;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
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
