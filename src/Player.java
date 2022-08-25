import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public interface Player {

    // Main method for placing a counter, called by the game class:

    int[][] place(int[][] m, boolean isRed, AtomicInteger row, AtomicInteger col);

    // Three methods commonly used in the algorithms:

    default ArrayList<Integer> nonFullCol(int[][] m){

        ArrayList<Integer> nonFullCol = new ArrayList<>();

        for(Integer c=0;c<7;c++){
            for(Integer r=0;r<6;r++){
                if(m[r][c]==0 && !nonFullCol.contains(c)){
                    nonFullCol.add(c);
                }
            }
        }

        return nonFullCol;
    }

    default int[][] copyMatrix(int[][] m){

        int[][] mtrx = new int[6][7];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int t = m[i][j];
                mtrx[i][j] = t;
            }
        }

        return mtrx;
    }

    default int highestRow(int[][] m, int c){

        int topRow = 0;

        for (int r = 0; r<6; r++){
            if(m[r][c]==0){
                topRow=r;
            }
        }

        return topRow;

    }
}