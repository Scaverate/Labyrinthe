package controler.controlerOnline;

import model.observable.MazeGame;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class MazeGameControlerOnlineClient extends MazeGameControlerOnline implements Runnable{
    public MazeGameControlerOnlineClient(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            connexion = new Socket(host, port);
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
            writer.flush();
            System.out.println("Commande '" + commande + "' envoyée au serveur");
            //On attend la réponse
            String response = read();
            System.out.println("Réponse reçue : " + response);
        } catch (IOException e1) {
            e1.printStackTrace();
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

    private Socket connexion;
    private PrintWriter writer;
    private BufferedInputStream reader;
}
