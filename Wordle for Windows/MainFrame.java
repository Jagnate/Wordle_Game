import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

/**
 *  Title      : MainFrame.java
 *  Description: This class is the main interface of the Wordle game 
 *  which shows all the component realted to the game
 * 
 *  In this version, I set up the physical keyboard and 30 blocks for basic game style
 *  @author  Jin Si
 *  @version 1.0
 * 
 *  In this version, I added the tips function
 *  @author  Jin Si
 *  @version 2.0
 * 
 *  In this version, I made the GUI style more pretty
 *  @author  Jin Si
 *  @version 2.1
 * 
 *  In this version, I added the history function
 *  @author  Jin Si
 *  @version 3.0
 */

public class MainFrame extends JFrame {

    /** 
     * This is the main panel
     */
    private JPanel mainPanel = new JPanel();
    /** 
     * This is the Tips label
     */
    private JLabel TipsLabel = new JLabel("Tricky? Touch me!");
    /** 
     * This is the Tips window
     */
    private JWindow TipWin = new JWindow();
    /** 
     * This is the Tips JTextArea to save tips text
     */
    private JTextArea Tip = new JTextArea();
    /** 
     * This is a history label to check records
     */
    private JLabel HistoryLabel = new JLabel("History");
    /** 
     * This is a history window to show records
     */
    private JWindow HisWin = new JWindow();
    /** 
     * This is a JTextArea shows the history text
     */
    private JTextArea History = new JTextArea();
    /** 
     * This is a 2D array, save the JTextField as the letter box
     */
    protected static JTextField[][] inLetter = new JTextField[6][5];
    /** 
     * This is the keyboard button, each button is a key.
     */
    protected static JButton[] keyBoard = new JButton[28];

    /** 
     * This constructor sets up the layout and show the main GUI to player
     */
    public MainFrame(){
        super("Wordle Desgined by Jin");
        mainPanel.setLayout(null);
        this.setContentPane(mainPanel);
        wordleLayout();  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(395,650);
        this.setResizable(false);   //Can not change size
        this.setLocationRelativeTo(null);    
    }

    /** 
     * This method sets they original style of JTextField 
     * This method is for wordLayout function 
     * @param letter JTextField component to show letter
     */    
    private void setInLetter(JTextField letter){
        letter.setOpaque(true);
        letter.setBackground(new Color(48,48,48));
        letter.setFont( new FontUIResource("Arial",Font.BOLD,50));
        letter.setHorizontalAlignment(SwingConstants.CENTER);
        letter.setForeground( new ColorUIResource(255, 255, 255));
    }

