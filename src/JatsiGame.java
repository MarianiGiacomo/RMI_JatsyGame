
import java.util.Random;

/**
 * Object for the game. 
 * Stores the two players and their scores, the rund count and calculates points
 * from dices numbers. 
 * @author Giacomo Mariani
 */
public class JatsiGame {

    private JatsiPlayer[] players;
    private boolean ready;
    private JatsiRound[] rounds;
    private int roundCount;
    private int pointsPlayerOne;
    private int pointsPlayerTwo;
    private int totPointsPlayerOne;
    private int totPointsPlayerTwo;
    private boolean expired;

    public JatsiGame() {
        players = new JatsiPlayer[2];
        ready = false;
        rounds = new JatsiRound[5];
        roundCount = 0;
        pointsPlayerOne = 0;
        pointsPlayerTwo = 0;
        totPointsPlayerOne = 0;
        totPointsPlayerTwo = 0;
        expired = false;

    }

    public JatsiGame(JatsiPlayer player) {
        players = new JatsiPlayer[2];
        players[0] = player;
        rounds = new JatsiRound[5];
        ready = false;
    }
    
    /**
     * Starts a Jatsi round.
     * Calls method in JatsiRound.
     * Increases round number by one.
     * Calls method for calculating points.
     * Cumulative point count. 
     */
    public void playRound() {

        rounds[roundCount] = new JatsiRound();
        pointsPlayerOne = calcPoints(rounds[roundCount].playPlayerOne());       // Throwas the dices and calculate the points
        pointsPlayerTwo = calcPoints(rounds[roundCount].playPlayerTwo());
        totPointsPlayerOne = totPointsPlayerOne + pointsPlayerOne;
        totPointsPlayerTwo = totPointsPlayerTwo + pointsPlayerTwo;
        roundCount++;
    }
    
    /**
     * Adds second player to arraylist
     * @param String name 
     */
    public void setSecondPlayer(String name) {
        players[1] = new JatsiPlayer(name);
        ready = true;
    }
    
    /**
     * Sets the game to expired = true
     */
    public void setExpired() {
        ready = false;
        expired = true;
    }
    
    /**
     * To check if the game is ready to start
     * @return boolean ready
     */
    public boolean isReady() {
        return ready;
    }
    
    /**
     * To check if the game has expired
     * @return boolean expired
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * Returns the arraylist containing the two JatsiPlayers
     * @return JatsyPlayer[]
     */
    public JatsiPlayer[] getPlayers() {
        return players;
    }

    /**
     * Returns the given index JatsyPlayer's points from last played round.
     * @param int PlayerIndex
     * @return points
     */
    public int getPlayerPoints(int PlayerIndex) {
        if (PlayerIndex == 0) {
            return pointsPlayerOne;
        }
        return pointsPlayerTwo;
    }
    
    /**
     * Returns the cumulative point count for the JatsiPlayer in the given index
     * @param int PlayerIndex
     * @return int totalPoints
     */
    public int getPlayerTotPoints(int PlayerIndex){
        if (PlayerIndex == 0) {
            return totPointsPlayerOne;
        }
        return totPointsPlayerTwo;
    }

    /**
     * Returns the corresponding round
     * @param int round. Round number to be retrieved. 
     * @return JatsiRound round
     */
    public JatsiRound getRound(int round) {
        return rounds[round];
    }

    /**
     * Calculated the points according to given five dices' numbers.
     * @param int[] dices
     * @return int points
     */
    private int calcPoints(int[] dices) {
        // Five of a kind
        if ((dices[0] == dices[1]) && dices[1] == dices[2] && (dices[2] == dices[3]) && (dices[3] == dices[4])) { // Yatsi
            return 50;
        }

        //Four of a kind
        if ((dices[0] == dices[1]) && dices[1] == dices[2] && (dices[2] == dices[3]) && (dices[3] != dices[4])) { // Fours 0123
            return dices[0] * 4;
        }
        if ((dices[0] == dices[1]) && dices[1] == dices[2] && (dices[2] != dices[3]) && (dices[2] == dices[4])) { // Fours 0124
            return dices[0] * 4;
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[1] == dices[3]) && (dices[3] == dices[4])) { // Fours 0134
            return dices[0] * 4;
        }
        if ((dices[0] != dices[1]) && dices[0] == dices[2] && (dices[2] == dices[3]) && (dices[3] == dices[4])) { // Fours 0234
            return dices[0] * 4;
        }
        if ((dices[0] != dices[1]) && dices[1] == dices[2] && (dices[1] == dices[3]) && (dices[3] == dices[4])) { // Fours 1234
            return dices[1] * 4;
        }

