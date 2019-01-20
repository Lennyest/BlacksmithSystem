package blacksmithsystem.blacksmithsystem.Inventory;

import blacksmithsystem.blacksmithsystem.BlacksmithSystem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BlacksmithInventory {

    public static Inventory inventory = Bukkit.createInventory(null, 3 * 12, ChatColor.DARK_RED + "Blacksmith Inventory") ;


    public static ItemStack glassPane;
    public static ItemStack closeButton;
    public static ItemStack craftLockpickButton;
    public static ItemStack recipeButton;


    public static ItemStack glassPaneItem() {
        glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta glassPaneMeta = glassPane.getItemMeta();
        glassPaneMeta.setDisplayName(" ");
        glassPane.setItemMeta(glassPaneMeta);
        return glassPane;
    }

    public static ItemStack closeButtonItem() {
        closeButton = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
        ItemMeta closeMeta = closeButton.getItemMeta();
        closeMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "CLOSE");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Click me to close");

        closeMeta.setLore(lore);
        closeButton.setItemMeta(closeMeta);

        return closeButton;
    }

    public static ItemStack craftLockpickButtonItem() {
        craftLockpickButton = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta cLB = craftLockpickButton.getItemMeta();
        cLB.setDisplayName(ChatColor.RED + "Lockpick");
        ArrayList<String> lockpickLore = new ArrayList<>();
        lockpickLore.add(ChatColor.GRAY + "A simple lockpick used to gain access to locked entities.");
        cLB.setLore(lockpickLore);

        craftLockpickButton.setItemMeta(cLB);

        return craftLockpickButton;
    }

    public static ItemStack recipeButtonItem() {
        recipeButton = new ItemStack(Material.PAPER);
        ItemMeta recipeMeta = recipeButton.getItemMeta();
        recipeMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        recipeMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.BLUE + "Click me to change recipe!");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "This will close this menu and remove all your progress!");
        recipeMeta.setLore(lore);
        recipeButton.setItemMeta(recipeMeta);

        return recipeButton;
    }
}
