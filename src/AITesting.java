import java.util.concurrent.atomic.AtomicInteger;


public class AITesting {

    /*

    This class is used to run lots of games between two AI players
    The bulk of the code is adapted from the game class without the GUI components

    To use:

    Alter p1 and p2 to select the algorithms you wish to test
    Select the number of games you wish to run
    Set true or false if you wish to print the board following a win of that player

     */

    public static void main(String[] args) {

        double start = System.currentTimeMillis();

        Player p1 = new Greedy();
        Player p2 = new RandomAI();
        int games = 1000;

        AITesting g = new AITesting(p1,p2,games);

        g.setPrintIfp1Wins(false);
        g.setPrintIfp2Wins(true);

        g.play();

        double end = System.currentTimeMillis();
        double time = (end-start)/1000;
        double timePerGame = time/games;

        System.out.println();
        System.out.println("Games: "+game);
        System.out.println("Red wins: "+redWins);
        System.out.println("Yellow wins: "+yellowWins);
        System.out.println("Draws: "+draws);
        System.out.println("Time elapsed: "+time+"s");
        System.out.println("Average time per game: "+timePerGame+"s");

    }



    Integer games;
    Player r;
    Player y;

    public AITesting(Player red, Player yellow, int games){

        this.r=red;
        this.y=yellow;
        this.games=games;

    }

    boolean printIfp1Wins = false;
    boolean printIfp2Wins = false;

    public void setPrintIfp1Wins(boolean printIfp1Wins) {
        this.printIfp1Wins = printIfp1Wins;
    }

    public void setPrintIfp2Wins(boolean printIfp2Wins) {
        this.printIfp2Wins = printIfp2Wins;
    }

    static int[][] mtrx = new int[6][7];

    Boolean running = true;

    static Integer redWins=0;
    static Integer yellowWins=0;
    static Integer game=0;
    static Integer draws=0;

    Boolean isRed = true;


    public void play() {
        while(games>0) {
            turn();
            System.out.println();
            System.out.println("Games: "+game);
            System.out.println("Red wins: "+redWins);
            System.out.println("Yellow wins: "+yellowWins);
            System.out.println("Draws: "+draws);
            games-=1;
        }
    }


    public void redWin(){

        running=false;
        redWins+=1;

        if(printIfp1Wins){
            print2D(mtrx);
        }

    }

    public void yellowWin(){

        running=false;
        yellowWins+=1;

        if(printIfp2Wins){
            print2D(mtrx);
        }

    }

    public void draw(){

        running=false;
        draws+=1;

    }


    public boolean isSpace(){

        for(int col=0; col<7;col++){

            for (int row=0; row<6; row++){

                if(mtrx[row][col]==0){return true;}

            }

        }

        return false;

    }

    public void winCheck(){

        boolean win = false;

        // Vertical win
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if ((mtrx[5 - row][col]) == (mtrx[4 - row][col]) && (mtrx[4 - row][col]) == (mtrx[3 - row][col]) && (mtrx[3 - row][col]) == (mtrx[2 - row][col])) {

                    if ((mtrx[5 - row][col]) == 1) {
                        redWin();
                        win = true;
                        break;
                    }
                    if ((mtrx[5 - row][col]) == 2) {
                        yellowWin();
                        win = true;
                        break;
                    }
                }
            }
        }



        // Horizontal win
        if(!win) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 4; col++) {
                    if ((mtrx[5 - row][col]) == (mtrx[5 - row][col + 1]) && (mtrx[5 - row][col + 1]) == (mtrx[5 - row][col + 2]) && (mtrx[5 - row][col + 2]) == (mtrx[5 - row][col + 3])) {

                        if ((mtrx[5 - row][col]) == 1) {
                            redWin();
                            win = true;
                            break;
                        }

                        if ((mtrx[5 - row][col]) == 2) {
                            yellowWin();
                            win = true;
                            break;
                        }
                    }
                }
            }
        }



        // Forward Diagonal win

        if(!win) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 4; col++) {
                    if ((mtrx[5 - row][col]) == (mtrx[4 - row][col + 1]) && (mtrx[4 - row][col + 1]) == (mtrx[3 - row][col + 2]) && (mtrx[3 - row][col + 2]) == (mtrx[2 - row][col + 3]) && !win) {

                        if ((mtrx[5 - row][col]) == 1) {
                            redWin();
                            win = true;
                            break;
                        }

                        if ((mtrx[5 - row][col]) == 2) {
                            yellowWin();
                            win = true;
                            break;
                        }
                    }
                }
            }
        }



        // Backward Diagonal win
        if (!win) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 4; col++) {
                    if ((mtrx[row][col]) == (mtrx[row + 1][col + 1]) && (mtrx[row + 1][col + 1]) == (mtrx[row + 2][col + 2]) && (mtrx[row + 2][col + 2]) == (mtrx[row + 3][col + 3]) && !win) {

                        if ((mtrx[row][col]) == 1) {
                            redWin();
                            win = true;
                            break;
                        }

                        if ((mtrx[row][col]) == 2) {
                            yellowWin();
                            win = true;
                            break;
                        }
                    }
                }
            }
        }

        // Draw
        if(!isSpace() && !win){draw();}

    }


    public void playAgain(){

        running=true;

        for(int col=0; col<7; col++){
            for(int row=0; row<6; row++){
                mtrx[row][col]=0;
            }
        }

    }

    AtomicInteger row = new AtomicInteger(0);
    AtomicInteger col = new AtomicInteger(0);

    public void AITurn(){

        // If it is red's turn and red is AI
        if (isRed && !(r instanceof User) && running) {

            r.place(mtrx, true, row, col);
            isRed = false;
            winCheck();

        }

        // If it is yellow's turn and yellow is AI
        else if (!isRed && !(y instanceof User) && running) {

            y.place(mtrx, false, row, col);
            isRed = true;
            winCheck();

        }

        //print2D(mtrx);

    }

    public void turn(){
        game +=1;

        while(running){
            AITurn();
        }

        // For automated simulation
        playAgain();

    }

    public static void print2D(int[][] m) {

        System.out.println();

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

    }

}