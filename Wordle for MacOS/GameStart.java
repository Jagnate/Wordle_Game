import javax.swing.JFrame;

/**
 *  Title      : GameStart.java
 *  Description: This class is the start of the Wordle game
 * 
 *  In this version, the answer word is not from a file
 *  @author  Jin Si
 *  @version 1.0
 * 
 *  In this version, the answer word is selected randomly from a file
 *  I added the getDic() and getOwnWord() methods
 *  @author  Jin Si
 *  @version 1.1
 */

public class GameStart {
    /**
     *  This main method is where my dream starts!
     *  Run this method can play the game.
     *  @param args[]
     */
    public static void main(String[] args) {
        GamePrepare.getDic();   //Load the Dictionary output file as answers and to check whether is a word
        GamePrepare.getOwnWord();   //Load the TOEFL_Word file as answers
        StartFrame startFace = new StartFrame();
        startFace.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFace.setSize(380,310);
        //Like Steven Jobs said: People don’t know what they want until you show it to them.
        startFace.setResizable(false);  //So the frame is set to fixed size
        startFace.setLocationRelativeTo(null);
        startFace.setVisible(true);
    }
}
