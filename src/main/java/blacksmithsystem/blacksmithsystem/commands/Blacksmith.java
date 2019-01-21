package blacksmithsystem.blacksmithsystem.commands;

import blacksmithsystem.blacksmithsystem.events.BlackSmithInteract;
import blacksmithsystem.blacksmithsystem.libs.EasyComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.UUID;

public class Blacksmith extends CommandMaster {

    public Blacksmith() {
        super("Blacksmith");
        setAliases(Collections.singletonList("bs"));
        setDescription("A fully fledged blacksmith system");
        setUsage("/blacksmith");
    }

    @Override
    protected void runCommand(Player player, String[] args) {

        if (args.length == 1) {
            switch (args[0]) {
                case "save":
//                    config.saveConfig();
                    return;
                case "reload":
//                    config.reloadConfig();
                    return;
                case "help":
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
                    return;
            }
        } else if (args.length > 1) {
            switch (args[0]) {
                case "save":
                    ItemStack[] inventoryContents = BlackSmithInteract.DEBATED_RECIPES.get(UUID.fromString(args[1]));
                    // from here, convert this to whatever format you want (NMSUtils json would work very well with this)
                    // then save to file.
                    player.sendMessage("Added recipe");
                    BlackSmithInteract.DEBATED_RECIPES.remove(UUID.fromString(args[1]));
                    return;
                case "discard":
                    BlackSmithInteract.DEBATED_RECIPES.remove(UUID.fromString(args[1]));
                    player.sendMessage("Removed recipe");
                    return;
            }

        } else player.sendMessage(ChatColor.RED + "Unrecognized command");
//        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100F, 100F);
    }
}
