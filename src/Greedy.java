import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Greedy implements Player{

    /*
    The Greedy method selects the best move at the current moment in time without looking ahead
     */

    @Override
    public int[][] place(int[][] m, boolean isRed, AtomicInteger row, AtomicInteger col){

        ArrayList<Integer> hiScorers = new ArrayList<>();
        ArrayList<Integer> hiRows = new ArrayList<>();
        Integer highestScoringCol;
        int highScore=0;
        Integer highRow;
        boolean firstCol=true;

        // For each column...
        for(Integer c: nonFullCol(m)){

            // Find the lowest empty row
            int r = highestRow(m,c);

            // Find the score for placing a counter in this slot
            int s = AIScoring.score(m,c,r,isRed);

            // Compare score to other columns, returning the highest scoring one. If there are multiple with an equal
            // high score then they will be returned in a list
            if(firstCol){
                firstCol=false;
                hiScorers.add(c);
                hiRows.add(r);
                highScore=s;
            }else if(s>highScore){
                hiScorers.clear();
                hiScorers.add(c);
                hiRows.clear();
                hiRows.add(r);
                highScore=s;
            }else if(s==highScore){
                hiScorers.add(c);
                hiRows.add(r);
            }
        }

        // If there is more than one column with the high score, then this selects one of these columns at random
        Random rand = new Random();
        int randomHi = rand.nextInt(hiScorers.size());
        highestScoringCol=(hiScorers.get(randomHi));
        highRow=(hiRows.get(randomHi));

        // Update the atomic integers for the GUI
        col.set(highestScoringCol);
        row.set(5-highRow);

        // Place the counter in the matrix
        if(isRed){m[highRow][highestScoringCol]=1;}
        if(!isRed){m[highRow][highestScoringCol]=2;}

        return m;
    }

    // This method is used in the other algorithms when the scores are equal, returning the best move at the time from
    // those that are equal
    public static int place(int[][] m, boolean isRed, ArrayList<Integer> cols){

        ArrayList<Integer> hiScorers = new ArrayList<>();
        Integer highestScoringCol;
        int highScore=0;
        boolean firstCol=true;

        for(Integer c: cols){

            int topRow = 0;

            for (int r = 0; r<6; r++){
                if(m[r][c]==0){
                    topRow=r;
                }
            }

            int r = topRow;

            int s = AIScoring.score(m,c,r,isRed);

            if(firstCol){
                firstCol=false;
                hiScorers.add(c);
                highScore=s;
            }else if(s>highScore){
                hiScorers.clear();
                hiScorers.add(c);
                highScore=s;
            }else if(s==highScore){
                hiScorers.add(c);
            }
        }

        Random rand = new Random();
        int randomHi = rand.nextInt(hiScorers.size());
        highestScoringCol=(hiScorers.get(randomHi));

        return highestScoringCol;
    }

}
