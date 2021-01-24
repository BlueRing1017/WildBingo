package wildbingo.wildbingo;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventManager implements Listener {
    private final WildBingo plugin;
    public EventManager(WildBingo instance){ plugin = instance; }

    int[] invLocList = {7,3,4,5,6,16,12,13,14,15,25,21,22,23,24,34,30,31,32,33,43,39,40,41,42};
    public static List<Player> clearPlayer = new ArrayList<>();

    @EventHandler
    public void GeneralInventoryClickEvent(InventoryClickEvent event) throws InterruptedException {
        Player player = (Player) event.getWhoClicked();
        String invTitle = event.getView().getTitle();
        ItemStack invItem = event.getCurrentItem();

        ItemStack item = new MaterialData(Material.BARRIER, (byte)0).toItemStack(1);
        ItemMeta barrier = item.getItemMeta();
        barrier.setDisplayName(ChatColor.RED+ "이미 클리어한 칸입니다.");
        barrier.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(barrier);

        if (invTitle.equals(ChatColor.BLACK+ "빙고판")){
            event.setCancelled(true);
            for (int i = 0; i < invLocList.length; i++){
                if (event.getRawSlot() == invLocList[i]){
                    if (player.getInventory().contains(invItem.getType())){
                        player.getInventory().remove(invItem);
                        player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 0.5F, 1);
                        Inventory inv = event.getInventory();
                        inv.setItem(event.getRawSlot(), item);
                        CommandManager.setBingo(player, inv);
                        player.openInventory(CommandManager.getBingo(player));
                        CommandManager.addSlot(player, event.getRawSlot());
                    }
                    break;
                }else if (event.getRawSlot() == 28){
                    if (CommandManager.getCheck(player) >= 5){
                        if (!clearPlayer.contains(player)){
                            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.2F, 1);
                            player.closeInventory();
                            clearPlayer.add(player);
                            Bukkit.getServer().broadcastMessage(player.getName()+ ChatColor.YELLOW.toString()+ ChatColor.BOLD.toString()+ " BINGO!");
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void GeneralRightCLickEvent(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        try {
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "야생빙고")) {
                if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (!clearPlayer.contains(player)) {
//                        player.openInventory(CommandManager.getBingo(player));
                        player.openInventory(player.openInventory(CommandManager.getBingo(player)));
                    }
                }
            }
        }
        catch (NullPointerException e1){}
        catch (IndexOutOfBoundsException e2){Bukkit.broadcastMessage("ERROR2");}

    }


}