        //Three of a kind
        if ((dices[0] == dices[1]) && dices[1] == dices[2] && (dices[2] != dices[3]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Three 012
            return dices[0] * 3;
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[1] == dices[3]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Three 013
            return dices[0] * 3;
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[1] != dices[3]) && (dices[1] == dices[4]) && (dices[2] != dices[3])) { // Three 014
            return dices[0] * 3;
        }
        if ((dices[0] != dices[1]) && dices[0] == dices[2] && (dices[2] == dices[3]) && (dices[3] != dices[4]) && (dices[1] != dices[4])) { // Three 023
            return dices[0] * 3;
        }
        if ((dices[0] != dices[1]) && dices[0] == dices[2] && (dices[2] != dices[3]) && (dices[2] == dices[4]) && (dices[1] != dices[3])) { // Three 024
            return dices[0] * 3;
        }
        if ((dices[0] != dices[1]) && dices[0] != dices[2] && (dices[0] == dices[3]) && (dices[3] == dices[4]) && (dices[1] != dices[2])) { // Three 034
            return dices[0] * 3;
        }
        if ((dices[0] != dices[1]) && dices[1] == dices[2] && (dices[2] == dices[3]) && (dices[3] != dices[4]) && (dices[0] != dices[4])) { // Three 123
            return dices[1] * 3;
        }
        if ((dices[0] != dices[1]) && dices[1] == dices[2] && (dices[2] != dices[3]) && (dices[2] == dices[4]) && (dices[0] != dices[3])) { // Three 124
            return dices[1] * 3;
        }
        if ((dices[0] != dices[1]) && dices[1] != dices[2] && (dices[1] == dices[3]) && (dices[3] == dices[4]) && (dices[0] != dices[2])) { // Three 134
            return dices[1] * 3;
        }
        if ((dices[0] != dices[1]) && dices[1] != dices[2] && (dices[2] == dices[3]) && (dices[3] == dices[4]) && (dices[0] != dices[2])) { // Three 234
            return dices[2] * 3;
        }

