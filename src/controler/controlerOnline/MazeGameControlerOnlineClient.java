package controler.controlerOnline;

import model.observable.MazeGame;
import tools.ReceptionSocket;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeGameControlerOnlineClient extends MazeGameControlerOnline implements Runnable{
    public MazeGameControlerOnlineClient(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            connexion = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(this);
        t.start();

    }

    public void run(){
        try {
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());
            String commande = "GETMAZEGAME";
            writer.write(commande);
            writer.flush();

            //System.out.println(this.getMazeGame());
            this.mazeGame = this.getMazeGame();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

       /* writer.write("CLOSE");
        writer.flush();
        writer.close();*/
        
    }

    private String read() throws IOException{
        String response;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }
    
    private MazeGame getMazeGame(){
        ArrayList<Object> recieveObjects = new ArrayList<>();
        try {
            ObjectInputStream entree = new ObjectInputStream(connexion.getInputStream());
            int taille = (int) entree.readObject();
            for(int i=0;i<taille;i++){
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
    private BufferedInputStream reader;
}
