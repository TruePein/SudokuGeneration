package sudoku;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Eric
 */
public class Grid {
    private int[][] grid;
    private final int SIZE = 9;
    private RollBackMemory rbm;
    private Random r;
    
    public Grid(){
        grid = new int[SIZE][SIZE];
        rbm = new RollBackMemory();
        r = new Random();
    }
    
    public void fill(){
        while(!getPossibleMoves().isEmpty()){
            ArrayList<Integer[]> possibleMoves = getPossibleMoves();
            boolean madeMove = false;
            while(!madeMove && !possibleMoves.isEmpty()){
                //Integer[] move = possibleMoves.remove(r.nextInt(possibleMoves.size()));
                Integer[] move = possibleMoves.remove(0);
                int row = move[0];
                int column = move[1];
                ArrayList<Integer> possibleValues = new ArrayList(){{
                    for(int i = 1; i <= 9; i++){
                        add(i);
                    }
                }};
                while(!madeMove && !possibleValues.isEmpty()){
                    //int value = possibleValues.remove(r.nextInt(possibleValues.size()));
                    int value = possibleValues.remove(0);
                    if(checkMove(row, column, value)){
                        makeMove(row, column, value);
                        if(checkAll(possibleMoves)){
                            rbm.saveMoves(possibleMoves);
                            rbm.saveValues(possibleValues);
                            rbm.saveLastMove(row, column);
                            madeMove = true;
                        }else{
                            makeMove(row, column, 0);
                        }
                    }
                }
                if(!madeMove){
                    rollBackMove();
                }
            }
            if(!madeMove){
                rollBackValue();
            }
        }
    }
    
    private void rollBackValue(){
        int[] lastMove = rbm.retrieveLastMove();
        int row = lastMove[0];
        int column = lastMove[1];
        ArrayList<Integer> possibleValues = rbm.retrieveValues();
        makeMove(row, column, 0);
        boolean madeMove = false;
        while(!madeMove && !possibleValues.isEmpty()){
            //int value = possibleValues.remove(r.nextInt(possibleValues.size()));
            int value = possibleValues.remove(0);
            if(checkMove(row, column, value)){
                if(checkAll(getPossibleMoves())){
                    makeMove(row, column, value);
                    rbm.saveValues(possibleValues);
                    rbm.saveLastMove(row, column);
                    madeMove = true;
                }else{
                    makeMove(row, column, 0);
                }
            }
        }
        if(!madeMove){
            rollBackMove();
        }
    }
    
    private void rollBackMove(){
        ArrayList<Integer[]> possibleMoves = rbm.retrieveMoves();
        boolean madeMove = false;
        while(!madeMove && !possibleMoves.isEmpty()){
            //Integer[] move = possibleMoves.remove(r.nextInt(possibleMoves.size()));
            Integer[] move = possibleMoves.remove(0);
            int row = move[0];
            int column = move[1];
            ArrayList<Integer> possibleValues = new ArrayList(){{
                for(int i = 1; i <= 9; i++){
                    add(i);
                }
            }};
            while(!madeMove && !possibleValues.isEmpty()){
                //int value = possibleValues.remove(r.nextInt(possibleValues.size()));
                int value = possibleValues.remove(0);
                if(checkMove(row, column, value)){
                    if(checkAll(possibleMoves)){
                        makeMove(row, column, value);
                        rbm.saveMoves(possibleMoves);
                        rbm.saveValues(possibleValues);
                        rbm.saveLastMove(row, column);
                        madeMove = true;
                    }else{
                        makeMove(row, column, 0);
                    }
                }
            }
        }
        if(!madeMove){
            rollBackValue();
        }
    }
        
    private ArrayList getPossibleMoves(){
        ArrayList<Integer[]> a = new ArrayList();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(grid[i][j] == 0){
                    a.add(new Integer[]{i,j});
                }
            }
        }
        return a;
    }
    
    private boolean checkAll(ArrayList<Integer[]> possibleMoves){
        for(Integer[] move : possibleMoves){
            int row = move[0];
            int column = move[1];
            boolean canMove = false;
            for(int value = 1; value <= 9; value++){
                if(checkMove(row, column, value)){
                    canMove = true;
                    value = 9;
                }
            }
            if(!canMove){
                return false;
            }
        }
        return true;
    }
    
    private boolean checkMove(int row, int column, int value){
        for(int i = 0; i < 9; i++){
            if(grid[row][i]==value||grid[i][column]==value){
                return false;
            }
        }
        int rowStart = (row/3)*3;
        int rowEnd = rowStart+2;
        int columnStart = (column/3)*3;
        int columnEnd = columnStart+2;
        for(int i = rowStart; i <= rowEnd; i++){
            for(int j = columnStart; j <= columnEnd; j++){
                if(grid[i][j]==value)
                    return false;
            }
        }
        return true;
    }
    
    private void makeMove(int row, int column, int value){
        if(value == 0){
            System.out.print("Removing " + grid[row][column] + " from ");
        }else{
            System.out.print("Placing " + value + " at ");
        }
        System.out.println("[" + (row+1) + "][" + (column+1) + "]");
        grid[row][column] = value;
        printBoard();
    }
    
    public void printBoard(){
        System.out.println("---------------------------------------");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print("|");
                for(int k = 0; k < 3; k++){
                    for(int l = 0; l < 3; l++){
                        System.out.print(" " + getValueFromCell((i*3)+j, (k*3)+l) + " |");
                    }
                    if(k!=2){
                        System.out.print("|");
                    }
                }
                System.out.println();
            }
            System.out.println("---------------------------------------");
        }
    }
    
    private String getValueFromCell(int row, int column){
        if(grid[row][column]==0){
            return " ";
        }
        return Integer.toString(grid[row][column]);
    }
}
