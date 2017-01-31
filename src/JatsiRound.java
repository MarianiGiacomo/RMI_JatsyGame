
import java.util.Random;


/**
 * Class for JatsiGame rounds. 
 * @author Giacomo Mariani
 */
public class JatsiRound {

    private Random dice;

    private int[] scorePlayerOne;
    private int[] scorePlayerTwo;

    public JatsiRound() {
        dice = new Random();
        scorePlayerOne = new int[5];
        scorePlayerTwo = new int[5];
    }
    
    /**
     * Throw dices for first JatsiPlayer
     * @return int[] score. Dice numbers. 
     */
    public int[] playPlayerOne() {
        for (int i=0; i<5; i++){
            scorePlayerOne[i]=throwDice();
        }
        return scorePlayerOne;
    }

    /**
     * Throw dices for first JatsiPlayer
     * @return int[] score. Dice numbers. 
     */
    public int[] playPlayerTwo() {
        for (int i=0; i<5; i++){
            scorePlayerTwo[i]=throwDice();
        }
        return scorePlayerTwo;
    }
    
    /**
     * Returns the score for player's last dice throw
     * @param int playerIndex
     * @return int[] playerScore 
     */
    public int[] getPlayerScore(int playerIndex){
        if(playerIndex == 0){return scorePlayerOne;}
        return scorePlayerTwo;
        
    }

    /**
     * Throws one dice.
     * @return int number in range [1-6]
     */
    private int throwDice(){
        return dice.nextInt(6)+1;
    }

    
}
