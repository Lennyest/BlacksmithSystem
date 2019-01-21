package blacksmithsystem.blacksmithsystem.libs;

import blacksmithsystem.blacksmithsystem.BlacksmithSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

public class ConfigMaster {
    public File file;
    public String path;
    public FileConfiguration configuration;
    public ConfigMaster(File f, String p, FileConfiguration c) {
        file = f;
        path = p;
        configuration = c;

        if (!file.exists()) {
            createConfig();
        }
    }


    private void createConfig() {

        if (!BlacksmithSystem.INSTANCE.getDataFolder().exists()) {
            BlacksmithSystem.INSTANCE.getDataFolder().mkdir();
        }

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            BlacksmithSystem.INSTANCE.saveResource(path, false);
        }

        try {
            configuration.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        if (file.exists()) {
            try {
                configuration.save(file);
                configuration.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                System.out.println("Caused by BlacksmithSystem plugin");
                System.out.println("ERROR TYPE: " + e);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("BlacksmithSystem.ERRORS")) {
                        player.sendMessage("An error has occured with the blacksmith system, unable to save!");
                        player.sendMessage("Error type: " + e);
                    }
                }

            }
        }
    }

}
