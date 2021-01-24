package wildbingo.wildbingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import wildbingo.wildbingo.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GuiManager {

    public static Inventory setNewBingo(Player player){
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK+ "빙고판");
        List<String> used = new ArrayList<>();
        int loc = 3;

        for (int i = 0; i < 25; i++){
            int num = (int) (Math.random() * FileManager.getItemList().length);
            Material item = Material.matchMaterial(FileManager.getItemList()[num]);
            while(used.contains(FileManager.getItemList()[num])) {
                num = (int) (Math.random() * FileManager.getItemList().length);
                item = Material.matchMaterial(FileManager.getItemList()[num]);
            }

            if (loc % 9 == 8){
                loc += 4;
            }
            bingoStack(item, 0, 1, loc, inv);
            used.add(FileManager.getItemList()[num]);
            System.out.println(FileManager.getItemList()[num]);

            loc++;
        }
        System.out.println("====================");

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        head.setItemMeta(headMeta);
        ItemMeta im = head.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW+ player.getName());
        head.setItemMeta(im);
        inv.setItem(10, head);

        stack(ChatColor.GOLD+ ChatColor.BOLD.toString()+ "BINGO!", Material.STRUCTURE_VOID, 0, 1,
                Arrays.asList(ChatColor.GRAY+ "", ChatColor.GREEN+ "5줄을 완성했다면 "+
                        ChatColor.GOLD+ ChatColor.BOLD.toString()+ "BINGO! "+ ChatColor.GREEN+ "를 눌러주세요."),28, inv);
        stack(ChatColor.GRAY+ "", Material.STRUCTURE_BLOCK, 0, 1, Arrays.asList(ChatColor.GRAY+ ""), 36, inv);

        return inv;
    }

    public void openBingoGui(Player player, Inventory inv){

        player.openInventory(inv);
    }

    public static void stack(String Display, Material ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv){
        ItemStack item = new MaterialData(ID, (byte)DATA).toItemStack(STACK);
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.setDisplayName(Display);
        im.setLore(lore);
        item.setItemMeta(im);
        inv.setItem(loc, item);
    }

    public static void bingoStack(Material ID, int DATA, int STACK, int loc, Inventory inv){
        ItemStack item = new MaterialData(ID, (byte)DATA).toItemStack(STACK);
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(im);
        inv.setItem(loc, item);
    }

}
