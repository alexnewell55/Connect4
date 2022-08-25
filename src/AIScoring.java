import java.util.ArrayList;

public class AIScoring {

    /*

    Scoring system for each move:
    Win = 50000
    Block opponent win = 2000
    Set up loss = -10000
    Three in a row = 100
    Two in a row = 20
    Middle col = 30

     */


    // Score method checks the board for the different scoring possibilities, then alters the total score accordingly
    static int score(int[][] m, int col, int row, boolean isRed){
        int s = 0;

        s+=colPoints(col);

        if (win(m,col,row,isRed)){
            s+=50000;
        }

        if(blockOpp(m,col,row,isRed)){
            s+=2000;
        }

        if(setUpLoss(m,col,row,isRed)){
            s-=10000;
        }

        if(twoInARow(m,col,row,isRed)){
            s+=20;
        }

        if(threeInARow(m,col,row,isRed)){
            s+=100;
        }

        return s;
    }

    // This method scores the current board state, rather than simulating placing a counter.
    static int boardStateScore(int[][] m, boolean isRed){
        ArrayList<Integer> columns = nonFullCol(m);

        int playerTwos=0;
        int playerThrees=0;
        int oppTwos=0;
        int oppThrees=0;

        int s;

        for(Integer c:columns){
            int topRow = highestRow(m,c);
            if(twoCheck(m,c,topRow,isRed)){
                playerTwos+=1;
            }
            if(threeCheck(m,c,topRow,isRed)){
                playerThrees+=1;
            }
            if(twoCheck(m,c,topRow,!isRed)){
                oppTwos+=1;
            }
            if(threeCheck(m,c,topRow,!isRed)){
                oppThrees+=1;
            }
        }

        s = playerTwos*20+playerThrees*100-oppTwos*20-oppThrees*100;

        return s;
    }

    // Returns a score based on which column is selected
    public static int colPoints(int col){
        if(col==3){
            return 30;
        }else if(col==2 || col==4){
            return 3;
        }else if(col==1 || col==5){
            return 2;
        }else{
            return 1;
        }
    }

    // Place a counter and then check for a win
    public static boolean win(int[][] m, int c, int r, boolean isRed){

        int[][] mtrx = copyMatrix(m);

        if(isRed){mtrx[r][c]=1;}
        if(!isRed){mtrx[r][c]=2;}

        return winCheck(mtrx);
    }

    // Place a counter of the opponents colour and then check for a win
    public static boolean blockOpp(int[][] m, int c, int r, boolean isRed){
        int[][] mtrx = copyMatrix(m);

        if(isRed){mtrx[r][c]=2;}
        if(!isRed){mtrx[r][c]=1;}

        return winCheck(mtrx);
    }

