import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.awt.event.*;

/**
 *  Title      : StratFrame.java
 *  Description: This class is the start interface of the Wordle game
 *  which is used for select mode and check rules
 * 
 *  In this version, I added 3 modes for players.
 *  @author  Jin Si
 *  @version 1.0
 * 
 *  In this version, I added rules label and setToolTipText() for players 
 *  to know the difference between differnt modes 
 *  @author  Jin Si
 *  @version 2.0
 * 
 *  In this version, I added the beautiful wordle title!
 *  @author  Jin Si
 *  @version 2.1
 */

public class StartFrame extends JFrame{
    
    private JPanel startPanel = new JPanel();
    //Wordle Title
    private JLabel BigTitle = new JLabel();
    //Three mode buttons
    private JButton easyMode = new JButton("Easy Mode");
    private JButton learnMode = new JButton("English-learning Mode");
    private JButton hardMode = new JButton("Hard Mode");
    private JLabel rulesLabel = new JLabel("Rules");
    //Jwindow use to show the rules
    private JWindow RulesWin = new JWindow();
    private String easyTips = "Easy Mode, what you enter doesn't need to be a word ";
    private String learnTips = "English-learning Mode, what you enter must be a word and there wil be Chinese meaning after you finish the game";
    private String hardTips = "Hard Mode, what you enter must be a word and cannot enter the word not in the word which you have tired before";
    //Rules text shows in the JWindow
    private JTextArea rules = new JTextArea(
    "                                     Welcome to Jin's Wordle Game!\n"+
    "There are three modes of my Wordle game. If you want to know the difference,\njust put your cursor lingering over the mode button.\n"+
    "\n"+
    "The basic rules are listed as follows:\n"+
    "1.You will guess a 5-letter word\n"+
    "2.You have to enter a 5-letter word with physical or virtual keyboard. \n"+
    "3.If you want to mix the way you typing in your word, click the input box before you use physcial keyboard.\n"+
    "4.Green means the letter is contained in the word and is in that position.\n"+
    "5.Yellow means the letter is contained in the word but not in that position.\n"+
    "6.Grey means the letter is not contained in the word.\n"+
    "7.Make sure you have fun!!");
    //static for other file to access
    protected static int mode = 0; //1-easy mode，//2-learning mode //3-hard mode

    /** 
     * This constructor sets up the layout and game title in the start GUI
     */
    public StartFrame(){
        super("Welcome to Jin's Wordle Game");
        startPanel.setLayout(null);     //To design my own layout
        this.setContentPane(startPanel);
        this.startLayout();            
        this.addFcn();      //Add event to rules label and three mode buttons
    }

    /** 
     * This method set up the layout of all the components in 
     */
    public void startLayout(){
        //Add three mode buttons and set the font and location
        this.add(easyMode);
        this.add(hardMode);
        this.add(learnMode);
        easyMode.setBounds(5,235, 100, 40);
        easyMode.setFont( new FontUIResource("Arial",Font.BOLD,12));
        easyMode.setToolTipText(easyTips);
        learnMode.setBounds(105,235, 170, 40);
        learnMode.setFont( new FontUIResource("Arial",Font.BOLD,12));
        learnMode.setToolTipText(learnTips);
        hardMode.setBounds(275,235, 100, 40);
        hardMode.setFont( new FontUIResource("Arial",Font.BOLD,12));
        hardMode.setToolTipText(hardTips);
        //Add rules label and set the font and location
        this.add(rulesLabel);
        rulesLabel.setBounds(310, 197, 50, 40);
        rulesLabel.setFont( new FontUIResource("Arial",Font.ITALIC,17));
        rules.setLineWrap(true); //Allow line wrap
        //Add the Title picture as a JLabel
        ImageIcon icon = new ImageIcon("ICON/Title.png");
        BigTitle.setIcon(icon);
        BigTitle.setBounds(0,0,380,200);
        this.add(BigTitle);
    }

    /** 
     * This method sets up the listener of rules label and three buttons
     */
    public void addFcn(){
        //Use adapter to make code more clear instead of implemnting all the methods
        rulesLabel.addMouseListener(new MouseAdapter(){
            //When the cusor moves into the label, show the window of game rules
            public void mouseEntered(java.awt.event.MouseEvent e){
                RulesWin.setSize(505,210);
                RulesWin.setLocationRelativeTo(easyMode); //Set the center point to easyMode button
                RulesWin.add(rules);
                RulesWin.setVisible(true);  
            }
            public void mouseExited(java.awt.event.MouseEvent e){
                RulesWin.dispose(); //When the cusor moves out of the label, close the window
            }
        });

        //Use adapter to make code more clear instead of implemnting all the methods
        easyMode.addMouseListener(new MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){
                dispose();
                mode = 1;
                GameRule NewGame = new GameRule();  //Start games as mode 1
                NewGame.go();
                MainFrame mainFace = new MainFrame();   //Show the main GUI
                mainFace.setVisible(true); 
            }
        });
        hardMode.addMouseListener(new MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){
                dispose();
                mode = 3;
                GameRule NewGame = new GameRule();  //Start games as mode 3
                NewGame.go();
                MainFrame mainFace = new MainFrame(); //Show the main GUI
                mainFace.setVisible(true);  
            }
        });
        learnMode.addMouseListener(new MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){
                dispose();
                mode = 2;
                GameRule NewGame = new GameRule();  //Start games as mode 2
                NewGame.go();
                MainFrame mainFace = new MainFrame(); //Show the main GUI
                mainFace.setVisible(true);  
            }
        });
    }

}
