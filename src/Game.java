import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class Game implements ActionListener {

    Player r;
    Player y;
    JFrame f;
    Menu menu;

    public Game(Player red, Player yellow, JFrame frame, Menu menu){

        this.r=red;
        this.y=yellow;
        this.f=frame;
        this.menu=menu;

    }

    // First initialising the non-gui components

    static int[][] mtrx = new int[6][7];

    ArrayList<JButton> board = new ArrayList<>();
    ArrayList<JButton> placed = new ArrayList<>();

    ArrayList<JButton> c0 = new ArrayList<>();
    ArrayList<JButton> c1 = new ArrayList<>();
    ArrayList<JButton> c2 = new ArrayList<>();
    ArrayList<JButton> c3 = new ArrayList<>();
    ArrayList<JButton> c4 = new ArrayList<>();
    ArrayList<JButton> c5 = new ArrayList<>();
    ArrayList<JButton> c6 = new ArrayList<>();

    ArrayList<ArrayList> columns = new ArrayList<>();

    Boolean running = true;
    Boolean isRed = true;

    // Initialise all the Swing GUI components

    JLabel background = new JLabel();

    JLayeredPane lp = new JLayeredPane();
    JPanel p = new JPanel();
    JPanel p2 = new RoundedPanel(50);
    JPanel p3 = new RoundedPanel(50);

    JLabel rWin = new JLabel();
    JLabel yWin = new JLabel();
    JLabel draw = new JLabel();

    Integer redWins=0;
    Integer yellowWins=0;
    JLabel red = new JLabel("Wins: "+redWins, JLabel.CENTER);
    JLabel yellow = new JLabel("Wins: "+yellowWins, JLabel.CENTER);
    JLabel redTurn = new JLabel();
    JLabel yellowTurn = new JLabel();

    JButton playAgain = new RoundButton(40);
    JPanel pa = new RoundedPanel(50);
    JLabel paText = new JLabel();
    JButton back = new RoundButton(40);

    JButton infoBtn = new RoundButton(60);
    JPanel infoBtnBorder = new RoundedPanel(70);
    JLabel question = new JLabel();
    JPanel info = new RoundedPanel(40);
    JLabel infoText = new JLabel("Created by Alex Newell. Artwork by vectorpocket /Freepik.com", JLabel.CENTER);
    JPanel infoBorder = new RoundedPanel(50);

    {

        // Adding the buttons to the array lists for their respective columns
        for(int k=0;k<42;k++){

            JButton b = new RoundButton(91);
            board.add(b);
            if(k%7==0){c0.add(0,b);}
            if(k%7==1){c1.add(0,b);}
            if(k%7==2){c2.add(0,b);}
            if(k%7==3){c3.add(0,b);}
            if(k%7==4){c4.add(0,b);}
            if(k%7==5){c5.add(0,b);}
            if(k%7==6){c6.add(0,b);}

        }

        // Adding the columns to a list
        Collections.addAll(columns,c0,c1,c2,c3,c4,c5,c6);

    }

    // This is the running method of the class, it calls all the main methods within the class
    public void play(){

        setGUI();
        addBoard();
        for(JButton b : board) {
            setButton(b);
        }
        openGUI();
        if(isRed && !(r instanceof User) || !isRed && !(y instanceof User)){
            AITurn();
        }

    }

    // This method sets the parameters for the majority of the GUI components, excluding the game board
    public void setGUI(){

        // Layered pane
        // The layered pane is the base where all the other components will be placed
        {lp.setBounds(0,0,1300,1000);
        lp.setOpaque(true);
        f.add(lp);}

        // Background
        {
            // Different background based on the opponent
            if(y instanceof User) {
                background.setIcon(new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("wall.png"))));
            }else if(y instanceof RandomAI) {
                background.setIcon(new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("table.png"))));
            }else if(y instanceof Greedy) {
                background.setIcon(new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("window.png"))));
            }else{
                background.setIcon(new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("dungeon.png"))));
            }
            background.setVisible(true);
            background.setBounds(0, 0, 1300, 1000);
            lp.add(background, Integer.valueOf(0));
        }

        // Connect 4 header
        {
            // Load image
            ImageIcon con4 = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("Connect4.png")));
            // Set image on label
            JLabel connect4img = new JLabel(con4,JLabel.CENTER);
            // Set bounds for the label
            connect4img.setBounds(425,50,450,100);
            // Add label to the layered pane
            lp.add(connect4img,Integer.valueOf(4));
        }


        // Info button
        {
            info.setBounds(90,890,400,60);
            info.setVisible(false);
            infoText.setBounds(90,890,400,60);
            infoText.setVisible(false);
            infoBorder.setBounds(85,885,410,70);
            infoBorder.setBackground(Color.blue);
            infoBorder.setVisible(false);

            infoBtn.setBounds(10,890,60,60);
            infoBtn.setBackground(Color.white);
            infoBtn.setBorderPainted(false);
            infoBtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    info.setVisible(true);
                    infoText.setVisible(true);
                    infoBorder.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    info.setVisible(false);
                    infoText.setVisible(false);
                    infoBorder.setVisible(false);
                }
            });
            infoBtnBorder.setBounds(5,885,70,70);
            infoBtnBorder.setBackground(Color.blue);
            ImageIcon q = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("question.png")));
            question.setIcon(q);
            question.setHorizontalAlignment(JLabel.CENTER);
            question.setBounds(10,890,60,60);

            lp.add(infoBorder,Integer.valueOf(1));
            lp.add(info, Integer.valueOf(2));
            lp.add(infoText,Integer.valueOf(3));
            lp.add(question, Integer.valueOf(3));
            lp.add(infoBtn, Integer.valueOf(2));
            lp.add(infoBtnBorder, Integer.valueOf(1));

        }

        // End of game messages
        {
            ImageIcon redimg = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("redw.png")));
            rWin.setIcon(redimg);
            rWin.setBounds(425, 300, 450, 300);
            rWin.setVisible(false);

            ImageIcon yellowimg = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("yelloww.png")));
            yWin.setIcon(yellowimg);
            yWin.setBounds(425, 300, 450, 300);
            yWin.setVisible(false);

            ImageIcon drawimg = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("draw.png")));
            draw.setIcon(drawimg);
            draw.setBounds(425, 300, 450, 300);
            draw.setVisible(false);

            lp.add(rWin, Integer.valueOf(4));
            lp.add(yWin, Integer.valueOf(4));
            lp.add(draw, Integer.valueOf(4));
        }

        // Play again button
        {playAgain.setBounds(560,875,180,50);
        playAgain.setBackground(Color.white);
        playAgain.setBorderPainted(false);
        playAgain.setVisible(false);
        playAgain.addActionListener(this);
        pa.setBounds(555,870,190,60);
        pa.setBackground(Color.blue);
        pa.setVisible(false);
        ImageIcon paImg = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("playAgain.png")));
        paText.setIcon(paImg);
        paText.setHorizontalAlignment(JLabel.CENTER);
        paText.setBounds(560,875,180,50);
        paText.setVisible(false);

        lp.add(pa,Integer.valueOf(2));
        lp.add(playAgain,Integer.valueOf(3));
        lp.add(paText,Integer.valueOf(4));}

        // Player label, wins and turn indicator
        {ImageIcon p1 = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("player1.png")));
        JLabel play1 = new JLabel(p1,JLabel.CENTER);
        play1.setBounds(0,200,300,100);
        red.setBounds(0,300,300,50);
        red.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        red.setBackground(Color.white);
        ImageIcon rg = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("redTurn.png")));
        redTurn.setIcon(rg);
        redTurn.setHorizontalTextPosition(JLabel.CENTER);
        redTurn.setBounds(0,400,300,100);

        ImageIcon p2 = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("player2.png")));
        JLabel play2 = new JLabel(p2,JLabel.CENTER);
        play2.setBounds(1000,200,300,100);
        yellow.setBounds(1000,300,300,50);
        yellow.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        yellow.setBackground(Color.white);
        ImageIcon yg = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("yellowTurn.png")));
        yellowTurn.setIcon(yg);
        yellowTurn.setHorizontalTextPosition(JLabel.CENTER);
        yellowTurn.setBounds(1000,400,300,100);
        yellowTurn.setVisible(false);

        lp.add(play1,Integer.valueOf(1));
        lp.add(red,Integer.valueOf(1));
        lp.add(redTurn,Integer.valueOf(1));
        lp.add(play2,Integer.valueOf(1));
        lp.add(yellow,Integer.valueOf(1));
        lp.add(yellowTurn,Integer.valueOf(1));}

        // Return to menu button
        {back.setBounds(1000,875,200,50);
        back.setBackground(Color.white);
        back.setBorderPainted(false);
        back.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        back.addActionListener(this);
        JPanel backBorder = new RoundedPanel(50);
        backBorder.setBounds(995,870,210,60);
        backBorder.setBackground(Color.blue);
        ImageIcon menuText = new ImageIcon(Objects.requireNonNull(Game.class.getClassLoader().getResource("menu.png")));
        JLabel returnToMenu = new JLabel(menuText,JLabel.CENTER);
        returnToMenu.setBounds(1000,875,200,50);
        lp.add(returnToMenu,Integer.valueOf(3));
        lp.add(back,Integer.valueOf(2));
        lp.add(backBorder,Integer.valueOf(1));}

    }

    // Open the GUI, only after it has been fully set up
    public void openGUI(){

        f.setVisible(true);

    }

    // Adding the game board to the layered pane
    public void addBoard(){

        p3.setBounds(300,197,720,620);
        p3.setBackground(Color.black);

        p2.setBounds(290,190,720,620);
        p2.setBackground(Color.blue);

        p.setBounds(300,200,700,600);
        p.setBackground(Color.blue);
        // This is the grid that the buttons will sit in
        p.setLayout(new GridLayout(6,7,10,10));

        lp.add(p3,Integer.valueOf(1));
        lp.add(p2,Integer.valueOf(2));
        lp.add(p,Integer.valueOf(3));

    }

    // Setting the conditions for the buttons on the board
    public void setButton(JButton b){

        p.add(b);
        b.setBackground(Color.white);
        b.setBorderPainted(false);
        b.addActionListener(this);

        // This highlights the button that the cursor hovers over in the colour of the player
        b.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(b.getBackground()==Color.white && running){
                    if(isRed){b.setBackground(Color.red);}
                    else{b.setBackground(Color.yellow);}
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(!placed.contains(b)) {
                    b.setBackground(Color.white);
                }
            }
        });

    }

    // Method for the user to place a counter
    public void placeCounter(Object source){

        int row = 0;
        int col = 0;
        boolean checking = true;

        // If it is the turn of a user...
        if (isRed && r instanceof User || !isRed && y instanceof User) {
            // If the button clicked is empty...
            if (!placed.contains((JButton) source)) {
                // For each column...
                for (ArrayList c : columns) {
                    // If the button clicked is in this column...
                    if (c.contains(source)) {
                        // For each row...
                        for (int i = 0; i < c.size(); i++) {
                            // If this is the lowest empty row...
                            if (!placed.contains((JButton) c.get(i)) && checking) {
                                // If it is red's turn...
                                if (isRed) {
                                    // Place counter, check for win and update the GUI
                                    mtrx[5 - row][col] = 1;
                                    winCheck();
                                    ((JButton) c.get(i)).setBackground(Color.RED);

                                    // Changes the colour of the highlighted counter since it is now yellow's turn
                                    if (!(source == c.get(i)) && y instanceof User) {
                                        ((JButton) source).setBackground(Color.yellow);
                                    }

                                    // Add the button to the list of placed counters
                                    placed.add((JButton) c.get(i));
                                    // Update turn tracker
                                    isRed = false;
                                    // Change turn indicator label
                                    redTurn.setVisible(false);
                                    yellowTurn.setVisible(true);
                                    // Stop looking for empty slots
                                    checking = false;

                                    // Triggers AI turn, which only runs if teh opponent is not a user
                                    turn();

                                } // Else, it is yellow's turn so do the opposite
                                else {

                                    mtrx[5 - row][col] = 2;
                                    winCheck();
                                    ((JButton) c.get(i)).setBackground(Color.YELLOW);

                                    if (!(source == c.get(i)) && r instanceof User) {
                                        ((JButton) source).setBackground(Color.red);
                                    }

                                    placed.add((JButton) c.get(i));
                                    isRed = true;
                                    redTurn.setVisible(true);
                                    yellowTurn.setVisible(false);
                                    checking = false;

                                    turn();

                                }
                            }
                            row += 1;

                        }
                    }
                    col += 1;
                }
            }
        }
    }

    // Method for when the red player wins
    public void redWin(){

        // Red win message and play again button appear
        rWin.setVisible(true);
        pa.setVisible(true);
        playAgain.setVisible(true);
        paText.setVisible(true);

        // Remove function from teh buttons
        for(JButton b : board){

            b.removeActionListener(this);

        }
        // Stop the running
        running=false;
        // Increment the wins and update the counter on the GUI
        redWins+=1;
        red.setText("Wins: "+redWins);

    }

    // Yellow win method, same as the red
    public void yellowWin(){

        yWin.setVisible(true);
        pa.setVisible(true);
        playAgain.setVisible(true);
        paText.setVisible(true);

        for(JButton b : board){

            b.removeActionListener(this);

        }
        running=false;
        yellowWins+=1;
        yellow.setText("Wins: "+yellowWins);

    }

    // Draw method, similar to the winning methods
    public void draw(){

        draw.setVisible(true);
        pa.setVisible(true);
        playAgain.setVisible(true);
        paText.setVisible(true);

        for(JButton b : board){

            b.removeActionListener(this);

        }
        running=false;

    }

    // This method checks if there is space left in the board, responsible for calling a draw
    public boolean isSpace(){

        for(int col=0; col<7;col++){
            for (int row=0; row<6; row++){
                if(mtrx[row][col]==0){return true;}
            }
        }

        return false;

    }

    // This method checks the board for a win/draw
    public void winCheck(){

        boolean win = false;

        /*
        The method works by checking every possible row of 4 on the board and checking if all the counters in that
        4 are the same colour. There is a separate check for all the different ways of winning.
         */

        // Vertical win

        for(int col=0; col<7;col++){
            for (int row=0; row<3; row++){
                if((mtrx[5-row][col])==(mtrx[4-row][col]) && (mtrx[4-row][col])==(mtrx[3-row][col]) && (mtrx[3-row][col])==(mtrx[2-row][col])){

                    if ((mtrx[5-row][col])==1){redWin();win=true;}
                    if ((mtrx[5-row][col])==2){yellowWin();win=true;}

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
                        }
                        if ((mtrx[5 - row][col]) == 2) {
                            yellowWin();
                            win = true;
                        }

                    }
                }
            }
        }

        // Forward Diagonal win
        if(!win) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 4; col++) {
                    if ((mtrx[5 - row][col]) == (mtrx[4 - row][col + 1]) && (mtrx[4 - row][col + 1]) == (mtrx[3 - row][col + 2]) && (mtrx[3 - row][col + 2]) == (mtrx[2 - row][col + 3])) {

                        if ((mtrx[5 - row][col]) == 1) {
                            redWin();
                            win=true;
                        }
                        if ((mtrx[5 - row][col]) == 2) {
                            yellowWin();
                            win=true;
                        }

                    }
                }
            }
        }

        // Backward Diagonal win
        if(!win) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 4; col++) {
                    if ((mtrx[row][col]) == (mtrx[row + 1][col + 1]) && (mtrx[row + 1][col + 1]) == (mtrx[row + 2][col + 2]) && (mtrx[row + 2][col + 2]) == (mtrx[row + 3][col + 3])) {

                        if ((mtrx[row][col]) == 1) {
                            redWin();
                            win = true;
                        }
                        if ((mtrx[row][col]) == 2) {
                            yellowWin();
                            win = true;
                        }

                    }
                }
            }
        }

        // Draw

        if(!isSpace() && !win){draw();}

    }

    // The play again method called by clicking the play again button
    public void playAgain(){

        running=true;

        // Reset the game matrix
        for(int col=0; col<7; col++){
            for(int row=0; row<6; row++){
                mtrx[row][col]=0;
            }
        }

        // Reset the GUI board
        for(JButton b : board){
            b.setBackground(Color.white);
            b.addActionListener(this);
        }

        rWin.setVisible(false);
        yWin.setVisible(false);
        draw.setVisible(false);
        playAgain.setVisible(false);
        pa.setVisible(false);
        paText.setVisible(false);

        // Clear the list of placed counters
        placed.clear();

    }

    // Atomic integer that are altered by the AI turn to update the GUI
    AtomicInteger row = new AtomicInteger(0);
    AtomicInteger col = new AtomicInteger(0);

    // The turn of an AI
    public void AITurn(){

        if(running){

            // If it is red's turn and red is AI
            if (isRed && !(r instanceof User)) {
                // Call the place method of the player
                r.place(mtrx, true, row, col);
                // Standard procedure for a turn
                isRed = false;
                redTurn.setVisible(false);
                yellowTurn.setVisible(true);
                // Updating the GUI using the atomic integers
                ArrayList clm = columns.get(col.get());
                JButton but = (JButton) clm.get(row.get());
                but.setBackground(Color.red);
                placed.add(but);
                winCheck();
            }

            // If it is yellow's turn and yellow is AI

            else if (!isRed && !(y instanceof User)) {
                y.place(mtrx, false, row, col);
                isRed = true;
                redTurn.setVisible(true);
                yellowTurn.setVisible(false);
                ArrayList clm = columns.get(col.get());
                JButton but = (JButton) clm.get(row.get());
                but.setBackground(Color.yellow);
                placed.add(but);
                winCheck();
            }

            turn();

        }

    }

    // Calls an AI move if it is the AI's turn
    public void turn(){

        if(isRed && !(r instanceof User) || !isRed && !(y instanceof User)){

            AITurn();

        }

    }



    // Method for providing functionality to the buttons
    @Override
    public void actionPerformed(ActionEvent e) {

        // Obtaining the source button
        Object source = e.getSource();

        // If the button is one of the counters...
        if(board.contains((JButton) source)) {
            placeCounter(source);
        }

        // If it is the play again button...
        if(source==playAgain){
            playAgain();
        }

        // If it is the back button...
        if(source==back){
            for(int col=0; col<7; col++){
                for(int row=0; row<6; row++){

                    mtrx[row][col]=0;

                }
            }
            running=false;
            // Return to menu
            lp.setVisible(false);
            menu.makeVisible();
        }

        // Following the click of any button, if it is the AI's turn it will take it
        if(isRed && !(r instanceof User) || !isRed && !(y instanceof User)){
            turn();
        }

    }


}