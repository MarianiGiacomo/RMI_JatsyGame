
import java.util.ArrayList;

/**
 * Class to store and retrive the JatsiGames on the JatsiServer. 
 * @author Giacomo Mariani
 */
public class SharedData {

    private ArrayList<JatsiGame> games;
    private boolean writable;

    public SharedData() {
        this.games = new ArrayList<JatsiGame>();
        writable = true; 
    }

    /**
     * Stored the given game in the JatsiGame arraylist
     * @param JatsiGame game 
     */
    public synchronized void setGame(JatsiGame game) {
        while (!writable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        games.add(game);
        writable = false; 
        notifyAll(); 
    }
    
    /**
     * Returns the last JatsiGame in the JatsiGame arraylist.
     * @return JatsiGame game.
     */
    public synchronized JatsiGame getLastGame(){
        while (writable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        writable = true; 
        notifyAll();
        return games.get(games.size()-1);
    }
    
    /**
     * Returns the JatsiGame arraylist
     * @return ArrayList JatsiGames
     */
    public synchronized ArrayList<JatsiGame> getGames() {
        return games;
    }

}
