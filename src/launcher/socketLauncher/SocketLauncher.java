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
		liste.add("testtest");
		try {
			Socket socket = new Socket(serverIP, PORT);
			
			/*Thread envoi = new Thread(new EnvoiSocket(socket, liste));
			envoi.start();
			envoi.join();
			System.out.println("Données envoyées");*/

	        Thread reception = new Thread(new ReceptionSocket(socket, liste));
	        reception.start();
			reception.join();
			System.out.println("Reception : " + liste);
			(new Thread(this)).start();
		} catch(Exception ex) {
            Logger.getLogger(EnvoiSocket.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	@Override
	public void run() { }

	static String IP = "127.0.0.1";
	String serverIP = IP;
	//String clientIP = IP;
	static int PORT = 1234;
	
	public static void main(String[] args) {
		SocketLauncherServer launcherServer = new SocketLauncherServer(IP, PORT);
		//SocketLauncher launcherClient = new SocketLauncher();

		System.out.println(launcherServer);
	}
}
