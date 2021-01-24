package wildbingo.wildbingo;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitTask;

import javax.xml.soap.Text;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandManager implements CommandExecutor {
    private WildBingo plugin;
    public CommandManager(WildBingo instance) { plugin = instance; }

    private static List<Player> allPlayer = new ArrayList<>();
    private static List<Inventory> bingoBoard = new ArrayList<>();
    private static List<BingoManager> bm = new ArrayList<>();


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("This command can't use on console.");
            return false;
        }
        Player player = (Player) sender;
        if (args.length > 0) {
            if (command.getName().equalsIgnoreCase("wb")) {
                if (args[0].equalsIgnoreCase("start")) {
                    EventManager.clearPlayer.clear();
                    ItemStack item = new MaterialData(Material.BOOK, (byte)0).toItemStack(1);
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(ChatColor.GOLD+ "야생빙고");
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    im.setLore(Arrays.asList(ChatColor.GRAY+ "", ChatColor.YELLOW+ "빙고를 완성하세요!"));
                    item.setItemMeta(im);
                    allPlayer = (List<Player>) Bukkit.getServer().getOnlinePlayers();

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TextComponent includeLink = new TextComponent(ChatColor.RED.toString()+ ChatColor.BOLD.toString()+ "유튜브 링크"+ ChatColor.RESET.toString()+ " : https://www.youtube.com/c/딤딤");
                            includeLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/c/딤딤"));
                            Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE+ ChatColor.BOLD.toString()+ "기획" + ChatColor.RESET+ " : 딤퍼니 " );
                            for (int i = 0; i < allPlayer.size(); i++){
                                allPlayer.get(i).playSound(allPlayer.get(i).getLocation(), Sound.BLOCK_CHAIN_PLACE, 1F, 1);
                                allPlayer.get(i).spigot().sendMessage(includeLink);
                            }
                            Bukkit.getServer().broadcastMessage(ChatColor.BLUE+ ChatColor.BOLD.toString()+ "플러그인" + ChatColor.RESET+ " : BlueRing1017 (블링)");
                            Bukkit.getServer().broadcastMessage(ChatColor.of(new Color(161,118,100)).toString()+ ChatColor.BOLD.toString()+ "리소스팩"+ ChatColor.RESET+ " : MGM_Choco (미초)");

                        }
                    }, 10);

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < allPlayer.size(); i++){
                                allPlayer.get(i).playSound(allPlayer.get(i).getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 1F, 1);
                            }
                            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+ Integer.toString(FileManager.getItemList().length)+ " 개의 아이템 목록을 불러왔습니다.");
                            Bukkit.getServer().broadcastMessage(ChatColor.RED+ "현 버전은 네더라이트, 프리즈머린 등 난이도가 높은 아이템은 대체로 제외된 버전입니다.");

                        }
                    }, 40);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < allPlayer.size(); i++){
                                allPlayer.get(i).playSound(allPlayer.get(i).getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2);
                            }
                            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+ "3초뒤 시작됩니다.");

                        }
                    }, 60);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < allPlayer.size(); i++){
                                allPlayer.get(i).playSound(allPlayer.get(i).getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2);
                            }
                            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+ "2초뒤 시작됩니다.");

                        }
                    }, 80);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < allPlayer.size(); i++){
                                allPlayer.get(i).playSound(allPlayer.get(i).getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2);
                            }
                            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+ "1초뒤 시작됩니다.");

                        }
                    }, 100);

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < allPlayer.size(); i++){
                                allPlayer.get(i).playSound(allPlayer.get(i).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1);
                            }
                            Bukkit.getServer().broadcastMessage(ChatColor.GOLD+ ChatColor.BOLD.toString()+ "게임이 시작되었습니다.");

                            for(int i = 0; i < allPlayer.size(); i++){
                                Inventory inv = GuiManager.setNewBingo(allPlayer.get(i));
                                bingoBoard.add(inv);
                                allPlayer.get(i).getInventory().addItem(item);
                                bm.add(new BingoManager(allPlayer.get(i), inv));
                            }
                        }
                    },120);



                    return true;
                }
                else if (args[0].equalsIgnoreCase("reset")) {

                    bingoBoard.clear();
                    bm.clear();

                    return true;
                }
                else if (args[0].equalsIgnoreCase("show")) {

                    for(int i = 0; i < bm.size(); i++){
                        Bukkit.broadcastMessage(bm.get(i).getPlayer().getName());
                    }

                    return true;
                }
                else if (args[0].equalsIgnoreCase("board")) {

                    try {
                        for (int i = 0; i < bm.size(); i++) {
                            if (bm.get(i).getPlayer().getName().equals(args[1])) {
                                player.openInventory(bm.get(i).getBingoBoard());
                                return true;
                            }
                        }
                        Bukkit.broadcastMessage("None Player : " + args[1]);
                    }catch (NullPointerException e){
                        System.out.println("error");
                    }

                    return true;
                }
            }
        }

        return true;
    }

    public String applyHex (String hex, String msg){
        return ChatColor.of(hex)+ msg;
    }

    public static void addSlot(Player player, int slot){
        for (int i = 0; i < bm.size(); i++){
            if (player.getName().equals(bm.get(i).getPlayer().getName())){
                bm.get(i).addClearedSlot(slot);
                return;
            }
        }
    }


    public static int getCheck(Player player){
                for (int i = 0; i < bm.size(); i++) {
                    if (player.getName().equals(bm.get(i).getPlayer().getName())) {
                        return bm.get(i).checkClearedSlot();
                    }
                }


        return 0;
    }

    public static Inventory getBingo(Player player){
        for (int i = 0; i < bm.size(); i++){
            if (bm.get(i).getPlayer().getName().equals(player.getName())){
                return bm.get(i).getBingoBoard();
            }
        }
        return null;
    }

    public static void setBingo(Player player, Inventory inv){
        int index = 0;
        for (int i = 0; i < allPlayer.size(); i++){
            if (allPlayer.get(i).equals(player)){
                index = i;
                break;
            }
        }
        bingoBoard.set(index, inv);
    }


}
