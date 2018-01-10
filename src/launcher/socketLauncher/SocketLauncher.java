package launcher.socketLauncher;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.EnvoiSocket;
import model.ReceptionSocket;

public class SocketLauncher implements Runnable {
	public SocketLauncher() {
		ArrayList<Object> liste = new ArrayList<>();
		liste.add(new String("testtest"));
		try {
			Socket socket = new Socket(serverIP, PORT);
			
			/*Thread envoi = new Thread(new EnvoiSocket(socket, liste));
			envoi.start();
			envoi.join();
			System.out.println("Données envoyées");*/
			
	        ArrayList<Object> objets = new ArrayList<Object>();
	        Thread reception = new Thread(new ReceptionSocket(socket, objets));
	        reception.start();
			reception.join();
			System.out.println("Reception : " + objets);
			(new Thread(this)).start();
		} catch(Exception ex) {
            Logger.getLogger(EnvoiSocket.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	@Override
	public void run() { }

	String clientIP = "134.214.50.39";
	String serverIP = "134.214.50.39";
	int PORT = 1234;
	
	public static void main(String[] args) {
		SocketLauncherServer launcherServer = new SocketLauncherServer();
		SocketLauncher launcherClient = new SocketLauncher();
	}
}
