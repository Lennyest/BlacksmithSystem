package blacksmithsystem.blacksmithsystem.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandMaster extends Command {

    public CommandMaster(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (!sender.hasPermission("BlacksmithSystem.ACCESS")) {
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Blacksmith >"  + ChatColor.RESET + ChatColor.WHITE + "You do not have the required permissions to execute this command!");
        }

        Player player = (Player) sender;

        runCommand(player, args);

        return false;
    }

    protected abstract void runCommand(Player player, String[] args);

}
