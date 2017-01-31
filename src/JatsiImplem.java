
import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author Giacomo Mariani
 * Implementation of the Jatsi surface. From this we create the RMI object
 * which the client uses to call methods.
 */
public class JatsiImplem extends UnicastRemoteObject implements Jatsi {

    private SharedData sd;    //To store and retrive game instances from server

    public JatsiImplem(SharedData sd) throws RemoteException {
        this.sd = sd;
    }
    
    /**
     * 
     * @return Welcome String
     * @throws RemoteException 
     */
    public String welcome() throws RemoteException {
        return "Welcome  to the Jatsi game!";
    }
    
    /**
     * 
     * @param  String name
     * @return array containing index of the Game and index of the Palyer in Game
     * @throws RemoteException 
     */
    public int[] setPlayer(String name) throws RemoteException {
        int gameIndex;
        int gamesNum = sd.getGames().size();                                    
        if (gamesNum == 0) {                                                    // If the JatsiGame arraylist is empthy
            sd.setGame(new JatsiGame(new JatsiPlayer(name)));                   // Create a new JatsiGame instance
            gameIndex = sd.getGames().size() - 1;                               // Store the index of the JatsiGame instance
            int[] details = {gameIndex, 0};                                     
            return details;                                                     // Return an array containing the index of the JatsiGame instance and index of the player in the JatsiPlayer arrray (first player)
        }

        else if (!sd.getGames().get(gamesNum - 1).isReady() && !sd.getGames().  // If the last JatsiGame added to the arraylist is not ready (misses second player) and has not expired
                get(gamesNum - 1).isExpired()) {
            sd.getLastGame().setSecondPlayer(name);                             // We add the player as second JatisPlayer to the existing game
            gameIndex = sd.getGames().size() - 1;
            int[] details = {gameIndex, 1};
            return details;
        } else {
            sd.setGame(new JatsiGame(new JatsiPlayer(name)));                   // If the last added JatsiGame does not need a second player, add a new game to the arraylist
            gameIndex = sd.getGames().size() - 1;
            int[] details = {gameIndex, 0};
            return details;
        }
    }
    
    /**
     * 
     * @param int gameIndex
     * @return boolean is game ready?
     * @throws RemoteException 
     */
    public boolean isGameReady(int gameIndex) throws RemoteException {
        return sd.getGames().get(gameIndex).isReady();
    }
    
    /**
     * 
     * @param int[] details
     * @return the name of the other players in the JatsiGame
     * @throws RemoteException 
     */
    public String getOtherPlayerName(int[] details) throws RemoteException {
        if (details[1] == 0) {
            return sd.getGames().get(details[0]).getPlayers()[1].toString();
        }
        return sd.getGames().get(details[0]).getPlayers()[0].toString();
    }

    /**
     * Starts the JatsiGame's round calling the method from JatsiGame object
     * @param int[] details
     * @throws RemoteException 
     */
    public void throwDices(int[] details) throws RemoteException {
        sd.getGames().get(details[0]).playRound();
    }
    
    /**
     * Sets the JatsiPlayer to ready = true / ready = false
     * Calls the method in  PlaJatsiyer object
     * @param int[] details
     * @param boolean ready
     * @throws RemoteException 
     */
    public void setReady(int[] details, boolean ready)throws RemoteException{
        sd.getGames().get(details[0]).getPlayers()[details[1]].setReady(ready);
    }
    
    /**
     * Calls the isReady() method in JatsiPlayer
     * @param int[] details
     * @return boolean other JatsiPlayer is ready == true/false
     * @throws RemoteException 
     */
    public boolean isOtherPlayerReady(int[] details)throws RemoteException{
        if(details[1] == 0){
            return sd.getGames().get(details[0]).getPlayers()[1].isReady();
        } return sd.getGames().get(details[0]).getPlayers()[0].isReady();
    }
    
    /**
     * Returns the numbers JatsiPlayer got from throwing the dices.
     * Calls the method in JatsiRound
     * @param dint[] etails
     * @param round, the number of played rounds 
     * @return int[] score, the dices numbers
     * @throws RemoteException 
     */
    public int[] getYourScore(int[] details, int round) throws RemoteException {
        return sd.getGames().get(details[0]).getRound(round).
                getPlayerScore(details[1]);
    }
    
    /**
     * Returns the numbers the other JatsiPlayer in the game got from throwing the dices.
     * Calls the method in JatsiRound.
     * @param dint[] etails
     * @param round, the number of played rounds 
     * @return int[] score, the dices numbers
     * @throws RemoteException 
     */
    public int[] getOtherPlayerScore(int[] details, int round) throws RemoteException {
        if (details[1] == 1) {
            return sd.getGames().get(details[0]).getRound(round).
                    getPlayerScore(0);
        }
        return sd.getGames().get(details[0]).getRound(round).
                getPlayerScore(1);
    }
    
    /**
     * Returns the point JatsiPlayer got from the last round.
     * Calls the method in JatsiGame
     * @param int[] details
     * @return int points, the points calulated from last dices' score
     * @throws RemoteException 
     */
    public int getPoints(int[] details) throws RemoteException {
        return sd.getGames().get(details[0]).getPlayerPoints(details[1]);
    }

    /**
     * Returns the point the other JatsiPlayer got from the last round.
     * Calls the method in JatsiGame
     * @param int[] details
     * @return int points, the points calulated from last dices' score
     * @throws RemoteException 
     */
    public int getOtherPlayerPoints(int[] details) throws RemoteException {
        if (details[1] == 1) {
            return sd.getGames().get(details[0]).getPlayerPoints(0);
        }
        return sd.getGames().get(details[0]).getPlayerPoints(1);
    }
    
    /**
     * Returns the sum of JatsiPlayer's points from all played rounds.
     * Calls method in JatsiGame.
     * @param int[] details
     * @return int totalPoints
     * @throws RemoteException 
     */
        public int getTotPoints(int[] details) throws RemoteException {
        return sd.getGames().get(details[0]).getPlayerTotPoints(details[1]);
    }

    /**
     * Returns the sum of the other JatsiPlayer's points from all played rounds.
     * Calls method in JatsiGame.
     * @param int[] details
     * @return int totalPoints
     * @throws RemoteException 
     */
    public int getOtherPlayerTotPoints(int[] details) throws RemoteException {
        if (details[1] == 1) {
            return sd.getGames().get(details[0]).getPlayerTotPoints(0);
        }
        return sd.getGames().get(details[0]).getPlayerTotPoints(1);
    }
    
    /**
     * To be called when JatsiPlayer leaves the game.
     * Calls method in JatsiGame  to set expired = true.
     * @param int[] details
     * @throws RemoteException 
     */
    public void disconnect(int[] details)throws RemoteException{
        sd.getGames().get(details[0]).setExpired();
    }

}
// Returns the scores of the player from the last round
