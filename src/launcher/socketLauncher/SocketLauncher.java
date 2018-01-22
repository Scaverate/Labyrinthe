package launcher.socketLauncher;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class SocketLauncher implements Runnable {
	public SocketLauncher(String host, int port) {
		name += ++count;
		try {
			connexion = new Socket(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		try {
			writer = new PrintWriter(connexion.getOutputStream(), true);
			reader = new BufferedInputStream(connexion.getInputStream());
			String commande = "toto";
			writer.write(commande);
			//TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
			writer.flush();
			System.out.println("Commande " + commande + " envoyée au serveur");
			//On attend la réponse
			String response = read();
			System.out.println("\t * " + name + " : Réponse reçue " + response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		writer.write("CLOSE");
		writer.flush();
		writer.close();
	}

	private String read() throws IOException{
		String response;
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}
	private Socket connexion = null;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private static int count = 0;
	private String name = "Client-";
}
