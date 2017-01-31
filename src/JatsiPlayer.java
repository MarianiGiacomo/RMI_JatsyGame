

/**
 * Class for the player. 
 * Contains the players name and status
 * @author Giacomo Mariani
 */
public class JatsiPlayer {

    private String name;
    private boolean ready;

    public JatsiPlayer(String name) {
        this.name = name;
        this.ready = false;
    }

    public JatsiPlayer() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public boolean isReady(){
        return ready;
    }

    public void setName(String name) {
        this.name = name;
    }
  
    public void setReady(boolean ready){
        this.ready = ready;
    }
    
    public String toString(){
        return name;
    }
}
