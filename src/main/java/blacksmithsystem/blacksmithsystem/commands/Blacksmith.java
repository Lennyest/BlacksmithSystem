package blacksmithsystem.blacksmithsystem.commands;

import blacksmithsystem.blacksmithsystem.BlacksmithSystem;
import blacksmithsystem.blacksmithsystem.libs.ConfigMaster;
import blacksmithsystem.blacksmithsystem.libs.EasyComponent;
import blacksmithsystem.blacksmithsystem.libs.LennyLib;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;

public class Blacksmith extends CommandMaster {

    /*Plugin p = BlacksmithSystem.getPlugin();
    private String path = p.getDataFolder() + File.separator + "recipes.yml";
    private File file = new File(path);
    private FileConfiguration cfg = new YamlConfiguration();

    ConfigMaster config = new ConfigMaster(file, path, cfg);*/

    public Blacksmith() {
        super("Blacksmith");
        setAliases(Arrays.asList("bs"));
        setDescription("A fully fledged blacksmith system");
        setUsage("/blacksmith");
    }

    @Override
    protected void runCommand(Player player, String[] args) {


        if (!(args.length == 1)) {
            player.sendMessage(ChatColor.RED + "Wrong usage!");
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Try again: ");
            EasyComponent.builder("&8I was trying to save the config")
                    .onHover("Click me!")
                    .onClickSuggestCmd("/blacksmith save")
                    .send(player);
            EasyComponent.builder("&8I was trying to create a new recipe")
                    .onHover("Click me to get started")
                    .onClickSuggestCmd("/blacksmith GUI")
                    .send(player);
            EasyComponent.builder("&8I was going to look in the help menu")
                    .onHover("Click me to get sent the help menu")
                    .onClickSuggestCmd("/blacksmith help")
                    .send(player);
            return;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100F, 100F);

        if (args[0].equalsIgnoreCase("save")) {
            //config.saveConfig();
            player.sendMessage(ChatColor.BOLD + "SAVED TO FILE");
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            //config.reloadConfig();
            player.sendMessage(ChatColor.BOLD + "CONFIG RELOADED");
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.STRIKETHROUGH + "                                       ");
            player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BLACKSMITH SYSTEM");
            player.sendMessage("");
            EasyComponent.builder(ChatColor.GOLD + "" + ChatColor.BOLD + "SAVE")
                    .append(ChatColor.WHITE + " - " + ChatColor.RESET)
                    .append(ChatColor.GRAY + "/blacksmith save")
                    .onClickSuggestCmd("/blacksmith save")
                    .onHover("Click me!")
                    .send(player);
            player.sendMessage(ChatColor.STRIKETHROUGH + "                                       ");
        }
    }
}
