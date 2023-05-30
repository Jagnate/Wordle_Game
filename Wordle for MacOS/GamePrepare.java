import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

/**
 *  Title      : GamePrepare.java
 *  Description: This class is to read two files of words, 
 *  for program select a random word 
 *  and check whether what user enter is a word or not
 * 
 *  In this version, I added the function of reading dictionary.txt file to select random word and check the word
 *  @author  Jin Si
 *  @version 1.0
 * 
 *  In this version, I added the function of reading TOEFL_WORD.txt to select random word 
 *  and recored the chinese meaning
 *  @author  Jin Si
 *  @version 1.1
 * 
 *  In this version, I use hash map to improve my program
 *  @author  Jin Si
 *  @version 2.0
 */
public class GamePrepare {
    
    private static String dic = "Dictionary.txt";   //Full 5-letter words
    private static String doc = "TOEFL_WORD.txt";   //My list when I'm leanring TOEFL
    private static String[] list = new String[4000];   //Save all the words, max:4000
    /** 
     * Save chinese meaning 
     */    
    protected static ArrayList<String> meaningList = new ArrayList<String>();  
    /** 
     * Use hash map to serach the word faster
     */    
    protected static HashMap<String,String> judgeMap = new HashMap<String,String>();
    /** 
     * Check the number of words
     */       
    protected static int numOfWord = 0;
    /** 
     * The index of the words
     */    
    protected static int indexOfword = 0;

    /** 
     * This method read all the 5-letter words to String list
     */    
    public static void getDic(){
        try{
            FileReader fileReader = new FileReader(dic);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            list = bufferedReader.readLine().toUpperCase().split(" "); //Only one line, split with space
            fileReader.close();
            bufferedReader.close();
            //3265 is the number of the word in the Dictionary.txt file
            for(int i=0;i<3624;i++){
                String key = list[i];
                String value = list[i];
                judgeMap.put(key,value);    //Add to hash map
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** 
     * This method set up the answers for English-learning mode
     */    
    public static void getOwnWord(){
        try{
            FileReader fileReader = new FileReader(doc);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //Read a line one by one
            String oneline = bufferedReader.readLine();
            while(oneline!=null){
                String[] temp = oneline.toUpperCase().split(" ");   //Save the word and the chinese meaning
                list[numOfWord] = temp[0];  //Save the word
                meaningList.add(temp[1]);   //Save the Chinese meaning
                numOfWord++;
                oneline = bufferedReader.readLine();
            }
            fileReader.close();
            bufferedReader.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Opps! Error occured", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** 
     * This method select a word randomly for player
     */    
    public static void selectWord(){
        //Mode 1 and Mode 3 selct word from dictioanry    
        if(StartFrame.mode==1||StartFrame.mode==3){  
            GameRule.ans = list[(int)(Math.random()*3624)].toUpperCase();
        }
        //Mode 2 select word from Chinese dictioanry 
        else if(StartFrame.mode==2){
            indexOfword = (int)(Math.random()*numOfWord);
            GameRule.ans = list[indexOfword].toUpperCase();
        }        
    }

}
