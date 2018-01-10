package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvoiSocket implements Runnable {
    private ArrayList<Object> objets;
    private Socket socket;
    private ObjectOutputStream sortie;
    
    public EnvoiSocket(Socket socket, ArrayList<Object> objets) throws IOException{
        this.socket = socket;
        this.objets = objets; 
    }

    @Override
    public void run() {
        try {
            sortie = new ObjectOutputStream(socket.getOutputStream());
            //permet de dire au récepteur combien d'éléments récupérer
            sortie.writeObject(objets.size());
            //sortie.flush();
            for(Object tmp : objets){
                sortie.writeObject(tmp);
            }
            sortie.flush();
        } catch (IOException ex) {
            Logger.getLogger(EnvoiSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
