package controler.controlerOnline;

import model.observable.MazeGame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MazeGameControlerOnlineServer extends MazeGameControlerOnline implements Observer{
	public MazeGameControlerOnlineServer(MazeGame mazeGame, String host, int port) {
		super(mazeGame);
		try {
			serverSocket = new ServerSocket(port, 100, InetAddress.getByName(host));
			System.out.println("server created");
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
		sendMessage("testDepuisServ");
	}

	private void open(){
		Thread ClientHandler = new Thread(new Runnable(){
			public void run(){
				try {
					client = serverSocket.accept();
					System.out.println("Traitement de la connexion cliente - côté serveur");
					while(!serverSocket.isClosed()){
						// reception depuis le client
						/*ArrayList<Object> reception = recieveUpdate();
						if(reception.size() > 0 ) { System.out.println(reception); }*/

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
			ArrayList<Object> objects = new ArrayList<>();
			objects.add(command);
			ObjectOutputStream sortie = new ObjectOutputStream(client.getOutputStream());
			sortie.writeObject(objects.size());
			for(Object object : objects){
				sortie.writeObject(object);
			}
			sortie.flush();
		} catch(IOException ex) { ex.printStackTrace(); }
	}

	private ArrayList<Object> recieveUpdate() {
		ObjectInputStream entree;
		ArrayList<Object> reception = new ArrayList<>();
		try {
			entree = new ObjectInputStream(client.getInputStream());
			int taille = (int) entree.readObject();
			for(int i = 0; i < taille; i++){
				reception.add(entree.readObject());
			}
		} catch (IOException | ClassNotFoundException ex) { ex.printStackTrace(); }
		return reception;
	}

	private ServerSocket serverSocket;
	private Socket client;

	// tests
	public static void main(String[] args) {
		MazeGameControlerOnlineServer mazeGameControlerOnlineServer = new MazeGameControlerOnlineServer(
			null,
			"127.0.0.1",
			1234
		);
		MazeGameControlerOnlineClient mazeGameControlerOnlineClient = new MazeGameControlerOnlineClient(
			null,
			"127.0.0.1",
			1234
		);
	}
}
