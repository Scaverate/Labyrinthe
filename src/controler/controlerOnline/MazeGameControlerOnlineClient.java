package controler.controlerOnline;

import model.observable.MazeGame;
import vue.MazeGameGUI;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MazeGameControlerOnlineClient extends MazeGameControlerOnline implements Observer {
    public MazeGameControlerOnlineClient(MazeGame mazeGame, String host, int port, MazeGameGUI view) {
        super(mazeGame);

        try {
            this.connexion = new Socket(host, port);
            this.view = view;

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
                            if(mazeGame != null) {
                                mazeGame.updateFromExternalMazeGame((MazeGame) reception.get(0));
                                view.update(null, mazeGame.getPiecesIHMs());
                                view.update(null, mazeGame.getCouloirIHMs());
                                view.update(null, mazeGame.getExtraCorridorIHM());
                                view.update(null, mazeGame.getTreasureIHMs());
                            }
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
        if(command == null || connexion == null) {
            return;
        }

        try{
			ObjectOutputStream sortie = new ObjectOutputStream(connexion.getOutputStream());
			sortie.writeObject(command);
			sortie.flush();
        } catch(IOException ex) {
            ex.printStackTrace();
            try{
                connexion.close();
            } catch (IOException exc) { exc.printStackTrace(); }
        }
    }

    private ArrayList<Object> recieveUpdate() {
		
        ObjectInputStream entree;
        ArrayList<Object> reception = new ArrayList<>();
		if(connexion == null) {
			return reception;
		}
        try {
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
    private MazeGameGUI view;
}
