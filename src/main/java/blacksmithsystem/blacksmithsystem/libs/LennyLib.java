package blacksmithsystem.blacksmithsystem.libs;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class LennyLib {

    public static void registerCommand(Command command) {
        try {
            final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(command.toString(), command);

        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    public static String colorize(String input) {
       return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void sendBar(Player pl, String title) {
        try {
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colorize(title)));

        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

}
