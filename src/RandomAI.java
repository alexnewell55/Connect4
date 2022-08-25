import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomAI implements Player{

    /*
    The random AI simply selects a non-full column at random
     */

    @Override
    public int[][] place(int[][] m, boolean isRed, AtomicInteger row, AtomicInteger col){

        // Select a random non-full column
        Random rand = new Random();
        Integer r = rand.nextInt(nonFullCol(m).size());
        Integer randomCol=nonFullCol(m).get(r);

        // Update atomic integers and find the lowest empty slot
        col.set(randomCol);
        int ro = highestRow(m,randomCol);
        row.set(5-ro);

        // Place in game matrix
        if(isRed){m[ro][randomCol]=1;}
        if(!isRed){m[ro][randomCol]=2;}

        return m;

    }

}