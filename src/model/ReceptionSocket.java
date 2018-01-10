package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionSocket implements Runnable {
    private ArrayList<Object> objets;
    private ObjectInputStream entree;
    private Socket socket;
    
    public ReceptionSocket(Socket socket, ArrayList<Object> objets) throws IOException{
        this.socket = socket;
        this.objets = objets;
    }

    @Override
    public void run() {
        try {
            entree = new ObjectInputStream(socket.getInputStream());
            int taille = (int) entree.readObject();
            for(int i=0;i<taille;i++){
                objets.add(entree.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceptionSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}