        //Full houses
        if ((dices[0] == dices[1]) && dices[1] == dices[2] && (dices[2] != dices[3]) && (dices[2] != dices[4]) && (dices[3] == dices[4])) { // Full house 012
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[1] == dices[3]) && (dices[2] == dices[4]) && (dices[3] != dices[4])) { // Full house 013
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[1] != dices[3]) && (dices[1] == dices[4]) && (dices[2] == dices[3])) { // Full house 014
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] != dices[1]) && dices[0] == dices[2] && (dices[2] == dices[3]) && (dices[3] != dices[4]) && (dices[1] == dices[4])) { // Full house 023
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] != dices[1]) && dices[0] == dices[2] && (dices[2] != dices[3]) && (dices[2] == dices[4]) && (dices[1] == dices[3])) { // Full house 024
            return dices[0] * 3;
        }
        if ((dices[0] != dices[1]) && dices[0] != dices[2] && (dices[0] == dices[3]) && (dices[3] == dices[4]) && (dices[1] == dices[2])) { // Full house 034
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] != dices[1]) && dices[1] == dices[2] && (dices[2] == dices[3]) && (dices[3] != dices[4]) && (dices[0] == dices[4])) { // Full house 123
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] != dices[1]) && dices[1] == dices[2] && (dices[2] != dices[3]) && (dices[2] == dices[4]) && (dices[0] == dices[3])) { // Full house 124
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] != dices[1]) && dices[1] != dices[2] && (dices[1] == dices[3]) && (dices[3] == dices[4]) && (dices[0] == dices[2])) { // Full house 134
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[2] == dices[3]) && (dices[3] == dices[4]) && (dices[0] != dices[2])) { // Full house 234
            return (dices[0] + dices[1] + dices[2] + dices[3] + dices[4]);
        }

        //Two pairs
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[2] == dices[3]) && (dices[1] != dices[4]) && (dices[3] != dices[4])) { // Two pairs 01 23
            return (dices[0] * 2 + dices[2] * 2);
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[2] == dices[4]) && (dices[1] != dices[3]) && (dices[2] != dices[3])) { // Two pairs 01 24
            return (dices[0] * 2 + dices[2] * 2);
        }
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[3] == dices[4]) && (dices[1] != dices[2]) && (dices[2] != dices[3])) { // Two pairs 01 34
            return (dices[0] * 2 + dices[3] * 2);
        }
        if ((dices[0] == dices[2]) && dices[1] != dices[2] && (dices[1] == dices[3]) && (dices[1] != dices[4]) && (dices[2] != dices[4])) { // Two pairs 02 13
            return (dices[0] * 2 + dices[3] * 2);
        }
        if ((dices[0] == dices[2]) && dices[1] != dices[2] && (dices[1] == dices[4]) && (dices[1] != dices[3]) && (dices[2] != dices[3])) { // Two pairs 02 14
            return (dices[0] * 2 + dices[1] * 2);
        }
        if ((dices[0] == dices[2]) && dices[1] != dices[2] && (dices[1] != dices[4]) && (dices[2] != dices[3]) && (dices[3] == dices[4])) { // Two pairs 02 34
            return (dices[0] * 2 + dices[3] * 2);
        }
        if ((dices[0] == dices[3]) && dices[0] != dices[1] && (dices[1] == dices[2]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Two pairs 03 12
            return (dices[0] * 2 + dices[1] * 2);
        }
        if ((dices[0] == dices[3]) && dices[0] != dices[1] && (dices[1] == dices[4]) && (dices[2] != dices[4]) && (dices[3] != dices[2])) { // Two pairs 03 14
            return (dices[0] * 2 + dices[1] * 2);
        }
        if ((dices[0] == dices[3]) && dices[0] != dices[2] && (dices[2] == dices[4]) && (dices[2] != dices[1]) && (dices[3] != dices[1])) { // Two pairs 03 24
            return (dices[0] * 2 + dices[2] * 2);
        }
        if ((dices[0] == dices[4]) && dices[4] != dices[1] && (dices[1] == dices[2]) && (dices[4] != dices[3]) && (dices[3] != dices[1])) { // Two pairs 04 12
            return (dices[0] * 2 + dices[2] * 2);
        }
        if ((dices[0] == dices[4]) && dices[4] != dices[1] && (dices[1] == dices[3]) && (dices[4] != dices[2]) && (dices[3] != dices[2])) { // Two pairs 04 13
            return (dices[0] * 2 + dices[3] * 2);
        }
        if ((dices[0] == dices[4]) && dices[4] != dices[2] && (dices[2] == dices[3]) && (dices[4] != dices[1]) && (dices[3] != dices[1])) { // Two pairs 04 23
            return (dices[0] * 2 + dices[3] * 2);
        }
        if ((dices[1] == dices[2]) && dices[2] != dices[3] && (dices[3] == dices[4]) && (dices[4] != dices[0]) && (dices[1] != dices[0])) { // Two pairs 12 34
            return (dices[1] * 2 + dices[3] * 2);
        }
        if ((dices[1] == dices[3]) && dices[3] != dices[2] && (dices[2] == dices[4]) && (dices[4] != dices[0]) && (dices[1] != dices[0])) { // Two pairs 13 24
            return (dices[1] * 2 + dices[2] * 2);
        }
        if ((dices[1] == dices[4]) && dices[4] != dices[2] && (dices[2] == dices[3]) && (dices[4] != dices[0]) && (dices[2] != dices[0])) { // Two pairs 14 23
            return (dices[1] * 2 + dices[2] * 2);
        }

        //Two of a kind
        if ((dices[0] == dices[1]) && dices[1] != dices[2] && (dices[1] != dices[3]) && (dices[1] != dices[4]) && (dices[2] != dices[3]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Two 01
            return dices[0] * 2;
        }
        if ((dices[0] == dices[2]) && dices[1] != dices[2] && (dices[1] != dices[3]) && (dices[1] != dices[4]) && (dices[2] != dices[3]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Two 02
            return dices[0] * 2;
        }
        if ((dices[0] == dices[3]) && dices[1] != dices[2] && (dices[1] != dices[3]) && (dices[1] != dices[4]) && (dices[2] != dices[3]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Two 03
            return dices[0] * 2;
        }
        if ((dices[0] == dices[4]) && dices[1] != dices[2] && (dices[1] != dices[3]) && (dices[1] != dices[4]) && (dices[2] != dices[3]) && (dices[2] != dices[4]) && (dices[3] != dices[4])) { // Two 04
            return dices[0] * 2;
        }
        if ((dices[1] == dices[2]) && dices[0] != dices[2] && (dices[0] != dices[3]) && (dices[0] != dices[4]) && (dices[3] != dices[2]) && (dices[3] != dices[4]) && (dices[2] != dices[4])) { // Two 12
            return dices[1] * 2;
        }
        if ((dices[1] == dices[3]) && dices[0] != dices[2] && (dices[0] != dices[3]) && (dices[0] != dices[4]) && (dices[3] != dices[2]) && (dices[3] != dices[4]) && (dices[2] != dices[4])) { // Two 13
            return dices[1] * 2;
        }
        if ((dices[1] == dices[4]) && dices[0] != dices[2] && (dices[0] != dices[3]) && (dices[0] != dices[4]) && (dices[3] != dices[2]) && (dices[3] != dices[4]) && (dices[2] != dices[4])) { // Two 14
            return dices[1] * 2;
        }
        if ((dices[2] == dices[3]) && dices[0] != dices[1] && (dices[0] != dices[3]) && (dices[0] != dices[4]) && (dices[1] != dices[2]) && (dices[1] != dices[4]) && (dices[2] != dices[4])) { // Two 23
            return dices[2] * 2;
        }
        if ((dices[2] == dices[4]) && dices[0] != dices[1] && (dices[0] != dices[3]) && (dices[0] != dices[4]) && (dices[1] != dices[2]) && (dices[1] != dices[3]) && (dices[2] != dices[3])) { // Two 24
            return dices[2] * 2;
        }
        if ((dices[3] == dices[4]) && dices[0] != dices[2] && (dices[0] != dices[3]) && (dices[0] != dices[1]) && (dices[1] != dices[2]) && (dices[1] != dices[3]) && (dices[2] != dices[3])) { // Two 34
            return dices[3] * 2;
        }

        //Small straight
        if (dices[0] != 6 && dices[0] != dices[1] && dices[0] != dices[2] && dices[0] != dices[3] && dices[0] != dices[4] && dices[1] != 6 && dices[1] != dices[2] && dices[1] != dices[3]
                && dices[1] != dices[4] && dices[2] != 6 && dices[2] != dices[3] && dices[2] != dices[4] && dices[3] != 6 && dices[3] != dices[4] && dices[4] != 6) { // Small straight
            return 15;
        }

        //Large straight
        if (dices[0] != 1 && dices[0] != dices[1] && dices[0] != dices[2] && dices[0] != dices[3] && dices[0] != dices[4] && dices[1] != 1 && dices[1] != dices[2] && dices[1] != dices[3]
                && dices[1] != dices[4] && dices[2] != 1 && dices[2] != dices[3] && dices[2] != dices[4] && dices[3] != 1 && dices[3] != dices[4] && dices[4] != 1) { // Large straight
            return 20;
        }
        return 0;
    }

}