    /** 
     * This method sets the layout of all component
     */    
    private void wordleLayout(){
        //GameRUle to set a function
        GameRule rule = new GameRule();
        //30 blcoks to show the letters
        for(int i=0;i<6;i++)
            for(int j=0;j<5;j++){
                inLetter[i][j] = new JTextField("");
                setInLetter(inLetter[i][j]);
                this.add(inLetter[i][j]);
                //Set the location based on the index of inLetter to make code clean
                inLetter[i][j].setBounds(((5*(j+1))+70*j),(5*(i+1)+70*i),70,70);
                inLetter[i][j].setEditable(false);
                inLetter[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                //Add listener and event to each physcial keyborad related to each JTextField
                rule.KeyBoard(inLetter[i][j]);
            }
        //Set tips button
        this.add(TipsLabel);
        TipsLabel.setBounds(250, 465, 150, 20);
        addTipsFcn();
        //Set virtual keyboard
        KeyBoardLayout();
        rule.VirtualKeyBoard();
        //Set history-check label
        this.add(HistoryLabel);
        HistoryLabel.setBounds(5, 465, 50, 20);
        addHisFcn();
    }

    /** 
     * This method set up the tips function
     */    
    public void addTipsFcn(){
        int index = (int)Math.random()*5;
        //Use adapter to make code more clear instead of implemnting all the methods
        TipsLabel.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){
                String str= new String();
                //Easy and Hard mode gives 2 tips
                if(StartFrame.mode==1||StartFrame.mode==3){
                    str = "1.The plural of 4-letter word also counts, have a try!!\n"
                    +"2.Try the letter "+GameRule.ans.charAt(index)+", maybe it's in the letter?"; //Give a random letter which is in the answer
                }
                //English leanring mdoe gives 3 tips
                else if(StartFrame.mode==2){
                    str = "1.The plural of 4-letter word also counts, have a try!!\n"
                    +"2.Try the letter \""+GameRule.ans.charAt(index)+"\", maybe it's in the letter?\n"
                    +"3.The Chinese meaning of this word is "+GamePrepare.meaningList.get(GamePrepare.indexOfword); //Give the Chinese meaning of the word
                }
                Tip.setLineWrap(true);
                Tip.setText(str);
                //Show the tips window
                TipWin.setSize(350,65);
                TipWin.setLocationRelativeTo(MainFrame.keyBoard[17]);
                TipWin.add(Tip);
                TipWin.setVisible(true);  
            }
            public void mouseExited(java.awt.event.MouseEvent e){
                TipWin.dispose();
            }
        });
    }

    /** 
     * This method set up histroy-check function
     */   
    public void addHisFcn(){
        ArrayList<String> str = new ArrayList<String>();    //Read file and get the history
        History.setLineWrap(true);
        //Use adapter to make code more clear instead of implemnting all the methods
        HistoryLabel.addMouseListener(new MouseAdapter(){
            //Each time player consor enter the textfield, read the file and update it
            public void mouseEntered(java.awt.event.MouseEvent e){
                String His=new String();
                try{
                    //Read line one by one
                    FileReader fileReader = new FileReader("History.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String oneline = bufferedReader.readLine();
                    while(oneline!=null){
                        str.add(oneline);   //Add to Arraylist
                        oneline = bufferedReader.readLine();
                    }
                    fileReader.close();
                    bufferedReader.close();
                }catch(IOException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
                }
                while(!str.isEmpty()){
                    His += str.get(0)+"\n";
                    str.remove(0);
                }
                //Show the histroy window 
                History.setText(His);
                HisWin.setSize(250,200);
                HisWin.setLocationRelativeTo(MainFrame.keyBoard[25]);
                HisWin.add(History);
                HisWin.setVisible(true);  
            }
            public void mouseExited(java.awt.event.MouseEvent e){
                HisWin.dispose();   //close the window
            }
        });
    }

    /** 
     * This method set up all the virtual keyboard button to the interface
     */       
    private void KeyBoardLayout(){
        //The arrange of keyboard doesn't have a clear pattern, set location one by one
        keyBoard[16] = new JButton("Q");
        keyBoard[16].setBounds(5,500,32,32);
        keyBoard[22] = new JButton("W");
        keyBoard[22].setBounds(42,500,32,32);
        keyBoard[4] = new JButton("E");
        keyBoard[4].setBounds(79,500,32,32);
        keyBoard[17] = new JButton("R");
        keyBoard[17].setBounds(116,500,32,32);
        keyBoard[19] = new JButton("T");
        keyBoard[19].setBounds(153,500,32,32);
        keyBoard[24] = new JButton("Y");
        keyBoard[24].setBounds(190,500,32,32);
        keyBoard[20] = new JButton("U");
        keyBoard[20].setBounds(227,500,32,32);
        keyBoard[8] = new JButton("I");
        keyBoard[8].setBounds(264,500,32,32);
        keyBoard[14] = new JButton("O");
        keyBoard[14].setBounds(301,500,32,32);
        keyBoard[15] = new JButton("P");
        keyBoard[15].setBounds(338,500,32,32);
        keyBoard[0] = new JButton("A");
        keyBoard[0].setBounds(21, 537, 32, 32);
        keyBoard[18] = new JButton("S");
        keyBoard[18].setBounds(58, 537, 32, 32);
        keyBoard[3] = new JButton("D");
        keyBoard[3].setBounds(95, 537, 32, 32);
        keyBoard[5] = new JButton("F");
        keyBoard[5].setBounds(132, 537, 32, 32);
        keyBoard[6] = new JButton("G");
        keyBoard[6].setBounds(169, 537, 32, 32);
        keyBoard[7] = new JButton("H");
        keyBoard[7].setBounds(206, 537, 32, 32);
        keyBoard[9] = new JButton("J");
        keyBoard[9].setBounds(243, 537, 32, 32);
        keyBoard[10] = new JButton("K");
        keyBoard[10].setBounds(280, 537, 32, 32);
        keyBoard[11] = new JButton("L");
        keyBoard[11].setBounds(317, 537, 32, 32);
        keyBoard[26] = new JButton("ENTER");
        keyBoard[26].setBounds(4,574,50,32);
        keyBoard[26].setFont( new FontUIResource("Arial",Font.BOLD,13));
        keyBoard[26].setMargin(new Insets(0, 0, 0, 0));
        keyBoard[25] = new JButton("Z");
        keyBoard[25].setBounds(58, 574, 32, 32);
        keyBoard[23] = new JButton("X");
        keyBoard[23].setBounds(95, 574, 32, 32);
        keyBoard[2] = new JButton("C");
        keyBoard[2].setBounds(132, 574, 32, 32);
        keyBoard[21] = new JButton("V");
        keyBoard[21].setBounds(169, 574, 32, 32);
        keyBoard[1] = new JButton("B");
        keyBoard[1].setBounds(206, 574, 32, 32);
        keyBoard[13] = new JButton("N");
        keyBoard[13].setBounds(243, 574, 32, 32);
        keyBoard[12] = new JButton("M");
        keyBoard[12].setBounds(280, 574, 32, 32);
        keyBoard[27] = new JButton("DELETE");
        keyBoard[27].setBounds(317,574,50,32);
        keyBoard[27].setFont( new FontUIResource("Arial",Font.BOLD,12));
        keyBoard[27].setMargin(new Insets(0, 0, 0, 0));
        //Add the button with for loop
        for(int i=0;i<28;i++){
            keyBoard[i].setOpaque(true);
            keyBoard[i].setMargin(new Insets(0, 0, 0, 0));
            this.add(keyBoard[i]);
        }
    }

}

