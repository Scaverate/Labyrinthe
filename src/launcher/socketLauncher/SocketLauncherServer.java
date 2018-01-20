package launcher.socketLauncher;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import controler.controlerOnline.ClientProcessor;
import model.EnvoiSocket;

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

						Thread t = new Thread(new ClientProcessor(client));
						t.start();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("stopped running");

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
	private boolean isRunning = true;
	private final String IP;
	private final int PORT;
	
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 1234;

		SocketLauncherServer launcherServer = new SocketLauncherServer(host, port);
		launcherServer.open();
		System.out.println("Serveur initialisé.");

		Thread t = new Thread(new SocketLauncher(host, port));
		t.start();
	}
}