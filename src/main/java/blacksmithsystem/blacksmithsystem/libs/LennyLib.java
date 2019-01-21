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

    private static final CommandMap COMMAND_MAP;
    static {
        CommandMap tempCommandMap = null;
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            tempCommandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        COMMAND_MAP = tempCommandMap;
    }

    public static void registerCommand(Command command) {
            COMMAND_MAP.register(command.toString(), command);
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
