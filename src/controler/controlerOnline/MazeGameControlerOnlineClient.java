package controler.controlerOnline;

import model.observable.MazeGame;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MazeGameControlerOnlineClient extends MazeGameControlerOnline implements Observer {
    public MazeGameControlerOnlineClient(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            connexion = new Socket(host, port);
            this.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
		if(this.mazeGame != null) {
			this.mazeGame.addObserver(this);
		}
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update client mazegame");

        // envoi vers le client
		sendMessage(this.mazeGame);
    }
    
    private void open() {
        Thread ServerHandler = new Thread(new Runnable(){
            public void run(){
                System.out.println("Traitement de la connexion serveur- côté client");
                while(!connexion.isClosed()){
                    // reception depuis le serveur
                    ArrayList<Object> reception = recieveUpdate();
                    if(reception.size() > 0 ) {
                    	if(reception.get(0) != null) {
                            System.out.println(reception.get(0));
                        	mazeGame.updateFromExternalMazeGame((MazeGame) reception.get(0));
                        	mazeGame.notifyObservers(mazeGame.getTreasureIHMs());
                        	mazeGame.notifyObservers(mazeGame.getCouloirIHMs());
                        	mazeGame.notifyObservers(mazeGame.getExtraCorridorIHM());
                        	//mazeGame.notifyObservers(mazeGame.getExtraTreasureIHM());
                        	mazeGame.notifyObservers(mazeGame.getPiecesIHMs());
                    	}
                    }

                    // fermer la connexion
                    /*if(query.equals("CLOSE")){
                        System.err.println("COMMANDE CLOSE DETECTEE ! ");
                        writer = null;
                        connexion.close();
                        break;
                    }*/
                }
            }
        });
        ServerHandler.start();
    }
    
    private void sendMessage(Object command) {
        if(command == null) {
            return;
        }

        try{
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(command);
            ObjectOutputStream sortie = new ObjectOutputStream(connexion.getOutputStream());
            sortie.writeObject(objects.size());
            for(Object tmp : objects){
                sortie.writeObject(tmp);
            }
            sortie.flush();
        } catch(IOException ex) { ex.printStackTrace(); }
    }

    private ArrayList<Object> recieveUpdate() {
		
        ObjectInputStream entree;
        ArrayList<Object> reception = new ArrayList<>();
		if(connexion == null) {
			return reception;
		}
        try {
        	/*
            entree = new ObjectInputStream(connexion.getInputStream());
            int taille = (int) entree.readObject();
            for(int i = 0; i < taille; i++){
                reception.add(entree.readObject());
            }
            */
            entree = new ObjectInputStream(connexion.getInputStream());
            reception.add(entree.readObject());
        } catch (IOException | ClassNotFoundException ex) {
        	ex.printStackTrace();
        	try{
            	connexion.close();
        	} catch (IOException exc) { exc.printStackTrace(); }
        }
        return reception;
    }

    private Socket connexion;
}
