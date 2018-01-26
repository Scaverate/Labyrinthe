package controler.controlerOnline;

import model.observable.MazeGame;
import vue.MazeGameGUI;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MazeGameControlerOnlineServer extends MazeGameControlerOnline implements Observer{
	public MazeGameControlerOnlineServer(MazeGame mazeGame, String host, int port, MazeGameGUI view) {
		super(mazeGame);
		try {
			this.serverSocket = new ServerSocket(port, 100, InetAddress.getByName(host));
			System.out.println("server created");
            this.view = view;
			this.open();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(this.mazeGame != null) {
			this.mazeGame.addObserver(this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update server mazegame");

		// envoi vers le client
		sendMessage(this.mazeGame);
	}

	private void open(){
		Thread ClientHandler = new Thread(new Runnable(){
			public void run(){
				try {
					client = serverSocket.accept();
					System.out.println("Traitement de la connexion cliente - côté serveur");
					while(!serverSocket.isClosed() && !client.isClosed()){
						//sendMessage(mazeGame);
						//update(null, null);
						
						// reception depuis le client
	                    ArrayList<Object> reception = recieveUpdate();
	                    if(reception.size() > 0 ) {
	                    	if(reception.get(0) != null) {
	                        	mazeGame.updateFromExternalMazeGame((MazeGame) reception.get(0));
                                view.update(null, mazeGame.getPiecesIHM());
                                view.update(null, mazeGame.getCouloirIHMs());
                                view.update(null, mazeGame.getExtraCorridorIHM());
                                view.update(null, mazeGame.getTreasureIHMs());
	                    	}
	                    }

						// fermer la connexion
						/*
						if(query.equals("CLOSE")){
						System.err.println("COMMANDE CLOSE DETECTEE ! ");
						writer = null;
						reader = null;
						client.close();
						break;
						}
						*/
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		ClientHandler.start();
	}

	private void sendMessage(Object command) {
		if(command == null || client == null) {
			return;
		}

		try {
			ObjectOutputStream sortie = new ObjectOutputStream(client.getOutputStream());
			sortie.writeObject(command);
			sortie.flush();
		} catch(IOException ex) {
            ex.printStackTrace();
            try{
                client.close();
            } catch (IOException exc) { exc.printStackTrace(); }
        }
	}

	private ArrayList<Object> recieveUpdate() {
		ObjectInputStream entree;
		ArrayList<Object> reception = new ArrayList<>();
		if(client == null) {
			return reception;
		}
		try {
            entree = new ObjectInputStream(client.getInputStream());
            reception.add(entree.readObject());
        } catch (IOException | ClassNotFoundException ex) {
        	ex.printStackTrace();
        	try{
        		client.close();
        	} catch (IOException exc) { exc.printStackTrace(); }
        }
		return reception;
	}

	private ServerSocket serverSocket;
	private Socket client;
    private MazeGameGUI view;

	// tests
	public static void main(String[] args) {
		final String IP = "127.0.0.1";
		final int PORT = 1234;
		// on détecte si un serveur existe deja

		MazeGameControlerOnlineServer mazeGameControlerOnlineServer = new MazeGameControlerOnlineServer(
			null,
			IP,
			1234,
            new MazeGameGUI(new Dimension(0,0))
		);
		MazeGameControlerOnlineClient mazeGameControlerOnlineClient = new MazeGameControlerOnlineClient(
			null,
			IP,
			1234,
            new MazeGameGUI(new Dimension(0,0))
		);
	}
}
