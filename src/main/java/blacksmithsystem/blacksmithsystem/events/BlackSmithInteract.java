package blacksmithsystem.blacksmithsystem.events;

import blacksmithsystem.blacksmithsystem.Inventory.BlacksmithInventory;
import blacksmithsystem.blacksmithsystem.libs.EasyComponent;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;


public class BlackSmithInteract implements Listener {

    @Getter
    private static Block clicked;
    HashMap<Integer, ItemStack> map = new HashMap<>();
    ItemStack itemStack;
    Inventory blacksmithInventory = BlacksmithInventory.inventory;
    Inventory recipeCreator = Bukkit.createInventory(null, InventoryType.WORKBENCH, ChatColor.BLUE + "Recipe Maker");
    //Nullpointer here, why? Figure out!
    ItemStack close = BlacksmithInventory.closeButtonItem();
    ItemStack glassPane = BlacksmithInventory.glassPaneItem();
    ItemStack recipeButton = BlacksmithInventory.recipeButtonItem();

    @EventHandler
    public void openInventory(PlayerInteractEvent event) {
        if (!(event.getClickedBlock().getType() == Material.ANVIL)) {
            return;
        }

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        clicked = event.getClickedBlock();

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100F, 25F);
        for (int i = 0; i <= blacksmithInventory.getSize() - 1; i++) {
            blacksmithInventory.setItem(i, glassPane);

        }

        blacksmithInventory.setItem(4, new ItemStack(Material.AIR));
        blacksmithInventory.setItem(0, close);
        blacksmithInventory.setItem(1, recipeButton);
        player.openInventory(blacksmithInventory);

    }

    @EventHandler
    public void clickedCloseItem(InventoryClickEvent event) {

        if (!event.getInventory().getTitle().equalsIgnoreCase(blacksmithInventory.getTitle())) {
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(close.getItemMeta().getDisplayName())) {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100F, 25F);
        }
    }

    /*
    Getting the item in the open window and adding into the recipe creator.
     */

    @EventHandler
    public void clickedRecipeItem(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(blacksmithInventory.getTitle())) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(recipeButton.getItemMeta().getDisplayName())) {
                Player p = (Player) event.getWhoClicked();
                itemStack = blacksmithInventory.getItem(4);

                if (itemStack.getType() == Material.AIR) {
                    return;
                }

                p.openInventory(recipeCreator);
                recipeCreator.setItem(0, itemStack);
            }
        }
    }

    /*
    Cancel if the player clicks on the item in the output window!
     */
    @EventHandler
    public void recipeOutputItemClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(recipeCreator.getTitle())) {
            //Cancel event for the slot 0.
            if (event.getSlot() == 0) {
                event.setCancelled(true);
            }
        }
    }

    /*
    Sending message and giving them a bit of information.
     */
    @EventHandler
    public void closedRecipeCreator(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(recipeCreator.getTitle())) {
            Player player = (Player) event.getPlayer();

            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Recipe: ");
            if (recipeCreator.getItem(0).getItemMeta().hasDisplayName()) {
                player.sendMessage(ChatColor.BLUE + "NAME: " + ChatColor.WHITE + recipeCreator.getItem(0).getItemMeta().getDisplayName());
            }
            player.sendMessage(ChatColor.BLUE + "TYPE: " + ChatColor.WHITE + recipeCreator.getItem(0).getType());
            player.sendMessage(ChatColor.BLUE + "AMOUNT: " + ChatColor.WHITE + recipeCreator.getItem(0).getAmount());

            EasyComponent.builder("Would you like to save this item? ")
                    .append("&a[YES] ")
                    .onHover("&8Save this file to be used in the blacksmith")
                    .onClick(ClickEvent.Action.RUN_COMMAND, "/bs save")
                    .append("&4[NO]")
                    .onClick(ClickEvent.Action.RUN_COMMAND, "")
                    .onHover("&8Delete the created recipe and start over")
                    .send(player);
        }
    }

}
