import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class Minmax implements Player{

    /*
    The minmax method selects the optimal move at each stage for both the player and the opponent in order to provide
    an accurate representation of the future game state.
     */

    // Initialise the global variables
    Integer depth;
    boolean red;

    // Boolean for whether the player is red or yellow
    public void setRed(boolean isRed) {
        red = isRed;
    }
    public boolean isRed() {
        return red;
    }

    // Constructor
    public Minmax(Integer d) {
        this.depth = d;
    }

    // Getter for the depth
    public Integer getDepth() {
        return depth;
    }

    // Main method for placing the counter, called by the game class
    @Override
    public int[][] place(int[][] m, boolean isRed, AtomicInteger row, AtomicInteger col) {

        // Set whether the player is red or not using input parameter
        setRed(isRed);

        // Initialising the variables used in the decision process
        ArrayList<Integer> cols = nonFullCol(m);
        Integer maxValue=null;
        ArrayList<Integer> maxCols = new ArrayList<>();
        boolean firstCol = true;

        // For each non-full column...
        for(Integer c:cols){

            // Creating a copy of the matrix and finding the lowest empty slot using methods from the interface
            int[][] mtrx = copyMatrix(m);
            int topRow=highestRow(mtrx,c);

            // Using the score class to value placing a counter at this slot
            int s = AIScoring.score(mtrx,c,topRow,isRed());

            // Place counter
            if(isRed){mtrx[topRow][c]=1;}
            if(!isRed){mtrx[topRow][c]=2;}

            int score;

            // If depth has not been reached and the move was not a winner...
            if(getDepth()>1 && !AIScoring.winCheck(mtrx)){
                // Call min method to simulate the opponents next move
                score = min(mtrx,getDepth());
            }else{
                // Else return the score at this stage
                score = s*getDepth();
            }

            // The next if loop returns the highest scoring column to place a counter. If there is multiple,
            // then it will return a list of the high scorers.

            // If it is the first column...
            if(firstCol){
                firstCol=false;
                maxValue=score;
                maxCols.add(c);
            } // Else if the score is higher than the high score...
            else if(score>maxValue){
                maxCols.clear();
                maxCols.add(c);
                maxValue=score;
            } // Else the score matches the high score...
            else if(score==maxValue){
                maxCols.add(c);
            }

        }

        // The Greedy.place method uses the Greedy class to select the best move at the current time if multiple
        // columns return the same score from the AB pruning.
        int maxColumn=Greedy.place(m,isRed,maxCols);
        int maxRow=highestRow(m,maxColumn);

        // Update the atomic integers to later update the GUI
        col.set(maxColumn);
        row.set(5-maxRow);

        // Place the counter in the selected spot and return the matrix
        if(isRed){m[maxRow][maxColumn]=1;}
        if(!isRed){m[maxRow][maxColumn]=2;}

        return m;
    }

    // Min method simulates the opponent's turn
    public int min(int[][] m, Integer times){

        // Initialise variables
        ArrayList<Integer> minOfThis = new ArrayList<>();
        int minValue;
        times-=1;

        // For each non-full column...
        for (Integer c:nonFullCol(m)){

            // Copy matrix and find the lowest empty slot
            int[][] mtrx = copyMatrix(m);
            int topRow=highestRow(mtrx,c);

            // Score for this column
            int s = AIScoring.score(mtrx,c,topRow,!isRed());

            // Place counter
            if (isRed()) {mtrx[topRow][c] = 2;}
            if (!isRed()) {mtrx[topRow][c] = 1;}

            int score;

            // If the depth has not been reached and not a winning move...
            if (times > 1 && !AIScoring.winCheck(mtrx)) {
                // Call max method
                score = max(mtrx, times);
            } else {
                score = -1*s*times;
            }

            minOfThis.add(score);

        }

        // Return the lowest value
        if(minOfThis.size()>0) {
            minValue = Collections.min(minOfThis);
            return minValue;
        }else{
            return 0;
        }

    }

    // Max method simulates a turn for the player, the method is similar to the min method
    public int max(int[][] m, Integer times) {
        ArrayList<Integer> maxOfThis = new ArrayList<>();
        int maxValue;
        times -= 1;

        for (Integer c : nonFullCol(m)) {

            int[][] mtrx = copyMatrix(m);

            int topRow=highestRow(mtrx,c);

            int s = AIScoring.score(mtrx,c,topRow,isRed());

            if (isRed()) {mtrx[topRow][c] = 1;}
            if (!isRed()) {mtrx[topRow][c] = 2;}

            int score;


            if (times > 1 && !AIScoring.winCheck(mtrx)) {
                // Call min method
                score = min(mtrx, times);
            } else {
                score = s*times;
            }
            maxOfThis.add(score);

        }

        // Return the highest value
        if (maxOfThis.size()>0) {
            maxValue = Collections.min(maxOfThis);
            return maxValue;
        }else{
            return 0;
        }
    }

}


