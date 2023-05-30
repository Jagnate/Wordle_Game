import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.Color;

/**
 *  Title      : GamePrepare.java
 *  Description: This class is to read two files of words, 
 *  for program select a random word 
 *  and check whether what user enter is a word or not
 * 
 *  In this version, I actualize the basic rule of wordle, but cannot choose word randomly
 *  @author  Jin Si
 *  @version 1.0
 * 
 *  In this version, I added the selectword method which now can choose word randomly
 *  and recored the chinese meaning.
 *  @author  Jin Si
 *  @version 2.0
 * 
 *  In this version, I added the history-check function.
 *  @author  Jin Si
 *  @version 3.0
 */
public class GameRule {
    /**
     * The answer word
     */
    protected static String ans;
    /**
     * The word player entered
     */
    protected static String tryAns = new String();
    private ImageIcon iconWin = new ImageIcon("ICON/icon1.png");
    private ImageIcon iconLose = new ImageIcon("ICON/icon2.png");
    private int numTry;
    private static int alphabet[][] = new int[26][2]; 
    //The fist dimension is to momorize which letter appears in the answer word
    //The second dimension is to caculate how many times it appears in the answer word

    /** 
     * This method starts the game, select the word and save each letter to array
     */    
    public void go(){  
        GamePrepare.selectWord();
        //System.out.println(ans);  //For test, for cheat hhh
        turnAns();
    }

    /** 
     * This method save the answer to an array
     */    
    public void turnAns(){
        //Initialize
        for(int i=0;i<26;i++){
            alphabet[i][0]=0;
            alphabet[i][1]=0;
        }
        //If the letter is in the word, turn the alphabet[ascii][0] to 1, otherwise 0
        //alphabet[][1] is used to remember how many times are this letter shows in the answer word
        for(int i=0;i<5;i++){
            alphabet[((int)ans.charAt(i))-65][0]=1;
            alphabet[((int)ans.charAt(i))-65][1]++;
        }
    }

    /** 
     * This method set the Text to the blocks
     * @throws Exception For mode 3, check if the word has been tried and its not in the answer
     */    
    public void getText() throws Exception{
        //The current letter user is entering
        int index = (tryAns.length()-1);
        //If the letter has been tested before and it's in the answer, than throw exception
        if(alphabet[(int)tryAns.charAt(index)-65][0]==-1&&StartFrame.mode==3)
            throw new Exception("You have tried \""+tryAns.charAt(index)+"\" before and it isn't in the word, try again!");
        MainFrame.inLetter[numTry][index].setText(Character.toString(tryAns.charAt(index))); //SetText to the blcok
    }

    /** 
     * This method check answer and change the color 
     */    
    public void checkAns(){
        int times[] = new int[26];
        for(int i=0;i<26;i++){
            times[i]=alphabet[i][1];
        }
        //Check If the letter is in the same position
        for(int i=0;i<5;i++){
            MainFrame.inLetter[numTry][i].setBackground(new Color(96,96,96));
            if(ans.charAt(i)==tryAns.charAt(i)){
                //Change the block color
                MainFrame.inLetter[numTry][i].setBackground(new Color(86,128,76));//This was tested by digital colorimeter
                //Change the keyboard color
                MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setBackground(new Color(86,128,76));
                MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                times[((int)tryAns.charAt(i))-65]--;    
            }
        }
        for(int i=0;i<5;i++){
            //It's in the word and it hasn't turned to green; then turn to yellow 
            if(alphabet[(int)(tryAns.charAt(i))-65][0]==1&&times[(int)(tryAns.charAt(i))-65]!=0&&(!MainFrame.inLetter[numTry][i].getBackground().equals(new Color(86,128,76)))){
                MainFrame.inLetter[numTry][i].setBackground(new Color(168,149,69));//This was tested by digital colorimeter
                MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setBackground(new Color(168,149,69));
                MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                times[(int)(tryAns.charAt(i))-65]--;
            }
            //It's not in the word, trun to gray
            else if(alphabet[(int)(tryAns.charAt(i))-65][0]==0){ 
                alphabet[(int)(tryAns.charAt(i))-65][0]=-1;
                MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setBackground(new Color(96,96,96));
                MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                if(StartFrame.mode==3)//Hard mode cannot enter the letter not in the answer word which you have tried before
                    MainFrame.keyBoard[(int)tryAns.charAt(i)-65].setEnabled(false);
            }
        }
    }