    // Checking the board for a win
    public static boolean winCheck(int[][] mtrx) {

        boolean win = false;

        // Vertical win

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if ((mtrx[5 - row][col]) == (mtrx[4 - row][col]) && (mtrx[4 - row][col]) == (mtrx[3 - row][col]) && (mtrx[3 - row][col]) == (mtrx[2 - row][col])) {

                    if(mtrx[5-row][col]==1 || mtrx[5-row][col]==2) {
                        win = true;
                    }

                }
            }
        }


        // Horizontal win

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if ((mtrx[5 - row][col]) == (mtrx[5 - row][col + 1]) && (mtrx[5 - row][col + 1]) == (mtrx[5 - row][col + 2]) && (mtrx[5 - row][col + 2]) == (mtrx[5 - row][col + 3])) {

                    if(mtrx[5-row][col]==1 || mtrx[5-row][col]==2) {
                        win = true;
                    }

                }
            }
        }


        // Forward Diagonal win

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if ((mtrx[5 - row][col]) == (mtrx[4 - row][col + 1]) && (mtrx[4 - row][col + 1]) == (mtrx[3 - row][col + 2]) && (mtrx[3 - row][col + 2]) == (mtrx[2 - row][col + 3])) {

                    if(mtrx[5-row][col]==1 || mtrx[5-row][col]==2) {
                        win = true;
                    }

                }
            }
        }


        // Backward Diagonal win

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if ((mtrx[row][col]) == (mtrx[row + 1][col + 1]) && (mtrx[row + 1][col + 1]) == (mtrx[row + 2][col + 2]) && (mtrx[row + 2][col + 2]) == (mtrx[row + 3][col + 3])) {

                    if(mtrx[row][col]==1 || mtrx[row][col]==2) {
                        win = true;
                    }

                }
            }
        }

        return win;
    }

    // Place a counter, then place the opponents colour on top and check for a win
    public static boolean setUpLoss(int[][] m, int c, int r, boolean isRed){

        int[][] mtrx = copyMatrix(m);

        if(isRed){
            mtrx[r][c]=1;
            if(r>0) {
                mtrx[r - 1][c] = 2;
            }
        }
        if(!isRed){
            mtrx[r][c]=2;
            if(r>0) {
                mtrx[r - 1][c] = 1;
            }
        }

        return winCheck(mtrx);
    }

    // Placing a counter then checking for two in a row
    public static boolean twoInARow(int[][] m, int c, int r, boolean isRed){

        int[][] mtrx = copyMatrix(m);

        if(isRed){mtrx[r][c]=1;}
        if(!isRed){mtrx[r][c]=2;}

        return twoCheck(mtrx,c,r,isRed);
    }

    // Checking for a two in a row
    // This works by checking for a four, but with two 0s and either two 1s or 2s
    public static boolean twoCheck(int[][]mtrx, int column, int r, boolean isRed){

        boolean two =false;
        int target;

        if(isRed){target=1;}
        else{target=2;}

        // Vertical two

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {

                int a = mtrx[5-row][col];
                int b = mtrx[4-row][col];
                int c = mtrx[3-row][col];
                int d = mtrx[2-row][col];

                if(column==col && (r==row || r==row+1 || r==row+2 || r==row+3)){

                    if (a == target && b == target && c==0 && d == 0 ||
                        a == target && b == 0 && c==target && d == 0 ||
                        a == target && b == 0 && c==0 && d == target ||
                        a == 0 && b == target && c==target && d == 0 ||
                        a == 0 && b == target && c==0 && d == target ||
                        a == 0 && b == 0 && c==target && d == target) {

                            two=true;

                    }

                }
            }
        }

        // Horizontal two

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 6; row++) {

                int a = mtrx[row][col];
                int b = mtrx[row][col+1];
                int c = mtrx[row][col+2];
                int d = mtrx[row][col+3];

                if((column==col || column==col+1 || column==col+2 || column==col+3) && r==row) {

                    if (a == target && b == target && c==0 && d == 0 ||
                            a == target && b == 0 && c==target && d == 0 ||
                            a == target && b == 0 && c==0 && d == target ||
                            a == 0 && b == target && c==target && d == 0 ||
                            a == 0 && b == target && c==0 && d == target ||
                            a == 0 && b == 0 && c==target && d == target) {

                        two = true;

                    }
                }
            }
        }

        // Forward diagonal two

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {

                int a = mtrx[5-row][col];
                int b = mtrx[4-row][col+1];
                int c = mtrx[3-row][col+2];
                int d = mtrx[2-row][col+3];

                if((column==col && r==5-row) || (column==col+1 && r==4-row) || (column==col+2 && r==3-row) || (column==col+3 && r==2-row)) {

                    if (a == target && b == target && c==0 && d == 0 ||
                            a == target && b == 0 && c==target && d == 0 ||
                            a == target && b == 0 && c==0 && d == target ||
                            a == 0 && b == target && c==target && d == 0 ||
                            a == 0 && b == target && c==0 && d == target ||
                            a == 0 && b == 0 && c==target && d == target) {

                        two = true;

                    }
                }
            }
        }

        // Backward diagonal two

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {

                int a = mtrx[row][col];
                int b = mtrx[row+1][col+1];
                int c = mtrx[row+2][col+2];
                int d = mtrx[row+3][col+3];

                if((column==col && r==row) || (column==col+1 && r==row+1) || (column==col+2 && r==row+2) || (column==col+3 && r==row+3)) {

                    if (a == target && b == target && c==0 && d == 0 ||
                            a == target && b == 0 && c==target && d == 0 ||
                            a == target && b == 0 && c==0 && d == target ||
                            a == 0 && b == target && c==target && d == 0 ||
                            a == 0 && b == target && c==0 && d == target ||
                            a == 0 && b == 0 && c==target && d == target) {

                        two = true;

                    }
                }
            }
        }

        return two;
    }

    // Placing a counter then checking for a three in a row
    public static boolean threeInARow(int[][] m, int c, int r, boolean isRed){
        int[][] mtrx = copyMatrix(m);

        if(isRed){mtrx[r][c]=1;}
        if(!isRed){mtrx[r][c]=2;}

        return threeCheck(mtrx,c,r,isRed);    }

    // Checking for a three in a row
    // This works by checking for a four, but with one 0 and either three 1s or 2s
    public static boolean threeCheck(int[][]mtrx, int column, int r, boolean isRed){

        boolean three =false;
        int target=0;

        if(isRed){target=1;}
        if(!isRed){target=2;}

        // Vertical three

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {

                int a = mtrx[5-row][col];
                int b = mtrx[4-row][col];
                int c = mtrx[3-row][col];
                int d = mtrx[2-row][col];

                if(column==col && (r==row || r==row+1 || r==row+2 || r==row+3)){

                    if (a == target && b == target && c==target && d == 0 ||
                            a == target && b == target && c==0 && d == target ||
                            a == target && b == 0 && c==target && d == target ||
                            a == 0 && b == target && c==target && d == target) {

                        three=true;

                    }

                }
            }
        }

        // Horizontal three

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 6; row++) {

                int a = mtrx[row][col];
                int b = mtrx[row][col+1];
                int c = mtrx[row][col+2];
                int d = mtrx[row][col+3];

                if((column==col || column==col+1 || column==col+2 || column==col+3) && r==row) {

                    if (a == target && b == target && c==target && d == 0 ||
                            a == target && b == target && c==0 && d == target ||
                            a == target && b == 0 && c==target && d == target ||
                            a == 0 && b == target && c==target && d == target) {

                        three = true;

                    }
                }
            }
        }

        // Forward diagonal three

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {

                int a = mtrx[5-row][col];
                int b = mtrx[4-row][col+1];
                int c = mtrx[3-row][col+2];
                int d = mtrx[2-row][col+3];

                if((column==col && r==5-row) || (column==col+1 && r==4-row) || (column==col+2 && r==3-row) || (column==col+3 && r==2-row)) {

                    if (a == target && b == target && c==target && d == 0 ||
                            a == target && b == target && c==0 && d == target ||
                            a == target && b == 0 && c==target && d == target ||
                            a == 0 && b == target && c==target && d == target) {

                        three = true;

                    }
                }
            }
        }

        // Backward diagonal three

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {

                int a = mtrx[row][col];
                int b = mtrx[row+1][col+1];
                int c = mtrx[row+2][col+2];
                int d = mtrx[row+3][col+3];

                if((column==col && r==row) || (column==col+1 && r==row+1) || (column==col+2 && r==row+2) || (column==col+3 && r==row+3)) {

                    if (a == target && b == target && c==target && d == 0 ||
                            a == target && b == target && c==0 && d == target ||
                            a == target && b == 0 && c==target && d == target ||
                            a == 0 && b == target && c==target && d == target) {

                        three = true;

                    }
                }
            }
        }

        return three;
    }

    public static ArrayList<Integer> nonFullCol(int[][] m){

        ArrayList<Integer> nonFullCol = new ArrayList<>();

        for(Integer c=0;c<7;c++){
            for(int r=0;r<6;r++){
                if(m[r][c]==0 && !nonFullCol.contains(c)){
                    nonFullCol.add(c);
                }
            }
        }

        return nonFullCol;
    }
    public static int highestRow(int[][] m, int c){

        int topRow = 0;

        for (int r = 0; r<6; r++){
            if(m[r][c]==0){
                topRow=r;
            }
        }

        return topRow;

    }
    public static int[][] copyMatrix(int[][] m){

        int[][] mtrx = new int[6][7];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int t = m[i][j];
                mtrx[i][j] = t;
            }
        }

        return mtrx;
    }

}
