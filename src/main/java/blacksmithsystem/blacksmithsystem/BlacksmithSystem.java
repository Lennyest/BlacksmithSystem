package blacksmithsystem.blacksmithsystem;

import blacksmithsystem.blacksmithsystem.commands.Blacksmith;
import blacksmithsystem.blacksmithsystem.events.BlackSmithInteract;
import blacksmithsystem.blacksmithsystem.events.BlacksmithInventoryInteract;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static blacksmithsystem.blacksmithsystem.libs.LennyLib.registerCommand;

public final class BlacksmithSystem extends JavaPlugin {

    public static Plugin INSTANCE;

    public BlacksmithSystem() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        registerCommand(new Blacksmith());
        getServer().getPluginManager().registerEvents(new BlackSmithInteract(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithInventoryInteract(), this);
    }
}
