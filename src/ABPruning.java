import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ABPruning implements Player{

    /*
    The ABPruning method is an adaptation of teh minmax method, whereby it does not explore further branches if the
    condition alpha >= beta is met
     */

    // Initialise the global variables
    Integer depth;
    boolean red;

    // Method for registering whether the method is for the red or yellow player
    public void setRed(boolean isRed) {
        red = isRed;
    }
    public boolean isRed() {
        return red;
    }


    // Constructor with depth parameter
    public ABPruning(Integer d) {
        this.depth = d;
    }

    // Get method for the depth
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
        Double maxValue=null;
        ArrayList<Integer> maxCols = new ArrayList<>();
        boolean firstCol = true;

        // Setting the initial values for alpha and beta
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        // For each non-full column...
        for(Integer c:cols){

            // Creating a copy of the matrix and finding the lowest empty slot using methods from the interface
            int[][] mtrx = copyMatrix(m);
            int topRow=highestRow(mtrx,c);

            // Using the score class to value placing a counter at this slot
            int s = AIScoring.score(mtrx,c,topRow,isRed());

            // Place a counter on this top spot
            if(isRed){mtrx[topRow][c]=1;}
            if(!isRed){mtrx[topRow][c]=2;}

            double score;

            // If depth has not been reached and the move was not a winner...
            if(getDepth()>1 && !AIScoring.winCheck(mtrx)){
                // Call min method to simulate the opponents next move
                score = min(mtrx,getDepth(), alpha, beta);
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

    // Min method simulates the opponent selecting the move that is best for them
    // Uses a similar method to that seen above
    public double min(int[][] m, Integer times, double alpha, double beta){

        // Initialise the variables
        double minValue = Double.POSITIVE_INFINITY;
        times-=1;

        for (Integer c:nonFullCol(m)){

            int[][] mtrx = copyMatrix(m);
            int topRow=highestRow(mtrx,c);

            int s = AIScoring.score(mtrx,c,topRow,!isRed());

            if (isRed()) {
                mtrx[topRow][c] = 2;
            }
            if (!isRed()) {
                mtrx[topRow][c] = 1;
            }

            double score;

            if (times > 1 && !AIScoring.winCheck(mtrx)) {
                // Call the maximum method
                score = max(mtrx, times, alpha, beta);
            }else if (AIScoring.winCheck(mtrx)){
                score=-1*s*times;
            }else{
                score = AIScoring.boardStateScore(mtrx,isRed());
            }

            // Update the values of beta since this is the min method
            if(score<minValue){
                minValue=score;
            }
            if(minValue<beta){
                beta=minValue;
            }
            // If the AB pruning condition is met
            if(alpha>=beta){
                break;
            }

        }

        return minValue;

    }

    // Max method simulates the player's turn, selecting the method that is best for them
    public double max(int[][] m, Integer times, double alpha, double beta) {

        // Initialise variables
        double maxValue=Double.NEGATIVE_INFINITY;
        times -= 1;

        for (Integer c : nonFullCol(m)) {

            int[][] mtrx = copyMatrix(m);
            int topRow=highestRow(mtrx,c);

            int s = AIScoring.score(mtrx,c,topRow,isRed());

            if (isRed()) {
                mtrx[topRow][c] = 1;
            }
            if (!isRed()) {
                mtrx[topRow][c] = 2;
            }

            double score;


            if (times > 1 && !AIScoring.winCheck(mtrx)) {
                // Call the minimum method
                score = min(mtrx, times, alpha, beta);
            }else if (AIScoring.winCheck(mtrx)){
                score = s * times;
            }else{
                score = AIScoring.boardStateScore(mtrx,isRed());
            }

            // Update the alpha value
            if(score>maxValue){
                maxValue=score;
            }
            if(maxValue>alpha){
                alpha=maxValue;
            }
            // Check for the condition
            if(alpha>=beta){
                break;
            }

        }
        return maxValue;
    }

}


