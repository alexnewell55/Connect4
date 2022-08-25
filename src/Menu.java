import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Menu implements ActionListener {

        // Initialising all the GUI components

        JFrame f = new JFrame();
        JLayeredPane lp = new JLayeredPane();

        JButton sp = new RoundButton(40);
        JPanel spBorder = new RoundedPanel(50);
        JLabel spMode = new JLabel();
        JButton tp = new RoundButton(40);
        JPanel tpBorder = new RoundedPanel(50);
        JLabel tpMode = new JLabel();
        JButton exit = new RoundButton(40);
        JPanel exitBorder = new RoundedPanel(50);
        JLabel ex = new JLabel();

        JButton beg = new RoundButton(40);
        JPanel begBorder = new RoundedPanel(50);
        JLabel begText = new JLabel();
        JButton med = new RoundButton(40);
        JPanel medBorder = new RoundedPanel(50);
        JLabel medText = new JLabel();
        JButton exp = new RoundButton(40);
        JPanel expBorder = new RoundedPanel(50);
        JLabel expText = new JLabel();
        JLabel sdimg = new JLabel();
        JButton back = new RoundButton(40);
        JPanel backBorder = new RoundedPanel(50);
        JLabel backText = new JLabel();

        JButton infoBtn = new RoundButton(60);
        JPanel infoBtnBorder = new RoundedPanel(70);
        JLabel question = new JLabel();
        JPanel info = new RoundedPanel(40);
        JLabel infoText = new JLabel("Created by Alex Newell. Artwork by vectorpocket /Freepik.com", JLabel.CENTER);
        JPanel infoBorder = new RoundedPanel(50);

    // The runner class
    public void run(){
        setMenu();
        openMenu();
    }


    // This method sets the parameters for all the GUI components
    public void setMenu(){

        // Frame
        {f.setSize(new Dimension(1300,1000));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setTitle("Connect 4");}

        // Layered pane
        {lp.setBounds(0,0,1300,1000);
        lp.setOpaque(true);
        f.add(lp);}

        // Background
        {
            ImageIcon bgImg = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("castle.png")));
            JLabel bg = new JLabel(bgImg);
            bg.setBounds(0,0,1300,1000);
            lp.add(bg,Integer.valueOf(0));
        }

        // Connect 4 header
        {ImageIcon con4 = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("connect4large.png")));
        JLabel connect4img = new JLabel(con4,JLabel.CENTER);
        connect4img.setBounds(200,100,900,250);
        lp.add(connect4img,Integer.valueOf(1));}

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

        // Single-player button
        {sp.setBounds(435,535,430,100);
        sp.setBackground(Color.white);
        sp.setBorderPainted(false);
        sp.addActionListener(this);
        spBorder.setBounds(425,525,450,120);
        spBorder.setBackground(Color.blue);
        ImageIcon spM = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("spMode.png")));
        spMode.setIcon(spM);
        spMode.setHorizontalAlignment(JLabel.CENTER);
        spMode.setBounds(435,535,430,100);
        lp.add(spMode,Integer.valueOf(3));
        lp.add(spBorder,Integer.valueOf(1));
        lp.add(sp,Integer.valueOf(2));}

        // Two-player button
        {tp.setBounds(450,385,400,100);
        tp.setBackground(Color.white);
        tp.setBorderPainted(false);
        tp.addActionListener(this);
        tpBorder.setBounds(440,375,420,120);
        tpBorder.setBackground(Color.blue);
        ImageIcon tpM = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("tpMode.png")));
        tpMode.setIcon(tpM);
        tpMode.setHorizontalAlignment(JLabel.CENTER);
        tpMode.setBounds(410,385,480,100);
        lp.add(tpMode,Integer.valueOf(3));
        lp.add(tpBorder,Integer.valueOf(1));
        lp.add(tp,Integer.valueOf(2));}

        // Exit button
        {exit.setBounds(570,685,160,100);
        exit.setBackground(Color.white);
        exit.setBorderPainted(false);
        exit.setFont(new Font("Comic Sans MS",Font.BOLD,45));
        exit.addActionListener(this);
        exitBorder.setBounds(560,675,180,120);
        exitBorder.setBackground(Color.blue);
        ImageIcon exitImg = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("exit.png")));
        ex.setIcon(exitImg);
        ex.setHorizontalAlignment(JLabel.CENTER);
        ex.setBounds(570,685,160,100);
        lp.add(ex,Integer.valueOf(3));
        lp.add(exitBorder,Integer.valueOf(1));
        lp.add(exit,Integer.valueOf(2));}

        // Difficulty label
        {ImageIcon sd = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("difficulty.png")));
            sdimg.setIcon(sd);
            sdimg.setHorizontalAlignment(JLabel.CENTER);
            sdimg.setBounds(200,250,900,250);
            sdimg.setVisible(false);
            lp.add(sdimg,Integer.valueOf(1));}

        // Beginner button
        {beg.setBounds(515,435,270,100);
        beg.setBackground(Color.white);
        beg.setBorderPainted(false);
        beg.addActionListener(this);
        begBorder.setBounds(505,425,290,120);
        begBorder.setBackground(Color.blue);
        ImageIcon begImg = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("beginner.png")));
        begText.setIcon(begImg);
        begText.setHorizontalAlignment(JLabel.CENTER);
        begText.setBounds(515,435,270,100);
        beg.setVisible(false);
        begBorder.setVisible(false);
        begText.setVisible(false);
        lp.add(begText,Integer.valueOf(3));
        lp.add(begBorder,Integer.valueOf(1));
        lp.add(beg,Integer.valueOf(2));}

        // Intermediate button
        {med.setBounds(475,585,350,100);
        med.setBackground(Color.white);
        med.setBorderPainted(false);
        med.addActionListener(this);
        medBorder.setBounds(465,575,370,120);
        medBorder.setBackground(Color.blue);
        ImageIcon medImg = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("intermediate.png")));
        medText.setIcon(medImg);
        medText.setHorizontalAlignment(JLabel.CENTER);
        medText.setBounds(475,585,350,100);
        med.setVisible(false);
        medBorder.setVisible(false);
        medText.setVisible(false);
        lp.add(medText,Integer.valueOf(3));
        lp.add(medBorder,Integer.valueOf(1));
        lp.add(med,Integer.valueOf(2));}

        // Expert button
        {exp.setBounds(550,735,200,100);
        exp.setBackground(Color.white);
        exp.setBorderPainted(false);
        exp.setFont(new Font("Comic Sans MS",Font.BOLD,45));
        exp.addActionListener(this);
        expBorder.setBounds(540,725,220,120);
        expBorder.setBackground(Color.blue);
        ImageIcon expImg = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("expert.png")));
        expText.setIcon(expImg);
        expText.setHorizontalAlignment(JLabel.CENTER);
        expText.setBounds(550,735,200,100);
        exp.setVisible(false);
        expBorder.setVisible(false);
        expText.setVisible(false);
        lp.add(expText,Integer.valueOf(3));
        lp.add(expBorder,Integer.valueOf(1));
        lp.add(exp,Integer.valueOf(2));}

        // Back button
        {back.setBounds(575,875,150,50);
            back.setBackground(Color.white);
            back.setBorderPainted(false);
            back.setFont(new Font("Comic Sans MS",Font.BOLD,45));
            back.addActionListener(this);
            backBorder.setBounds(570,870,160,60);
            backBorder.setBackground(Color.blue);
            ImageIcon backImg = new ImageIcon(Objects.requireNonNull(Menu.class.getClassLoader().getResource("back.png")));
            backText.setIcon(backImg);
            backText.setHorizontalAlignment(JLabel.CENTER);
            backText.setBounds(575,875,150,50);
            back.setVisible(false);
            backBorder.setVisible(false);
            backText.setVisible(false);
            lp.add(backText,Integer.valueOf(3));
            lp.add(backBorder,Integer.valueOf(1));
            lp.add(back,Integer.valueOf(2));}
    }

    // Method for opening the menu, after it has been set up
    public void openMenu(){
        f.setVisible(true);
    }

    // Launches the correct game based on the input parameter
    public void gameMode(String opp){
        Player p1 = new User();
        Player p2 = null;
        if(Objects.equals(opp, "user")){
            p2=new User();
        }else if(Objects.equals(opp, "beg")){
            p2=new RandomAI();
        }else if(Objects.equals(opp, "med")){
            p2=new Greedy();
        }else if(Objects.equals(opp, "exp")){
            p2=new ABPruning(5);
        }
        Game g = new Game(p1,p2,f,this);
        showMainButtons();
        lp.setVisible(false);
        g.play();
    }

    // A method for making the menu visible, called from within the game class
    public void makeVisible(){
        lp.setVisible(true);
    }

    // This method changes the menu from the main menu to the difficulty select for single-player
    public void showSpButtons(){
        sp.setVisible(false);
        spBorder.setVisible(false);
        spMode.setVisible(false);
        tp.setVisible(false);
        tpBorder.setVisible(false);
        tpMode.setVisible(false);
        exit.setVisible(false);
        exitBorder.setVisible(false);
        ex.setVisible(false);

        beg.setVisible(true);
        begBorder.setVisible(true);
        begText.setVisible(true);
        med.setVisible(true);
        medBorder.setVisible(true);
        medText.setVisible(true);
        exp.setVisible(true);
        expBorder.setVisible(true);
        expText.setVisible(true);
        sdimg.setVisible(true);
        back.setVisible(true);
        backBorder.setVisible(true);
        backText.setVisible(true);
    }

    // This method returns to the main menu from the difficulty menu
    public void showMainButtons(){
        sp.setVisible(true);
        spBorder.setVisible(true);
        spMode.setVisible(true);
        tp.setVisible(true);
        tpBorder.setVisible(true);
        tpMode.setVisible(true);
        exit.setVisible(true);
        exitBorder.setVisible(true);
        ex.setVisible(true);

        beg.setVisible(false);
        begBorder.setVisible(false);
        begText.setVisible(false);
        med.setVisible(false);
        medBorder.setVisible(false);
        medText.setVisible(false);
        exp.setVisible(false);
        expBorder.setVisible(false);
        expText.setVisible(false);
        sdimg.setVisible(false);
        back.setVisible(false);
        backBorder.setVisible(false);
        backText.setVisible(false);
    }

    // Method for providing functionality to all the buttons
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source==sp){
            showSpButtons();
        }
        if(source==tp){
            gameMode("user");
        }
        if(source==exit){
            f.dispose();
        }
        if(source==back){
            showMainButtons();
        }
        if(source==beg){
            gameMode("beg");
        }
        if(source==med){
            gameMode("med");
        }
        if(source==exp){
            gameMode("exp");
        }

    }


}
