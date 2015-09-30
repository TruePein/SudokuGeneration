/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author Eric
 */
public class Sudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        Grid grid = new Grid();
        grid.fill();
        final long endTime = System.currentTimeMillis();
        final long totalTime = endTime - startTime;
        System.out.println("Total execution time: " + totalTime);
    }
    
}
