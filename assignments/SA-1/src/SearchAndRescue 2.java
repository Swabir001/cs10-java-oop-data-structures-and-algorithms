// Author: Swabir Mohamed Bwana, '27
// Class: CS10
// Assignment: SA-1
// Date: Thur Sep 18th, 2025


public class SearchAndRescue {
    // number of rows and columns in the grid
    private int m;
    private int n;

    // 2D array to hold the probabilities
    private double[][] grid;

    public static void main(String[] args) {
        // create a grid with 13 rows and 6 columns
        int m = 13;
        int n = 6;

        // create the SearchAndRescue object with that grid size
        SearchAndRescue sar = new SearchAndRescue(m,n); // creating a new object

        // pick a row and column to update (some parameters are deliberately out of bounds here for testing)
        sar.setGridProbability(2, 3, 15);
        sar.setGridProbability(6, 5, 0.59);
        sar.setGridProbability(98, 1, 0.08);
        sar.setGridProbability(3, 6, 0.15);

        sar.printBestLocation();
        sar.printGrid();
    }


    // constructor: sets up the grid and fills it up with equal probability in each spot
    public SearchAndRescue(int m, int n){
        this.m = m;
        this.n = n;
        this.grid = new double[m][n];

        // every cell starts with 1 / (m*n)
        for (int i = 0; i<m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] =  1.0 / (m*n);
            }
        }
    }


    // tries to set a probability in the given row and column
    public boolean setGridProbability(int row , int col, double probability) {
        // convert from 1-based to 0-based indexing
        row = row - 1;
        col = col - 1;

        // check if row and column are inside the grid
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            System.out.println("Error: row/col out of bounds.");
            System.out.println();
            return false;
        }

        // check if probability is valid (0 to 1)
        if (probability < 0 || probability > 1) {
            System.out.println("Invalid probability entry. It has to be between 0 and 1.");
            System.out.println();
            return false;
        }

        else{
            // if both checks pass, update the grid cell
            grid[row][col] = probability;

            // confirm to the user that the probability was set
            System.out.println("Probability at row " + (row+1) + " column " + (col+1) + " set to " + probability);
            System.out.println(" ");
            return true;
        }

    }


    // finds and prints the location with the highest probability
    public void printBestLocation() {
        double maxProb = -1.0;  // start lower than any real probability
        int bestRow = -1;
        int bestCol = -1;

        // check every cell in the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] > maxProb){
                    maxProb = grid[i][j];
                    bestRow = i;
                    bestCol = j;
                }
            }
        }
        // if a best cell was found, print its row, column, and probability
        if (bestRow != -1 && bestCol != -1) {
            System.out.println("Best location is row " + (bestRow+1) +
                    ", column " + (bestCol+1) +
                    " with probability of " + maxProb);
            System.out.println();
        }
    }

    //prints the grid probabilities
    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%.2f", grid[i][j]) ;
                System.out.print("  "); //spacing

            }
            System.out.println();
        }
    }
}
