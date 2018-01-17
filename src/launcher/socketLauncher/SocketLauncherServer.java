package launcher.socketLauncher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.EnvoiSocket;
import model.ReceptionSocket;

public class SocketLauncherServer{
	public SocketLauncherServer(String IP, int PORT) {
		this.IP = IP;
		this.PORT = PORT;
		try {
			serverSocket = new ServerSocket(this.PORT, 100, InetAddress.getByName(this.IP));
			System.out.println("server created");
		} catch(Exception ex) {
            Logger.getLogger(EnvoiSocket.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	public void open(){
		Thread t = new Thread(new Runnable(){
			public void run(){
				while(isRunning){
					try {
						//On attend une connexion d'un client
						Socket client = serverSocket.accept();

						//Une fois reçue, on la traite dans un thread séparé
						System.out.println("Connexion cliente reçue.");

						// TODO Gerer connexion client
						/*Thread t = new Thread(new ClientProcessor(client));
						t.start();*/

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
					serverSocket = null;
				}
			}
		});

		t.start();
	}

	public void close(){
		this.isRunning = false;
	}

	private ServerSocket serverSocket;
	private boolean isRunning;
	private final String IP;
	private final int PORT;
	
	public static void main(String[] args) {
		SocketLauncherServer launcherServer = new SocketLauncherServer("127.0.0.1", 1234);
		launcherServer.open();
	}
}