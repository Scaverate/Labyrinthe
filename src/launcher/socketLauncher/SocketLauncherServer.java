package launcher.socketLauncher;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.EnvoiSocket;
import model.ReceptionSocket;

public class SocketLauncherServer implements Runnable{
	public SocketLauncherServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			Socket socket = new Socket(serverIP, PORT);
			System.out.println("server created");
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
	}
}