package sudoku;
import java.util.ArrayList;
/**
 *
 * @author Eric
 */
public class RollBackMemory {
    private ArrayList[] possibleMoves;
    private int movePointer;
    private ArrayList[] possibleValues;
    private int valuePointer;
    private int[][] lastMove;
    private int lastPointer;
    
    public RollBackMemory(){
        possibleMoves = new ArrayList[81];
        movePointer = 0;
        possibleValues = new ArrayList[81];
        valuePointer = 0;
        lastMove = new int[81][2];
        lastPointer = 0;
    }
    
    public void saveMoves(ArrayList a){
        ArrayList b = new ArrayList();
        for (Object a1 : a) {
            b.add(a1);
        }
        possibleMoves[movePointer] = b;
        movePointer++;
    }
    
    public ArrayList retrieveMoves(){
        movePointer--;
        ArrayList a = new ArrayList();
        for(int i = 0; i < possibleMoves[movePointer].size(); i++){
            a.add(possibleMoves[movePointer].get(i));
        }
        return a;
    }
    
    public void saveValues(ArrayList a){
        ArrayList b = new ArrayList();
        for (Object a1 : a) {
            b.add(a1);
        }
        possibleValues[valuePointer] = b;
        valuePointer++;
    }
    
    public ArrayList retrieveValues(){
        valuePointer--;
        ArrayList a = new ArrayList();
        for(int i = 0; i < possibleValues[valuePointer].size(); i++){
            a.add(possibleValues[valuePointer].get(i));
        }
        return a;
    }
    
    public void saveLastMove(int row, int column){
        lastMove[lastPointer][0] = row;
        lastMove[lastPointer][1] = column;
        lastPointer++;
    }
    
    public int[] retrieveLastMove(){
        lastPointer--;
        int[] pair = new int[2];
        pair[0] = lastMove[lastPointer][0];
        pair[1] = lastMove[lastPointer][1];
        return pair;
    }
}
