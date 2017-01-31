
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Class for the client side of the jatsi game.
 * @author Giacomo Mariani
 */
public class JatsiClient {

    static String name;         // Player's name
    static String otherName;    // Other player's name
    static int[] details;       // Details of JatsiGame instance's and JatsiPlayer instance's indexes in corresponding arraylists.
    static int round;           // Round count

    /**
     * @param args the command line arguments
     */
    public static void main(String[] q) throws MalformedURLException, RemoteException, NotBoundException {

        Scanner reader = new Scanner(System.in);

        try {
            Jatsi d = (Jatsi) Naming.lookup("rmi://localhost:1099/jatsi");
            System.out.println(d.welcome() + "\n");
            System.out.println("What is your name? \n");
            name = reader.nextLine();
            details = d.setPlayer(name);    // Stores in details[] the indexes of JatsiGame instance's and JatsiPlayer instance's indexes in corresponding arraylists
            if (details[1] == 0) {          // If this is the fist player to join the game, we wait for the second player to arrive 
                System.out.println("Hi " + name + "! You are the first player, lets wait for somebody to join you...");
                while (!d.isGameReady(details[0])) {
                    try {
                        System.out.print(".");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                otherName = d.getOtherPlayerName(details);
                System.out.println("You have been joined by: " + otherName);
            } else if (details[1] == 1) {   // If this is the second player to join the game the we are ready to start
                otherName = d.getOtherPlayerName(details);
                System.out.println("Hi " + name + "! You are going to play with " + otherName);
            }
            startGame(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to start the game / opt ut of the game
     * @param d
     * @throws InterruptedException
     * @throws RemoteException 
     */
    static void startGame(Jatsi d) throws InterruptedException, RemoteException {
        round = 0;
        Scanner reader = new Scanner(System.in);
        System.out.print("Do you want to start the game? (y/n): ");
        String input = reader.nextLine();
        if (input.equals("n")) {
            System.exit(0);
        } else if (input.equals("y")) {
            startRound(d);
        } else if (reader.nextLine().equals("n")) {
            d.disconnect(details);  
            System.exit(0);
        } else {
            startGame(d);
        }
    }

    /**
     * Method to start the JatsiRound. 
     * Increases round count by one. Game ends when a player opts out or
     * five rounds have been played.
     * @param d
     * @throws InterruptedException
     * @throws RemoteException 
     */
    static void startRound(Jatsi d) throws InterruptedException, RemoteException {
        Scanner reader = new Scanner(System.in);
        try {
            System.out.println("ROUND " + (round+1) + "/5");
            if (!d.isOtherPlayerReady(details)) {   // We wait for the other player to join the round
                d.setReady(details, true);
                while (!d.isOtherPlayerReady(details)) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    if(!d.isGameReady(details[0])){ // If the game status has been changed to ready = false (other player has opted out) we stop the game
                        System.out.println("Sorry, " + otherName + " has exited the game..");
                        System.exit(0);
                    }
                }
            } else {                                // If the other player has already joined the round, we can go on 
                d.setReady(details, true);
                d.throwDices(details);
            }
            System.out.print("\n Throwing the dices..");
            for (int i = 0; i < 4; i++) {           // Just to get some suspance... 
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
            }
            int[] score = d.getYourScore(details, round);
            int[] otherScore = d.getOtherPlayerScore(details, round);
            System.out.print("\n Your dices: ");
            for (int i = 0; i < 5; i++) {
                System.out.print(score[i] + " ");
            }
            System.out.print("\n " + otherName + "'s dices: ");
            for (int i = 0; i < 5; i++) {
                System.out.print(otherScore[i] + " ");
            }
            System.out.print("\n Getting the score..");
            for (int i = 0; i < 4; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
            }
            System.out.println("\n Your  points: " + d.getPoints(details));
            System.out.println(" " + otherName + "'s points: " + d.getOtherPlayerPoints(details));
            System.out.println("TOTAL SCORE: ");
            System.out.println(name + ": " + d.getTotPoints(details));
            System.out.println(otherName + ": " + d.getOtherPlayerTotPoints(details));
            round++;
            if(round == 5){
                String winner = "";
                if(d.getTotPoints(details) > d.getOtherPlayerTotPoints(details)){
                    winner = name;
                } else if(d.getTotPoints(details) < d.getOtherPlayerTotPoints(details)){
                    winner = otherName;
                } else if (d.getTotPoints(details) == d.getOtherPlayerTotPoints(details)){
                    winner = "Both! Even game!";
                } else {
                    winner = "Huops... there has been some mistake.. Sorry :/";
                }
                System.out.print("THE WINNER IS.... " + winner + "!!!");
                System.exit(0);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        d.setReady(details, false);                 // At the end of each round we set JatsiPlayer's status to ready = false
        System.out.println("Another round? (y/other input): ");
        if (reader.nextLine().equals("y")) {
            startRound(d);
        } else {
            d.disconnect(details);
            System.exit(0);
        }
    }

}
