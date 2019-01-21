package blacksmithsystem.blacksmithsystem.events;

import blacksmithsystem.blacksmithsystem.BlacksmithSystem;
import blacksmithsystem.blacksmithsystem.libs.EasyComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;


public class BlackSmithInteract implements Listener {

    public static final String RESULT_SETTER_TITLE = ChatColor.DARK_RED + "Blacksmith Inventory";
    private static final String CREATOR_TITLE = ChatColor.BLUE + "Recipe Maker";

    private static final ItemStack CLOSE = new ItemStack(Material.EMERALD, 1, (byte) 14) {{
        ItemMeta closeMeta = getItemMeta();
        closeMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "CLOSE");
        closeMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click me to CLOSE"));
        setItemMeta(closeMeta);
    }};
    private static final ItemStack GLASS_PANE = new ItemStack(Material.GLASS, 1, (byte) 15) {{
        ItemMeta glassPaneMeta = getItemMeta();
        glassPaneMeta.setDisplayName(ChatColor.RESET.toString());
        setItemMeta(glassPaneMeta);
    }};
    private static final ItemStack RECIPE_BUTTON = new ItemStack(Material.PAPER) {{
        ItemMeta recipeMeta = getItemMeta();
        recipeMeta.addItemFlags(ItemFlag.values());
        recipeMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.BLUE + "Click me to change recipe!");
        recipeMeta.setLore(Collections.singletonList(ChatColor.GRAY + "This will CLOSE this menu and remove all your progress!"));
        setItemMeta(recipeMeta);
    }};

    @EventHandler
    public void openInventory(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!(event.getClickedBlock().getType() == Material.ANVIL)) {
            return;
        }
        event.setCancelled(true);

        Player player = event.getPlayer();
        player.removeMetadata("anvil", BlacksmithSystem.INSTANCE);
        player.getMetadata("anvil").clear();
        player.setMetadata("anvil", new FixedMetadataValue(BlacksmithSystem.INSTANCE, event.getClickedBlock().getLocation().toVector().toBlockVector()));

//        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100F, 25F);
        Inventory blacksmithInventory  = Bukkit.createInventory(null, 36, RESULT_SETTER_TITLE);
        for (int i = 0; i <= blacksmithInventory.getSize() - 1; i++) {
            blacksmithInventory.setItem(i, GLASS_PANE);
        }
        blacksmithInventory.setItem(4, new ItemStack(Material.AIR));
        blacksmithInventory.setItem(0, CLOSE);
        blacksmithInventory.setItem(1, RECIPE_BUTTON);
        player.openInventory(blacksmithInventory);

    }

    @EventHandler
    public void clickedCloseItem(InventoryClickEvent event) {

        if (!event.getInventory().getTitle().equalsIgnoreCase(RESULT_SETTER_TITLE)) {
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(CLOSE.getItemMeta().getDisplayName())) {
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
        if (event.getInventory().getTitle().equalsIgnoreCase(RESULT_SETTER_TITLE)) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(RECIPE_BUTTON.getItemMeta().getDisplayName())) {
                Player p = (Player) event.getWhoClicked();
                ItemStack itemStack = event.getClickedInventory().getItem(4);

                if (itemStack.getType() == Material.AIR) return;
                Inventory inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH, CREATOR_TITLE);
                inventory.setItem(0, itemStack);
                p.openInventory(inventory);
            }
        }
    }

    /*
    Cancel if the player clicks on the item in the output window!
     */
    @EventHandler
    public void recipeOutputItemClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(CREATOR_TITLE)) {
            //Cancel event for the slot is a result slot.
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                event.setCancelled(true);
            }
        }
    }

    /*
    Sending message and giving them a bit of information.
     */
    @EventHandler
    public void closedRecipeCreator(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(CREATOR_TITLE)) {
            Player player = (Player) event.getPlayer();

            player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Recipe: ");
            if (event.getInventory().getItem(0).getItemMeta().hasDisplayName()) {
                player.sendMessage(ChatColor.BLUE + "NAME: " + ChatColor.WHITE + event.getInventory().getItem(0).getItemMeta().getDisplayName());
            }
            player.sendMessage(ChatColor.BLUE + "TYPE: " + ChatColor.WHITE + event.getInventory().getItem(0).getType());
            player.sendMessage(ChatColor.BLUE + "AMOUNT: " + ChatColor.WHITE + event.getInventory().getItem(0).getAmount());

            UUID uuid = UUID.randomUUID();
            DEBATED_RECIPES.put(uuid, event.getInventory().getContents());
            EasyComponent.builder("Would you like to save this item? ")
                    .append(ChatColor.GREEN + "[YES] ")
                    .onHover(ChatColor.GRAY + "Save this file to be used in the blacksmith")
                    .onClick(ClickEvent.Action.RUN_COMMAND, "/bs save " + uuid)
                    .append(ChatColor.RED + "[NO]")
                    .onClick(ClickEvent.Action.RUN_COMMAND, "/bs discard " + uuid)
                    .onHover(ChatColor.GRAY + "Delete the created recipe and start over")
                    .send(player);
        }
    }

    public static final Map<UUID, ItemStack[]> DEBATED_RECIPES = new HashMap<>();
}