    /** 
     * This method check whether the game is over, win or lose
     */    
    public void endGame(){
        numTry++;
        if(ans.equals(tryAns)){
            writeHistory("Win!");   //Write this record to history
            int choice = 0;
            if(StartFrame.mode==1||StartFrame.mode==3)
                choice = JOptionPane.showOptionDialog(null,"Well Done!! Do you want to try another word?", "Congratulations!!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, iconWin, new String[]{"Absolutely!","No, thanks."} , "Absolutely!");
            else if(StartFrame.mode==2) //show Chinese meaning
                choice = JOptionPane.showOptionDialog(null,"Well Done!! The Chinese meaning of the wrod is "+GamePrepare.meaningList.get(GamePrepare.indexOfword)+"\n"+" Do you want to try another word?", "Congratulations!!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, iconWin, new String[]{"Absolutely!","No, thanks."} , "Absolutely!");
            if(choice==0){  //Start again
                reSet();
                go();
            }
            else
                System.exit(1); //Quite the program
        }
        else if(numTry==6){
            writeHistory("Lose!");  //Write this record to history
            int choice = 0;
            if(StartFrame.mode==1||StartFrame.mode==3)
                choice = JOptionPane.showOptionDialog(null,"What a pity!! The word is "+ans+"\n"+" Do you want to try another word?", "Bad Luck...", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, iconLose, new String[]{"Try again!","No, thanks."} , "Try again!");
            else if(StartFrame.mode==2) //show Chinese meaning
                choice = JOptionPane.showOptionDialog(null,"What a pity!! The word is "+ans+" and its Chinese meaning is "+GamePrepare.meaningList.get(GamePrepare.indexOfword)+"\n"+" Do you want to try another word?", "Bad Luck...", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, iconLose, new String[]{"Try again!","No, thanks."} , "Try again!");
            if(choice==0){  //Start again
                reSet();
                go();
            }
            else
                System.exit(1); //Quite the program
        }
        else{   //If not end, than, then clear the previous try
            tryAns="";
        }
    }

    /** 
     * This method resets all the component
     */    
    public void reSet(){
        tryAns="";
        numTry=0;
        for(int i=0;i<6;i++)
            for(int j=0;j<5;j++){
                MainFrame.inLetter[i][j].setText("");
                MainFrame.inLetter[i][j].setBackground(new Color(48,48,48));
            }
        for(int i=0;i<26;i++){
            MainFrame.keyBoard[i].setBackground(new JButton().getBackground());
            MainFrame.keyBoard[i].setBorder(new JButton().getBorder());
            MainFrame.keyBoard[i].setEnabled(true);
        }
    }

    /** 
     * This method check whether the word user enter is valid
     * @throws Exception Whether it is a valid word
     */    
    public void EnterCheck()throws Exception{
        if(tryAns.length()!=5)  //Must be 5 letters
            throw new Exception("You must try a 5-letter-word.");
        if(GamePrepare.judgeMap.get(tryAns)==null&&StartFrame.mode==2)  //If is mode 2, must enter a word
            throw new Exception("You're learning English! Try to spell a word!");
        if(GamePrepare.judgeMap.get(tryAns)==null&&StartFrame.mode==3)  //If is mode 3, must enter a word
            throw new Exception("You're challenging the hard mode! You must enter a word!");
        
    }

    /** 
     * This method sets all the function of physical keyboard
     * @param text Write the letter to the specific block 
     */    
    public void KeyBoard(JTextField text){
        //Use adapter to make code more clear instead of implemnting all the methods
        text.addKeyListener( new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                try{
                    if(tryAns.length()<5){  //If player clikced the keyboard many times won't make program crush
                        if(((int)e.getKeyChar()>=65)&&((int)e.getKeyChar()<=90)){   //Can only read the valid word
                            tryAns += e.getKeyChar();
                            getText(); 
                        }
                        if(((int)e.getKeyChar()>=97)&&((int)e.getKeyChar()<=122)){
                            tryAns += e.getKeyChar();
                            tryAns = tryAns.toUpperCase();
                            getText(); 
                        }
                    }
                }
                catch(Exception ex){
                    tryAns = tryAns.substring(0,tryAns.length()-1);
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            public void keyPressed(KeyEvent ev){
                if(ev.getKeyCode()==KeyEvent.VK_ENTER){ //Enter, go along the process and start next try
                    try{
                        EnterCheck();
                        checkAns();
                        endGame(); 
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(ev.getKeyCode()==KeyEvent.VK_BACK_SPACE){  //If Backspace, delete the letter
                    if(tryAns.length()>0){
                        MainFrame.inLetter[numTry][tryAns.length()-1].setText("");
                        tryAns = tryAns.substring(0,tryAns.length()-1);
                    }
                }
            }
        });
    }

    /** 
     * This method writes the current recored to the history.txt file
     * @param state Win or lose
     */    
    public void writeHistory(String state){
        try{
            //If the recored is more than 11, than clear all the previous recoreds and start new record
            FileReader fileReader = new FileReader("History.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String oneline = bufferedReader.readLine();
            int numberOfLine=1;
            while(oneline!=null){
                numberOfLine++;
                oneline = bufferedReader.readLine();
            }
            fileReader.close();
            bufferedReader.close();
            //Write record to the file
            boolean judge;
            if(numberOfLine>12)
                judge = false;  //If is more than 11 records, than overwrite
            else
                judge = true;   //If is less, than append
            FileWriter fileWriter = new FileWriter("History.txt",judge);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if(judge==false)
                bufferedWriter.write("Word  Times Win/Lose Date");
            bufferedWriter.write("\n");
            //Read current date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeStr = sdf.format(new Date());
            bufferedWriter.write(ans+"   "+numTry+"      "+state+"      "+timeStr);
            bufferedWriter.close();
            fileWriter.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
        }

    }

    /** 
     * This method sets all the function of virtual keyboard
     */    
    public void VirtualKeyBoard(){
        for(int i=0;i<26;i++){
            String letter = MainFrame.keyBoard[i].getText();
            MainFrame.keyBoard[i].addActionListener( new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        if(tryAns.length()<5){
                            tryAns += letter;
                            getText(); 
                        }
                    }
                    catch(Exception ex){
                        tryAns = tryAns.substring(0,tryAns.length()-1);
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
                    }
                }

            });
        }
        //Enter key
        MainFrame.keyBoard[26].addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    EnterCheck();
                    checkAns();
                    endGame();   
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Delete key
        MainFrame.keyBoard[27].addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tryAns.length()>0){
                    MainFrame.inLetter[numTry][tryAns.length()-1].setText("");
                    tryAns = tryAns.substring(0,tryAns.length()-1);
                }
            }
        });
    }
}
