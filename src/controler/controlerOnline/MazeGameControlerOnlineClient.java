package controler.controlerOnline;

import model.observable.MazeGame;
import tools.ReceptionSocket;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeGameControlerOnlineClient extends MazeGameControlerOnline implements Runnable {
    public MazeGameControlerOnlineClient(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            connexion = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            Thread t = new Thread(this);
            t.start();
            //t.join();
        } catch(Exception e) { }

    }

    public void run(){
        try {
            writer = new PrintWriter(connexion.getOutputStream(), true);
            String commande = "GETMAZEGAME";
            writer.write(commande);
            writer.flush();

            this.mazeGame = this.getMazeGame();

        } catch (IOException ex) {
        	ex.printStackTrace();
        }

       /* writer.write("CLOSE");
        writer.flush();
        writer.close();*/
        
    }
    
    private MazeGame getMazeGame(){
        ArrayList<Object> recieveObjects = new ArrayList<>();
        try {
            ObjectInputStream entree = new ObjectInputStream(connexion.getInputStream());
            int taille = (int) entree.readObject();
            for(int i=0; i<taille; i++){
                recieveObjects.add(entree.readObject());
            }
            System.out.println(recieveObjects);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceptionSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return recieveObjects.size() > 0 ? (MazeGame) recieveObjects.get(0) : null;
    }

    private Socket connexion;
    private PrintWriter writer;
}
