package wildbingo.wildbingo;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class BingoManager {

    private static List<Integer> clearedSlot = new ArrayList<>();
    private static int[][] slotList = {{3,4,5,6,7},{12,13,14,15,16},{21,22,23,24,25},{30,31,32,33,34},{39,40,41,42,43},
            {3,12,21,30,39},{4,13,22,31,40},{5,14,23,32,41},{6,15,24,33,42},{7,16,25,34,43},
            {3,13,23,33,43},{7,15,23,31,39}};
    private Player player;
    private Inventory bingoBoard;

    BingoManager(Player player,Inventory inv){

        this.player = player;
        this.bingoBoard = inv;
    }

    public static int checkClearedSlot(){
        int count = 0;
        int num;
        for(int i = 0; i < 12; i++){
            num = 0;
            for(int j = 0; j < 5; j++){
                for(int k = 0; k < clearedSlot.size(); k++){
                    if (clearedSlot.get(k) == slotList[i][j]){
                        num++;
                    }
                }
            }
            if (num == 5){
                count++;
            }
        }
        return count;
    }

    public void addClearedSlot(int slot){
        clearedSlot.add(slot);
    }

    public void setClearedSlot(List<Integer> clearedSlot) {
        this.clearedSlot = clearedSlot;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void removePlayer() {
        player.remove();
    }

    public Inventory getBingoBoard() {
        return bingoBoard;
    }

    public void setBingoBoard(Inventory bingoBoard) {
        this.bingoBoard = bingoBoard;
    }

}
