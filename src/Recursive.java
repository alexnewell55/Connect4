import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Recursive implements Player {

    /*
    The recursive algorithm calls itself x amount of times depending on the depth selected.
    It places a counter in all 7 possible slots and returns the scores for the board state at the end of the search.
     */

    // Initialise the global variables
    Integer recursions;
    Integer player;
    Integer opp;
    Boolean red;

    // Constructor
    public Recursive(Integer recursions) {
        this.recursions = recursions;
    }

    // Setters for the colour of the player/opponent
    public void setPlayer(Integer player) {this.player = player;}
    public void setOpp(Integer opp) {this.opp = opp;}

    // Getters for the amount of recursions and the colours of the player/opponents
    public Integer getRecursions() {
        return recursions;
    }
    public Integer getPlayer(){return player;}
    public Integer getOpp(){return opp;}

    // Getter and setter for whether the player is red
    public void setRed(Boolean red) {this.red = red;}
    public Boolean getRed() {return red;}


    // Main method for placing a counter, called by the game class
    @Override
    public int[][] place(int[][] m, boolean isRed, AtomicInteger row, AtomicInteger col) {

        // Set whether the player is red or yellow
        setRed(isRed);

        // Setting the colours of the player and opponent
        if(isRed){setPlayer(1);setOpp(2);}
        if(!isRed){setPlayer(2);setOpp(1);}

        // Initialising variables for the decision-making

        boolean winAvailable=false;
        Integer winnerCol=null;

        boolean firstCol = true;

        Integer maxValue=null;
        ArrayList<Integer> maxCols = new ArrayList<>();
        int maxColumn;
        int maxRow;

        ArrayList<Integer> columns = nonFullCol(m);
        int s;

        // Here the method checks if a winning move is available before any further action
        for (Integer c:columns){
            Integer topRow = highestRow(m,c);
            if (AIScoring.win(m,c,topRow,getRed())){
                winAvailable=true;
                winnerCol=c;
            }
        }

        // If no win is available...
        if(!winAvailable) {

            // For each non-full column...
            for (Integer c : columns) {
                int topRow = highestRow(m, c);

                // If the depth has not been met
                if (getRecursions()>0) {
                    int[][] mtrx = copyMatrix(m);

                    // Place a counter
                    if (isRed) {
                        mtrx[topRow][c] = 1;
                    }
                    if (!isRed) {
                        mtrx[topRow][c] = 2;
                    }

                    // Call a recursion
                    s = recursion(mtrx, getRecursions(),false);
                } // If depth is met...
                else{
                    s = AIScoring.score(m,c,topRow,getRed());
                }

                // Find highest scoring columns
                if(firstCol){
                    firstCol=false;
                    maxValue=s;
                    maxCols.add(c);
                }else if(s>maxValue){
                    maxCols.clear();
                    maxCols.add(c);
                    maxValue=s;
                }else if(s==maxValue){
                    maxCols.add(c);
                }

            }
            // Pick best column
            maxColumn=Greedy.place(m,isRed,maxCols);
            maxRow=highestRow(m,maxColumn);
        } // Else if a winning move is available, take it...
        else{
            maxColumn=winnerCol;
            maxRow=highestRow(m,maxColumn);
        }

        // Update atomic integers for the GUI
        col.set(maxColumn);
        row.set(5-maxRow);

        // Update game matrix
        if(isRed){m[maxRow][maxColumn]=1;}
        if(!isRed){m[maxRow][maxColumn]=2;}

        return m;

    }

    // Recursion method follows a similar structure
    public int recursion (int[][] m, int remaining, boolean myTurn){

        int s = 0;
        ArrayList<Integer> columns = nonFullCol(m);
        boolean winAvailable=false;
        boolean redTurn;

        // Is it red's turn or yellow's turn?
        if(myTurn && getRed() || !myTurn && !getRed()){
            redTurn=true;
        }else{
            redTurn=false;
        }

        // Check if win is available
        for (Integer c:columns){
            Integer topRow = highestRow(m,c);
            if (AIScoring.win(m,c,topRow,redTurn)){
                winAvailable=true;
            }
        }

        // if win is not available...
        if(!winAvailable){
            for(Integer c:columns) {
                int topRow=highestRow(m,c);
                // If depth has not been met...
                if (remaining > 1) {

                    // Copy matrix and place counter
                    int[][] mtrx = copyMatrix(m);
                    if(redTurn){
                        mtrx[topRow][c] = 1;
                    }else{
                        mtrx[topRow][c] = 2;
                    }

                    // Call recursion
                    s+=recursion(mtrx,remaining-1,!myTurn);

                } else {
                    s+=remaining*AIScoring.boardStateScore(m,getRed());
                }
            }
        } // If winning move available...
        else{
            if(myTurn){s=50000*remaining;}
            else{s=-50000*remaining;}
        }

        return s;
    }



}
