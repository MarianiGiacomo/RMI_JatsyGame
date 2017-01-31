
import java.rmi.*;

/**
 *
 * @author Giacomo Mariani
 * Interface for the RMI Jatsi object 
 */
interface Jatsi extends Remote {

    public String welcome() throws RemoteException;
    
    public int[] setPlayer(String name) throws RemoteException;
    
    public boolean isGameReady(int gameIndex)throws RemoteException;
    
    public String getOtherPlayerName(int[] details)throws RemoteException;
    
    public void throwDices(int[] details) throws RemoteException;
    
    public void setReady(int[] details, boolean ready)throws RemoteException;
    
    public boolean isOtherPlayerReady(int[] details)throws RemoteException;
    
    public int[] getYourScore(int[] details, int round) throws RemoteException;
    
    public int[] getOtherPlayerScore(int[] details, int round) throws RemoteException;
    
    public int getPoints(int[] details)throws RemoteException;
    
    public int getOtherPlayerPoints(int[] details) throws RemoteException;
    
    public int getTotPoints(int[] details) throws RemoteException;
    
    public int getOtherPlayerTotPoints(int[] details) throws RemoteException;
    
    public void disconnect(int[] details)throws RemoteException;
    
    

}
